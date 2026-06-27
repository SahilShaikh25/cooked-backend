package com.ai.projects.cooked.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.projects.cooked.model.Visitor;

@Repository
public interface VisitorRepository
        extends JpaRepository<Visitor, Long> {

	boolean existsByVisitorUuid(String visitorUuid);

    Optional<Visitor> findByVisitorUuid(String visitorUuid);

    long countByLikedTrue();
}