package com.ai.projects.cooked.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.projects.cooked.service.VisitorService;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final VisitorService visitorService;

    public StatsController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping("/visit")
    public void registerVisitor(
            @RequestHeader("Visitor-Id") String visitorId) {

        visitorService.registerVisitor(visitorId);
    }

    @GetMapping("/visitors")
    public long getVisitors() {
        return visitorService.getVisitorCount();
    }
    
    @PostMapping("/like")
    public void like(
            @RequestHeader("Visitor-Id") String visitorId) {

        visitorService.like(visitorId);
    }

    @GetMapping("/likes")
    public long getLikes() {
        return visitorService.getLikeCount();
    }
}