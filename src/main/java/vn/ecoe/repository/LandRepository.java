package vn.ecoe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import vn.ecoe.model.Land;

public interface LandRepository extends MongoRepository<Land, String>
{
}
