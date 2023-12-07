
import java.util.*;

class Student {
    private final int studentId;
    private final String name;
    private final Map<String, List<Integer>> courses;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.courses = new HashMap<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void addCourse(String courseName) {
        courses.put(courseName, new ArrayList<>());
    }

    public void addGrade(String courseName, int grade) {
        if (courses.containsKey(courseName)) {
            courses.get(courseName).add(grade);
        } else {
            System.out.println("Course " + courseName + " not found for student " + name);
        }
    }

    public double calculateGPA() {
        double totalGPA = 0.0;
        int totalCourses = 0;

        for (Map.Entry<String, List<Integer>> entry : courses.entrySet()) {
            List<Integer> grades = entry.getValue();
            if (!grades.isEmpty()) {
                double courseAverage = grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                totalGPA += courseAverage;
                totalCourses++;
            }
        }

        return totalCourses > 0 ? totalGPA / totalCourses : 0.0;
    }

    public double calculateCourseAverage(String courseName) {
        List<Integer> grades = courses.get(courseName);
        if (grades != null && !grades.isEmpty()) {
            return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + ", Name: " + name;
    }
}

class Course {
    private String courseId;
    private String courseName;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return "Course ID: " + courseId + ", Course Name: " + courseName;
    }
}

public class StudentGradeManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();

        while (true) {
            System.out.println("\n===== Student Grade Manager =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Add Grade");
            System.out.println("4. Calculate GPA");
            System.out.println("5. Calculate Course Average");
            System.out.println("6. Display Students");
            System.out.println("7. Display Courses");
            System.out.println("8. Exit");

            System.out.print("Enter your choice (1-8): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Adding a new student
                    System.out.print("Enter Student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();
                    students.add(new Student(studentId, studentName));
                    break;

                case 2:
                    // Adding a new course
                    System.out.print("Enter Course ID: ");
                    String courseId = scanner.nextLine();
                    System.out.print("Enter Course Name: ");
                    String courseName = scanner.nextLine();
                    courses.add(new Course(courseId, courseName));
                    for (Student student : students) {
                        student.addCourse(courseName);
                    }
                    break;

                case 3:
                    // Adding grade for a student in a course
                    System.out.print("Enter Student ID: ");
                    int stuId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Course ID: ");
                    String crsId = scanner.nextLine();
                    System.out.print("Enter Grade: ");
                    int grade = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Student foundStudent = students.stream().filter(s -> s.getStudentId() == stuId).findFirst().orElse(null);
                    if (foundStudent != null) {
                        foundStudent.addGrade(crsId, grade);
                        System.out.println("Grade added for Student ID: " + stuId + " in Course ID: " + crsId);
                    } else {
                        System.out.println("Student with ID " + stuId + " not found.");
                    }
                    break;

                case 4:
                    // Calculate GPA for a student
                    System.out.print("Enter Student ID: ");
                    int studentID = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Student found = students.stream().filter(s -> s.getStudentId() == studentID).findFirst().orElse(null);
                    if (found != null) {
                        double gpa = found.calculateGPA();
                        System.out.println("GPA for Student ID: " + studentID + " is " + gpa);
                    } else {
                        System.out.println("Student with ID " + studentID + " not found.");
                    }
                    break;

                case 5:
                    // Calculate course average
                    System.out.print("Enter Course ID: ");
                    String courseID = scanner.nextLine();
                    Course foundCourse = courses.stream().filter(c -> c.getCourseId().equals(courseID)).findFirst().orElse(null);
                    if (foundCourse != null) {
                        double courseAvg = 0.0;
                        int count = 0;
                        for (Student student : students) {
                            double avg = student.calculateCourseAverage(foundCourse.getCourseName());
                            if (avg > 0) {
                                courseAvg += avg;
                                count++;
                            }
                        }
                        if (count > 0) {
                            courseAvg /= count;
                        }
                        System.out.println("Average for Course ID: " + courseID + " is " + courseAvg);
                    } else {
                        System.out.println("Course with ID " + courseID + " not found.");
                    }
                    break;

                case 6:
                    // Display students
                    System.out.println("\n===== Students =====");
                    for (Student student : students) {
                        System.out.println(student);
                    }
                    break;

                case 7:
                    // Display courses
                    System.out.println("\n===== Courses =====");
                    for (Course course : courses) {
                        System.out.println(course);
                    }
                    break;

                case 8:
                    // Exit the program
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                    break;
            }
        }
    }
}







