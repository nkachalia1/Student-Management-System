package courses;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

import roles.Student;

public class Course {

	private String ID;

	private String name;

	private String lecturer;

	private String days;

	private String startTime;

	private String endTime;

	private int capacity;
	
	private List<Student> enrolledStudents = new ArrayList<>();
	
	// check for conflicts

	// constructor
	public Course(String ID, String name, String lecturer, String days, String startTime, String endTime,
			int capacity) {
		this.ID = ID;
		this.name = name;
		this.lecturer = lecturer;
		this.days = days;
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
	}
	
	public List<Student> getEnrolledStudents() {
		return enrolledStudents;
	}
	
	public String getCourseName() {
		return this.name;
	}

	// check time conflict
	public boolean hasTimeConflict(Course other) {
		if (this.days.equals(other.days)) {
			LocalTime startTime1 = LocalTime.parse(this.startTime);
			LocalTime endTime1 = LocalTime.parse(this.endTime);
			LocalTime startTime2 = LocalTime.parse(other.startTime);
			LocalTime endTime2 = LocalTime.parse(other.endTime);
			
			return (startTime1.isBefore(endTime2) && startTime2.isBefore(endTime1));
			
		}

		return false;
	}

	public boolean addStudent(Student student) {
		if (enrolledStudents.size() >= capacity) {
			System.out.println("Course is full.");
			return false;
		}

		enrolledStudents.add(student);
		return true;
	}

	public boolean removeStudent(Student student) {
		return enrolledStudents.remove(student);
	}

	@Override
	public String toString() {
		return ID + ": " + name + " (" + lecturer + ")";
	}

	public String getID() {
		return this.ID;
	}
}