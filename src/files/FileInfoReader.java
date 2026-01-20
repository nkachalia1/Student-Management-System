package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import courses.Course;
import roles.Admin;
import roles.Professor;
import roles.Student;

public class FileInfoReader {
	private Map<String, Course> courses = new HashMap<>();
	private List<Admin> admins = new ArrayList<>();
	private List<Student> students = new ArrayList<>();
	private List<Professor> professors = new ArrayList<>();
	
	public void loadCourseInfo(String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length == 7) {
					courses.put(parts[0].trim(), new Course(
						parts[0].trim(),
						parts[1].trim(),
						parts[2].trim(),
						parts[3].trim(),
						parts[4].trim(),
						parts[5].trim(),
						Integer.parseInt(parts[6].trim())
					));
				}
			}
		}
	}
	
	public void loadAdminInfo(String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length == 4) {
					admins.add(new Admin(
						parts[0].trim(),
						parts[1].trim(),
						parts[2].trim(),
						parts[3].trim()
					));
				}
			}
		}
	}
	
	public void loadStudentInfo(String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length >= 4) {
					students.add(new Student(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim()));
				}
			}
		}
	}
	
	public void loadProfessorInfo(String fileName) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length == 4) {
					professors.add(new Professor(
							parts[0].trim(),
							parts[1].trim(),
							parts[2].trim(),
							parts[3].trim(),
							new ArrayList<>()
					));
				}
			}
		}
	}
	
	public List<Course> getCourses() {
		return new ArrayList<>(courses.values());
	}
	
	public List<Admin> getAdmins() {
		return admins;
	}
	
	public List<Student> getStudents() {
		return students;
	}
	
	public List<Professor> getProfessors() {
		return professors;
	}
	
}
	
	