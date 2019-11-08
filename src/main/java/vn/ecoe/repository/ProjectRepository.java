package vn.ecoe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ecoe.model.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
