package roles;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import courses.Course;

public class Admin extends User {
	
	public Admin(String ID, String name, String username, String password) {
		super(ID, name, username, password);
	}
	
	public void addCourse(Scanner scanner, Map<String, Course> courses) {
		System.out.print("Enter course ID: ");
		String courseId = scanner.nextLine();
		
		if (courses.containsKey(courseId)) {
			System.out.println("Course ID already exists.");
			return;
		}
		
		System.out.print("Enter course name: ");
		String courseName = scanner.nextLine();
		System.out.print("Enter lecturer: ");
		String lecturer = scanner.nextLine();
		System.out.print("Enter days: ");
		String days = scanner.nextLine();
		System.out.print("Enter start time: ");
		String startTime = scanner.nextLine();
		System.out.print("Enter end time: ");
		String endTime = scanner.nextLine();
		System.out.print("Enter capacity: ");
		while (!scanner.hasNextInt()) {
			System.out.println("Please enter a valid number.");
			scanner.next();
		}
		int capacity = scanner.nextInt();
		scanner.nextLine();
		
		Course newCourse = new Course(courseId, courseName, lecturer, days, startTime, endTime, capacity);
		courses.put(courseId, newCourse);
		System.out.println("Course added successfully: " + newCourse);
	}
	
	public void removeCourse(String courseId, Map<String, Course> courses) {
		if (courses.containsKey(courseId)) {
			courses.remove(courseId);
			System.out.println("Course removed successfully.");
		} else {
			System.out.println("Course ID not found.");
		}
	}
	
	public void viewAllUsers(List<Student> students, List<Professor> professors, List<Admin> admins) {
		System.out.println("\nAll Users:");
		
		System.out.println("Students:");
		for (Student student : students) {
			System.out.println(student);
		}
		
		System.out.println("\nProfessors:");
		for (Professor professor : professors) {
			System.out.println(professor);
		}
		
		System.out.println("\nAdmins:");
		for (Admin admin : admins) {
			System.out.println(admin);
		}
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.name;
	}
	
}