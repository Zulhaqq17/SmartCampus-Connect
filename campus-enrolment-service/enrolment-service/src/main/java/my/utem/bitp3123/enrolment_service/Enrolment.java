package my.utem.bitp3123.enrolment_service;

public class Enrolment {
	private Integer enrolmentId; // guna Integer wrapper object instead of 
								 //primitive "Int" because primitives cannot be null
	private Integer studentId;
	private String courseCode;
	private String status; // "Succes or Reject"
	
	public Enrolment() {}
	
	public Enrolment(Integer enrolmentId, Integer studentId, String courseCode, String status) {
		this.enrolmentId = enrolmentId;
		this.studentId = studentId;
		this.courseCode = courseCode;
		this.status = status;
	}
	
	
// Setters and Getters
	public Integer getEnrolmentId() {
		return enrolmentId;
	}

	public void setEnrolmentId(Integer enrolmentId) {
		this.enrolmentId = enrolmentId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
