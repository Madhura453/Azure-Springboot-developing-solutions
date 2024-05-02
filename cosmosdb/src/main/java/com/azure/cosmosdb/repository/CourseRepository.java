package com.azure.cosmosdb.repository;

import com.azure.cosmosdb.Model.Course;
import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CourseRepository extends ReactiveCosmosRepository<Course,String> {
Mono<Course> findByCourseId(String courseId);

@Query(value="SELECT * FROM c where c.id= @id")
Flux<Course> getCourse(String id);
}
