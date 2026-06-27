package com.ai.projects.cooked.service;

import org.springframework.stereotype.Service;
import com.ai.projects.cooked.model.Visitor;
import com.ai.projects.cooked.repository.VisitorRepository;

@Service
public class VisitorService {

	private final VisitorRepository repository;

    public VisitorService(VisitorRepository repository) {
        this.repository = repository;
    }

    public void registerVisitor(String uuid) {

        if (!repository.existsByVisitorUuid(uuid)) {

            Visitor visitor = new Visitor();
            visitor.setVisitorUuid(uuid);

            repository.save(visitor);
        }
    }

    public long getVisitorCount() {
        return repository.count();
    }
    
    public void like(String visitorUuid) {

        Visitor visitor = repository
                .findByVisitorUuid(visitorUuid)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        if (!visitor.isLiked()) {
            visitor.setLiked(true);
            repository.save(visitor);
        }
    }

    public long getLikeCount() {
        return repository.countByLikedTrue();
    }
}
