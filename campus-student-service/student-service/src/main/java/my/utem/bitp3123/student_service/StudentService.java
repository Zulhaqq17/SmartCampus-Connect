package my.utem.bitp3123.student_service;

import org.springframework.stereotype.*;
import java.util.*;

@Service // Spring manage this class as a business logic component
public class StudentService {
	
	private final List<Student> students = Collections.synchronizedList(new ArrayList<>(List.of(
		new Student (1, "Kura", "kura@utem.edu.my", "Software Development"),
		new Student (2, "Danny", "danny@utem.edu.my", "Computer Networking"),
		new Student (3, "Kyzo", "kyzo@utem.edu.my", "Multimedia")
			)));
	
	private int nextID = 4;
	
	public List<Student> findAll(){
		return students; 
	}
	
	public Optional<Student> findById(int id){
		return students.stream()
				.filter(s -> s.getId() == id)
				.findFirst();
	}
	
	public Student create (Student student) {
		student.setId(nextID++);
		students.add(student);
		return student;
	}
}
