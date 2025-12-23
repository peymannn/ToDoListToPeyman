package com.peyman.todo.repository;

import com.peyman.todo.model.Todo;
import com.peyman.todo.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTopic(Topic topic);
}
