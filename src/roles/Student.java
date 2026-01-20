package roles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import courses.Course;

public class Student extends User {
	
	private List<Course> enrolledCourses = new ArrayList<>();
	private Map<String, String> grades = new HashMap<>();
	
	// constructor
	public Student(String ID, String name, String username, String password) {
		super(ID, name, username, password);
	}
	
	public void viewCourses() {
		for (Course course : enrolledCourses) {
			System.out.println(course);
		}
	}
	
	public boolean addCourse(Course course) {
		if (enrolledCourses.contains(course)) {
			System.out.println("Course already added.");
			return false;
		}
		
		for (Course c : enrolledCourses) {
			if (c.hasTimeConflict(course)) {
				System.out.println("Time conflict with another course.");
				return false;
			}
		}
		
		enrolledCourses.add(course);
		course.addStudent(this);
		return true;
	}
	
	public boolean dropCourse(Course course) {
		if (!enrolledCourses.contains(course)) {
			System.out.println("Course not found in your schedule");
			return false;
		}
		
		enrolledCourses.remove(course);
		course.removeStudent(this);
		return true;
	}
	
	public void addGrade(String courseId, String grade) {
		grades.put(courseId, grade);
	}
	
	public void viewGrades() {
		grades.forEach((courseID, grade) -> System.out.println(courseID + ": " + grade));
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

	public List<Course> getCourses() {
		return enrolledCourses;
	}
	
}