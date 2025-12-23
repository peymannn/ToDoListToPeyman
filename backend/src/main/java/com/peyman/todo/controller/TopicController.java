package com.peyman.todo.controller;

import com.peyman.todo.model.Topic;
import com.peyman.todo.model.User;
import com.peyman.todo.repository.TopicRepository;
import com.peyman.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createTopic(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Map<String,String> body){
        String title = body.get("title");
        User u = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Topic t = new Topic();
        t.setUser(u);
        t.setTitle(title);
        topicRepository.save(t);
        return ResponseEntity.ok(t);
    }

    @GetMapping
    public ResponseEntity<?> listTopics(@AuthenticationPrincipal UserDetails userDetails){
        User u = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Topic> list = topicRepository.findByUser(u);
        return ResponseEntity.ok(list);
    }
}
