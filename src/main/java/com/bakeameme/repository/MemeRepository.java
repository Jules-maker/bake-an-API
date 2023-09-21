package com.bakeameme.repository;

import com.bakeameme.domain.Meme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Meme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemeRepository extends MongoRepository<Meme, String> {}
