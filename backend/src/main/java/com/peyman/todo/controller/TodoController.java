package com.peyman.todo.controller;

import com.peyman.todo.model.Topic;
import com.peyman.todo.model.Todo;
import com.peyman.todo.model.User;
import com.peyman.todo.repository.TopicRepository;
import com.peyman.todo.repository.TodoRepository;
import com.peyman.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/topics/{topicId}/todos")
public class TodoController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> addTodo(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long topicId, @RequestBody Map<String,String> body){
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        // authorization: topic belongs to user
        User u = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (!topic.getUser().getId().equals(u.getId())) return ResponseEntity.status(403).build();
        Todo t = new Todo();
        t.setTopic(topic);
        t.setTitle(body.getOrDefault("title",""));
        t.setNote(body.get("note"));
        todoRepository.save(t);
        return ResponseEntity.ok(t);
    }

    @GetMapping
    public ResponseEntity<?> listTodos(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long topicId){
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        User u = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (!topic.getUser().getId().equals(u.getId())) return ResponseEntity.status(403).build();
        List<Todo> list = todoRepository.findByTopic(topic);
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<?> toggle(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long topicId, @PathVariable Long id){
        Todo t = todoRepository.findById(id).orElseThrow();
        User u = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (!t.getTopic().getUser().getId().equals(u.getId())) return ResponseEntity.status(403).build();
        t.setDone(!t.isDone());
        todoRepository.save(t);
        return ResponseEntity.ok(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long topicId, @PathVariable Long id){
        Todo t = todoRepository.findById(id).orElseThrow();
        User u = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        if (!t.getTopic().getUser().getId().equals(u.getId())) return ResponseEntity.status(403).build();
        todoRepository.delete(t);
        return ResponseEntity.ok(Map.of("status","deleted"));
    }
}
