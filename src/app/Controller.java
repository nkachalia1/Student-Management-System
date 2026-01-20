package app;
import java.util.Scanner;

import courses.Course;
import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;
import java.util.HashMap;
import java.util.Map;

public class Controller {
	
	private FileInfoReader fileReader = new FileInfoReader();
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.run();
	}
	
	public void run() {
		try {
			fileReader.loadCourseInfo("courseInfo.txt");
			fileReader.loadAdminInfo("adminInfo.txt");
			fileReader.loadStudentInfo("studentInfo.txt");
			fileReader.loadProfessorInfo("profInfo.txt");
			
			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Welcome to the Student Management System!");
				System.out.println("Please select a role:");
				System.out.println("1. Student");
				System.out.println("2. Professor");
				System.out.println("3. Administrator");
				System.out.println("4. Quit");
				
				String choice = scanner.nextLine();
				switch (choice) {
					case "1":
						handleStudentLogin(scanner);
						break;
					case "2":
						handleProfessorLogin(scanner);
						break;
					case "3":
						handleAdminLogin(scanner);
						break;
					case "4":
						System.out.println("Goodbye!");
						return;
					default:
						System.out.println("Invalid choice.");
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private void handleStudentLogin(Scanner scanner) {
		System.out.println("Student Login:");
		System.out.println("Enter username: ");
		String username = scanner.nextLine();
		System.out.println("Enter password: ");
		String password = scanner.nextLine();
		
		for (Student student : fileReader.getStudents()) {
			if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
				studentMenu(scanner, student);
				return;
			}
		}
		System.out.println("Invalid login credentials.");
	}
	
	private void studentMenu(Scanner scanner, Student student) {
		while (true) {
			System.out.println("\nStudent Menu:");
			System.out.println("1. View Courses");
			System.out.println("2. Add Course");
			System.out.println("3. Drop Course");
			System.out.println("4. View Grades");
			System.out.println("5. Logout");
			
			String choice = scanner.nextLine().trim();
			switch (choice) {
			case "1":
				student.viewCourses();
				break;
			case "2":
				System.out.print("Enter course ID to add: ");
				String addCourseId = scanner.nextLine().trim();
				Course courseToAdd = fileReader.getCourses().stream()
						.filter(c -> c.getID().equals(addCourseId))
						.findFirst()
						.orElse(null);
				if (courseToAdd != null) {
					student.addCourse(courseToAdd);
				} else {
					System.out.println("Course not found.");
				}
				break;
			case "3":
				System.out.print("Enter course ID to drop: ");
				String dropCourseId = scanner.nextLine().trim();
				Course courseToDrop = fileReader.getCourses().stream()
						.filter(c -> c.getID().equals(dropCourseId))
						.findFirst()
						.orElse(null);
				if (courseToDrop != null) {
					student.dropCourse(courseToDrop);
				} else {
					System.out.println("Course not found.");
				}
				break;
			case "4":
				student.viewGrades();
				break;
			case "5":
				System.out.println("Logged out.");
				return;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
	
	private void handleProfessorLogin(Scanner scanner) {
		System.out.println("\nProfessor Login");
		System.out.println("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		
		for (Professor professor : fileReader.getProfessors()) {
			if (professor.getUsername().equals(username) && professor.getPassword().equals(password)) {
				System.out.println("\nLogin sucessful. Welcome, Professor " + professor.getName() + ".");
				professorMenu(scanner, professor);
				return;
			}
		}
		System.out.println("Invalid login credentials.");
	}
	
	private void professorMenu(Scanner scanner, Professor professor) {
		while (true) {
			System.out.println("\nProfessor Menu:");
			System.out.println("1. View Assigned Courses");
			System.out.println("2. View Enrolled Students");
			System.out.println("3. Assign Grades");
			System.out.println("4. Log out");
			
			Map<String, Course> courseMap = new HashMap<>();
			for (Course course : fileReader.getCourses()) {
				courseMap.put(course.getID(), course);
			}
			
			String choice = scanner.nextLine();
			switch (choice) {
				case "1":
					professor.viewAssignedCourses(courseMap);
					break;
				case "2":
					System.out.print("Enter course ID to view students: ");
					String courseId = scanner.nextLine();
					professor.viewEnrolledStudents(courseId, courseMap, fileReader.getStudents());
					break;
				case "3":
					System.out.print("Enter course ID to assign grades: ");
					String gradeCourseId = scanner.nextLine();
					professor.assignGrades(scanner, gradeCourseId, fileReader.getStudents());
					break;
				case "4":
					System.out.println("Logged out.");
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}
	
	private void handleAdminLogin(Scanner scanner) {
		System.out.println("\nAdmin Login:");
		System.out.println("Enter username: ");
		String username = scanner.nextLine();
		System.out.println("Enter password: ");
		String password = scanner.nextLine();
		
		for (Admin admin : fileReader.getAdmins()) {
			if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
				System.out.println("\nLogin successful. Welcome " + admin.getName() + ".");
				adminMenu(scanner, admin);
				return;
			}
		}
		System.out.println("Invalid login credentials.");
	}
	
	private void adminMenu(Scanner scanner, Admin admin) {
		while (true) {
			System.out.println("\nAdmin Menu:");
			System.out.println("1. Add Course");
			System.out.println("2. Remove Course");
			System.out.println("3. View All Users");
			System.out.println("4. Log out");
			
			Map<String, Course> courseMap = new HashMap<>();
			for (Course course : fileReader.getCourses()) {
				courseMap.put(course.getID(), course);
			}
			
			String choice = scanner.nextLine();
			switch (choice) {
				case "1":
					admin.addCourse(scanner, courseMap);
					break;
				case "2":
					System.out.print("Enter course ID to remove: ");
					String removeCourseId = scanner.nextLine();
					admin.removeCourse(removeCourseId, courseMap);
					break;
				case "3":
					admin.viewAllUsers(fileReader.getStudents(), fileReader.getProfessors(), fileReader.getAdmins());
					break;
				case "4":
					System.out.println("Logged out.");
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}
	
}