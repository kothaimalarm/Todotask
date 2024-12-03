package com.example.todos.controller;

import com.example.todos.entity.Todo;
import com.example.todos.entity.User;
import com.example.todos.service.TodoService;
import com.example.todos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public String findAllTask(Model model){
//        List<Todo> todo = todoService.findAllTask();
//        model.addAttribute("todo",todo);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        model.addAttribute("todo", todoService.getTodosForAuthenticatedUser());

        return "todo";
    }

    @PostMapping
    public String createTask(@ModelAttribute Todo todo){

        System.out.println(todo.getDate());
        todoService.saveTask(todo);
        return "redirect:/tasks";
    }

    @GetMapping("/add")
    public String addTaskPage(Model model){
        model.addAttribute("todo",new Todo());
        return "addTodo";
    }

    @GetMapping("/{id}/edit")
    public String editTodo(@PathVariable Long id, Model model){
        Todo td = todoService.getTodo(id);
        model.addAttribute("todo",td);
        return "editTodo";
    }

    @PostMapping("/edit")
    public String editTodo(@ModelAttribute Todo todo){
        todoService.editTodo(todo);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/delete")
    public String deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
        return "redirect:/tasks";
    }

    
}
