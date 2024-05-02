package com.azure.cosmosdb.controller;

import com.azure.cosmosdb.Model.Course;
import com.azure.cosmosdb.service.CosmosDbNoSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class NoSQlCourseController {
    @Autowired
    private CosmosDbNoSqlService courseService;

    @PostMapping("/course")
    public Course saveCourse(@RequestBody Course course)
    {
       return courseService.saveCourse(course);
    }

    @GetMapping("/courseById")
    public Course findByCourseId(@RequestParam("courseId") String courseId)
    {
        return courseService.findByCourseId(courseId);
    }

    @DeleteMapping("/course")
    public void deleteByCourseIdAndPartitionKey(@RequestParam("id") String id,@RequestParam("courseId") String partitionKey)
    {
        courseService.delete(id,partitionKey);
    }

    @PostMapping("/list/courses")
    public List<Course> listOfInsert(@RequestBody List<Course> courseList)
    {
        return courseService.listOfInsert(courseList);
    }

    @DeleteMapping("/list/course")
    public String deleteByAllId(@RequestBody List<String> listOfIds)
    {
        courseService.deleteAllByID(listOfIds);

        return "Items deleted Successfully";
    }

    @PostMapping("/bulk/courses")
    public void bulkInsert(@RequestBody List<Course> courseList) throws Exception {
        courseService.bulkInsert(courseList);
    }

    @DeleteMapping("/bulk/course")
    public String bulkDelete(@RequestBody List<Course> courseList)
    {
        courseService.bulkDelete(courseList);

        return "Items deleted Successfully";
    }

    @PutMapping("/bulk/replace")
    public String bulkReplace(@RequestBody List<Course> courseList)
    {
        courseService.bulkReplace(courseList);

        return "Items replaced successfully";
    }


    @GetMapping("/quey")
    public Flux<Course> findByCourseUsingQuery(@RequestParam("id") String id)
    {
        return courseService.findByCourseUsingQuery(id);
    }

    @GetMapping("/all")
    public Flux<Course> findAllCourses()
    {
        return courseService.findAllCourses();
    }

    @GetMapping("/container/query")
    public List<Course> findCoursesByBulk(@RequestParam("id") String id,@RequestParam("courseId") String courseId)
    {
        return courseService.findCoursesByBulk(id,courseId);
    }


}
