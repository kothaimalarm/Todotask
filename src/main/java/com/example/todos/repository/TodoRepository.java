package com.example.todos.repository;

import com.example.todos.entity.Todo;
import com.example.todos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findByUser(User user);
}
