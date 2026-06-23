package my.utem.bitp3123.enrolment_service;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api/enrolments")
public class EnrolmentController {

	private final EnrolmentService service;
	
	public EnrolmentController(EnrolmentService service) {
		this.service = service;
	}
	
	// GET /api/enrolments to view all enrolments records
	@GetMapping
	public List<Enrolment> getAll(){
		return service.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Enrolment> create(@RequestBody Enrolment request) {
		
		System.out.println("Received Student ID: " + request.getStudentId());
		System.out.println("Received Course Code: " + request.getCourseCode());
		if (request.getStudentId() <= 0 || request.getCourseCode() == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Enrolment result = service.enrollStudent(request.getStudentId(), request.getCourseCode());
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
}
