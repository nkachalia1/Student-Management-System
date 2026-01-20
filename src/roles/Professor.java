package roles;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import courses.Course;

public class Professor extends User {
	private List<String> assignedCourses;
	
	public Professor(String ID, String name, String username, String password, List<String> assignedCourses) {
		super(ID, name, username, password);
		this.assignedCourses = assignedCourses;
	}
	
	public List<String> getAssignedCourses() {
		return assignedCourses;
	}
	
	public void viewAssignedCourses(Map<String, Course> courses) {
		System.out.println("\nAssigned Courses:");
		for (String courseId : assignedCourses) {
			Course course = courses.get(courseId);
			if (course != null) {
				System.out.println(course);
			} else {
				System.out.println("Course ID " + courseId + " not found.");
			}
		}
	}
	
	public void viewEnrolledStudents(String courseId, Map<String, Course> courses, List<Student> students) {
		Course course = courses.get(courseId);
		if (course == null || !assignedCourses.contains(courseId)) {
			System.out.println("You are not assigned to this course or the course does not exist.");
			return;
		}
		
		System.out.println("\nEnrolled students for " + course.getCourseName() + ":");
		for (Student student : students) {
			if (student.getCourses().contains(course)) {
				System.out.println(student);
			}
		}
	}
	
	public void assignGrades(Scanner scanner, String courseId, List<Student> students) {
		System.out.println("\nAssigning grades for course: " + courseId);
		for (Student student : students) {
			if (student.getCourses().stream().anyMatch(c -> c.getID().equals(courseId))) {
				System.out.print("Enter grade for " + student.getName() + ": ");
				String grade = scanner.nextLine().trim();
				student.addGrade(courseId, grade);
			}
		}
		System.out.println("Grades assigned successfully.");
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