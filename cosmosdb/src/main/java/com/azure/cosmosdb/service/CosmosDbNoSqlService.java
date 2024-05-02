package com.azure.cosmosdb.service;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.azure.cosmosdb.Model.Course;
import com.azure.cosmosdb.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CosmosDbNoSqlService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CosmosContainer container;



    public Course saveCourse(Course course) {
        return courseRepository.save(course).block();
    }

    public Course findByCourseId(String courseId) {
        return courseRepository.findByCourseId(courseId).block();
    }

    public void delete(String id, String partitionKey) {
        courseRepository.deleteById(id, new PartitionKey("Az-204")).block();
        //   Course t=courseRepository.findById(id,new PartitionKey(partitionKey)).block();
        //    courseRepository.delete(t);
        // log.info("madhura: {}",t);
        //    return "Item deleted Successfully";
    }

    public List<Course> listOfInsert(List<Course> courseList) {
        return courseRepository.saveAll(courseList).collectList().block();
    }

    public void deleteAllByID(List<String> listOfIds) {
        courseRepository.deleteAllById(listOfIds).block();
    }

    public void bulkInsert(List<Course> courseList) throws Exception {
        Iterable<CosmosItemOperation> cosmosItemOperations = courseList.stream().
                map(
                course -> {
                        return CosmosBulkOperations.getCreateItemOperation(course, new PartitionKey(course.getCourseId()));
                })
                .collect(Collectors.toList());
        ObjectMapper objectMapper=new ObjectMapper();

        container.executeBulkOperations(cosmosItemOperations);
        log.info("mad: {}",cosmosItemOperations);
                // container.createItem(cosmosItemOperations);

    }

    public void bulkDelete(List<Course> courseList) {
        Iterable<CosmosItemOperation> cosmosItemOperations = courseList.stream().
                map(
                        course -> CosmosBulkOperations.getDeleteItemOperation(course.getId(), new PartitionKey(course.getCourseId()
                                )))
                .collect(Collectors.toList());
      container.executeBulkOperations(cosmosItemOperations);
    }

    public void bulkReplace(List<Course> courseList) {
        Iterable<CosmosItemOperation> cosmosItemOperations = courseList.stream().
                map(
                        course -> CosmosBulkOperations.getReplaceItemOperation(course.getId(),course, new PartitionKey(course.getCourseId()
                        )))
                .collect(Collectors.toList());
         container.executeBulkOperations(cosmosItemOperations);
    }

    public Flux<Course> findByCourseUsingQuery(String courseId) {

        return courseRepository.getCourse(courseId);
    }

    public Flux<Course> findAllCourses() {

        return courseRepository.findAll();
    }

    public List<Course> findCoursesByBulk(String id,String courseId) {
        ArrayList<SqlParameter> paramList = new ArrayList<SqlParameter>();
        paramList.add(new SqlParameter("@id", id));
        paramList.add(new SqlParameter("@courseId", courseId));
        SqlQuerySpec querySpec = new SqlQuerySpec(
                "SELECT * FROM Course c WHERE c.id = @id AND c.courseId = @courseId",
                paramList);
        log.info("Execute query {}",querySpec.getQueryText());

        CosmosPagedIterable<Course> filteredFamilies = container.queryItems(querySpec, new CosmosQueryRequestOptions(), Course.class);

        List<Course> courseList=filteredFamilies.stream().collect(Collectors.toList());
        return courseList;
    }
}
