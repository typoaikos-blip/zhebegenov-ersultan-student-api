package kz.zhebegenov.studentapi.student;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class StudentController {
    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() {
        return "Student API by Zhebegenov Ersultan";
    }

    @GetMapping("/api/students")
    public List<Student> getAll() {
        return repository.findAll();
    }

    @GetMapping("/api/students/{id}")
    public Student getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student " + id + " not found"));
    }

    @PostMapping("/api/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student student) {
        student.setId(null);
        return repository.save(student);
    }

    @PutMapping("/api/students/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student updated) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student " + id + " not found"));
        student.setName(updated.getName());
        student.setEmail(updated.getEmail());
        student.setGroupName(updated.getGroupName());
        return repository.save(student);
    }

    @DeleteMapping("/api/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student " + id + " not found");
        }
        repository.deleteById(id);
    }
}
