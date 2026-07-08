package my.utem.bitp3123.student_service;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController // Expose endpoints as a REST web service
@RequestMapping("/api/students") // This is the base URL path
public class StudentController {
	
	private final StudentService service;
	
	// Constructor
	public StudentController(StudentService service) {
		this.service = service;
	}
	
	// GET /api/students to retrieve all profiles
	@GetMapping
	public List<Student> getAll(){
		return service.findAll();
	}
	
	// GET /api/students/{id} to get individyal profile
	@GetMapping("/{id}")
	public ResponseEntity<Student> getOne(@PathVariable int id){
		return service.findById(id)
				.map(ResponseEntity::ok) // return 200 ok with object data
				.orElse(ResponseEntity.notFound().build()); // return 400 not found
	}
	
	// POST /api/students - Create new student profile
	@PostMapping
	public ResponseEntity<Student> create(@RequestBody Student student) {
		
		// input validation
		if (student.getName() == null || student.getName().isBlank()) {
			return ResponseEntity.badRequest().build(); // return 400 bad request
		}
		Student created = service.create(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(created); // return 201 created
	}

}

