package my.utem.bitp3123.student_service;

public class Student {
	
	private int id;
	private String name;
	private String email;
	private String programme;
	
	// Default Constructor
	public Student() {}
	
	public Student(int id, String name, String email, String programme) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.programme = programme;
	}
	
	
	// Setters and Getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}
	
	

}
