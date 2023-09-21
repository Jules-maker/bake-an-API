package com.bakeameme.repository;

import com.bakeameme.domain.Disposition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Disposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositionRepository extends MongoRepository<Disposition, String> {}
