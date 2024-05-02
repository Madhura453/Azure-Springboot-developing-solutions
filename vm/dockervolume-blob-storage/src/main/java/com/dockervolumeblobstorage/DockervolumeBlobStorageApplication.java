package com.dockervolumeblobstorage;

import com.dockervolumeblobstorage.service.BlobStorageDownloadFileToDockerVolume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DockervolumeBlobStorageApplication implements CommandLineRunner {

	@Autowired
	private  BlobStorageDownloadFileToDockerVolume blobStorageDownloadFileToDockerVolume;

	public static void main(String[] args) {
		SpringApplication.run(DockervolumeBlobStorageApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("file download from main");
		String status=blobStorageDownloadFileToDockerVolume.blobStorageDownloadFileToDockerVolume();
		log.info("file download successfully. The status is:{}",status);
	}
}
