package com.community.backend.controller;

import com.community.backend.model.Thread;
import com.community.backend.model.Reply;
import com.community.backend.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/threads")
public class ThreadController {

    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping
    public List<Thread> getAllThreads() {
        return threadRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createThread(@RequestBody Thread thread) {
        return ResponseEntity.ok(threadRepository.save(thread));
    }

    // Dual-state Upvote Engine
    @PostMapping("/{id}/upvote")
    public ResponseEntity<?> upvote(@PathVariable String id, @RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        return threadRepository.findById(id).map(thread -> {
            thread.getDownvotedUserIds().remove(userId);
            if (thread.getUpvotedUserIds().contains(userId)) {
                thread.getUpvotedUserIds().remove(userId);
            } else {
                thread.getUpvotedUserIds().add(userId);
            }
            return ResponseEntity.ok(threadRepository.save(thread));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Downvote Engine
    @PostMapping("/{id}/downvote")
    public ResponseEntity<?> downvote(@PathVariable String id, @RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        return threadRepository.findById(id).map(thread -> {
            thread.getUpvotedUserIds().remove(userId);
            if (thread.getDownvotedUserIds().contains(userId)) {
                thread.getDownvotedUserIds().remove(userId);
            } else {
                thread.getDownvotedUserIds().add(userId);
            }
            return ResponseEntity.ok(threadRepository.save(thread));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Dynamic Reply Processing
    @PostMapping("/{id}/reply")
    public ResponseEntity<?> addReply(@PathVariable String id, @RequestBody Reply reply) {
        return threadRepository.findById(id).map(thread -> {
            thread.getReplies().add(reply);
            return ResponseEntity.ok(threadRepository.save(thread));
        }).orElse(ResponseEntity.notFound().build());
    }
    // 1. UPDATE/EDIT THREAD
    @PutMapping("/{id}")
    public ResponseEntity<?> updateThread(@PathVariable String id, @RequestBody Map<String, String> payload) {
        return threadRepository.findById(id).map(thread -> {
            String userId = payload.get("userId");
            if (!thread.getAuthorId().equals(userId)) {
                return ResponseEntity.status(403).body("Unauthorized: Aap sirf apna post edit kar sakte hain!");
            }
            thread.setTitle(payload.get("title"));
            thread.setContent(payload.get("content"));
            return ResponseEntity.ok(threadRepository.save(thread));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 2. ARCHIVE THREAD (Hide from general feed, keep in database)
    @PostMapping("/{id}/archive")
    public ResponseEntity<?> archiveThread(@PathVariable String id, @RequestBody Map<String, String> payload) {
        return threadRepository.findById(id).map(thread -> {
            String userId = payload.get("userId");
            if (!thread.getAuthorId().equals(userId)) {
                return ResponseEntity.status(403).body("Unauthorized!");
            }
            thread.setStatus("ARCHIVED");
            return ResponseEntity.ok(threadRepository.save(thread));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 3. HARD DELETE THREAD
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteThread(@PathVariable String id, @RequestParam String userId) {
        return threadRepository.findById(id).map(thread -> {
            if (!thread.getAuthorId().equals(userId)) {
                return ResponseEntity.status(403).body("Unauthorized!");
            }
            threadRepository.delete(thread);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Post successfully deleted!");
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }
}