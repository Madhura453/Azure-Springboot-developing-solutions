package com.azure.eventgrid.service.impl;

import com.azure.eventgrid.model.Employee;
import com.azure.eventgrid.repository.EmployeeRepository;
import com.azure.eventgrid.service.EventGridService;
import com.azure.eventgrid.util.CommonUtil;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.eventgrid.dto.EmployeeDto;
import com.azure.eventgrid.dto.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import com.azure.eventgrid.producer.SendMessageQueueProducer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventGridServiceImpl implements EventGridService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Value("${azure.storage.account-connection-string}")
    private String connectionString;

    @Value("${azure.storage.eventgrid-blob-storage}")
    private String eventGridBlobStorage;

    @Value("${azure.storage.eventgrid-archive-blob-storage}")
    private String eventGridArchiveBlobStorage;

    @Autowired
    private SendMessageQueueProducer sendMessageQueueProducer;

    @Override
    public void readEmployeeFile(String fileName) {

        String methodName = "readEmployeeFile";
        try {
            log.info("MethodName : {} | Establishing connection with container", methodName);
            BlobServiceClient blobServiceClient =
                    new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
            log.info(
                    "MethodName : {} | Establishing connection with blob storage: {}",
                    methodName,
                    eventGridBlobStorage);
            BlobContainerClient srcContClient =
                    blobServiceClient.getBlobContainerClient(eventGridBlobStorage);
            log.info("MethodName : {} | Establishing connection with file: {}", methodName, fileName);
            BlobClient srcBlob = srcContClient.getBlobClient(fileName);
            String content = readFileData(srcBlob);
            log.info("MethodName : {} | File content: {}", methodName, content);
            sendFileData(content);
            log.info(
                    "MethodName : {} | Establishing connection with archive blob storage: {}",
                    methodName,
                    eventGridArchiveBlobStorage);
            BlobContainerClient destContClient =
                    blobServiceClient.getBlobContainerClient(eventGridArchiveBlobStorage);
            archiveAndDeleteFile(fileName, srcBlob, destContClient);
            log.info("MethodName : {} | File archive completed", methodName);
        } catch (Exception e) {
            log.error("MethodName : {} | Error: {}", methodName, ExceptionUtils.getStackTrace(e));
        }
    }


    private String readFileData(BlobClient srcBlob) throws IOException {
        return StreamUtils.copyToString(srcBlob.openInputStream(), Charset.defaultCharset());
    }

    private void sendFileData(String content) {
        String methodName = "sendFileData";
        sendMessageQueueProducer.sendQueueMessage(content);
        log.info("MethodName : {}", methodName);
    }

    private void archiveAndDeleteFile(
            String fileName, BlobClient srcBlob, BlobContainerClient destContClient) {

        String methodName = "archiveAndDeleteFile";
        String archiveFileName = Timestamp.from(Instant.now()) + "_" + fileName;
        log.info(
                "MethodName : {} | Establishing connection with file: {}", methodName, archiveFileName);
        BlobClient destBlob = destContClient.getBlobClient(archiveFileName);
        log.info("MethodName : {} | Creating copy of file in archive!", methodName);
        destBlob.beginCopy(srcBlob.getBlobUrl(), null);
        log.info("MethodName : {} | Deleting original file!", methodName);
        srcBlob.delete();
    }


    @Override
    public void persistEmployeeObject(String content) {
        try {
            List<String> lines = Arrays.asList(content.split("\n", -1));
            List<Employee> employees = new ArrayList<>();
            if (!CollectionUtils.isEmpty(lines)) {
                employees = lines.stream()
                        .map(e -> {
                            EmployeeDto employeeDto = generateEmployee(e);
                            return employeeMapper.dtoToDtoEntity(employeeDto);
                        }).collect(Collectors.toList());
            } else {
                log.info("content line is empty {}", lines);
            }
            employees.removeAll(Collections.singletonList(null));
            if (!CollectionUtils.isEmpty(employees)) {
                employeeRepository.saveAll(employees);
            } else {
                log.info("rpa writeback data is empty............");
            }
        } catch (Exception e) {
            log.error("error occur while parsing line {} ", ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public EmployeeDto generateEmployee(String line) {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            CommonUtil.processFixedFile(EmployeeDto.class.getDeclaredFields(), line, employeeDto);
            return employeeDto;
        } catch (IllegalAccessException e) {
            log.info("Error coming while parsing data {}", ExceptionUtils.getStackTrace(e));
            return null;
        }
    }
}
