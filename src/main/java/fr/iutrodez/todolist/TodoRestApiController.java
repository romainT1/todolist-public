package fr.iutrodez.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoRestApiController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public Iterable<Todo> readAll() {
        return this.todoRepository.findAll();
    }

    @PostMapping("/todos")
    public Todo create(@RequestBody Todo todo) {
        return this.todoRepository.save(todo);
    }

    @DeleteMapping("/todos/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.todoRepository.deleteById(id);
    }

}
