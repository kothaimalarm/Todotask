package com.example.todos.service;

import com.example.todos.entity.Todo;
import com.example.todos.entity.User;
import com.example.todos.repository.TodoRepository;
import com.example.todos.repository.UserRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<Todo> findAllTask() {
        List<Todo> todo = todoRepository.findAll();
        return todo;
    }

    public void saveTask(Todo todo) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByName(userEmail);

        todo.setUser(user);
        todoRepository.save(todo);
    }

    public Todo getTodo(Long id) {
        return todoRepository.findById(id).get();
    }

    public void editTodo(Todo todo) {
        Todo existingTodo = todoRepository.findById(todo.getId()).get();

        existingTodo.setName(todo.getName());
        existingTodo.setCompleted(todo.getCompleted());
        existingTodo.setDate(todo.getDate());

        todoRepository.save(existingTodo);
    }

    public List<Todo> getTodosForAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username);
        return todoRepository.findByUser(user);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }


}
