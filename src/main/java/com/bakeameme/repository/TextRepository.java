package com.bakeameme.repository;

import com.bakeameme.domain.Text;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Text entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TextRepository extends MongoRepository<Text, String> {}
