package my.utem.bitp3123.enrolment_service;

import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import java.util.*;
import java.io.PrintWriter;
import java.net.Socket;


@Service
public class EnrolmentService {
	private final List<Enrolment> enrolments = Collections.synchronizedList(new ArrayList<>());
	private int nextId = 1;
	
	// Spring utility that make REST HTTP request to other API
	private final RestTemplate restTemplate = new RestTemplate();
	
	// URL pointing to Student Service
	private final String STUDENT_SERVICE_URL = "http://localhost:8080/api/students/";
	
	public List<Enrolment> findAll(){
		return enrolments;
	}
	
	// Add this helper method inside EnrolmentService.java to transmit notifications
	private void sendNetworkNotification(String message) {
	    // Connect to the Notification Service running on localhost port 9999
	    try (Socket socket = new Socket("localhost", 9999);
	         PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
	        
	        out.println(message); // Drop off the payload string
	        System.out.println("Asynchronous notification trigger dispatched over socket.");
	        
	    } catch (Exception e) {
	        // Safe fallback if the Notification server happens to be offline
	        System.err.println("Failed to dispatch real-time alert: Notification Service unreachable.");
	    }
	}
	
	public Enrolment enrollStudent(int studentId, String courseCode) {
		Enrolment enrolment = new Enrolment();
		enrolment.setEnrolmentId(nextId++);
		enrolment.setStudentId(studentId);
		enrolment.setCourseCode(courseCode);
		
		try {
			// Query to check if student exist
			System.out.println("Connecting to Student Service to validate ID: " + studentId);
			restTemplate.getForEntity(STUDENT_SERVICE_URL + studentId, String.class);
			
			// if request succeed, the student is valid
			enrolment.setStatus("Success");
			sendNetworkNotification("STUDENT_ID: " + studentId + " has successfully registered for course: " + courseCode);
		} catch (HttpClientErrorException.NotFound e) {
			System.out.println("Validation failed: Student ID " + studentId + "does not exist.");
			enrolment.setStatus("REJECTED - Invalid Student");
		} catch (Exception e) {
			
			// return if student service is offline
			System.out.println("Error: Student Service is unreachable.");
			enrolment.setStatus("REJECTED - SYSTEM OFFLINE");
		}
		
		enrolments.add(enrolment);
		return enrolment;
	}
}
