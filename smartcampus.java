import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

// Student class
class Student {
    int id;
    String name;
    String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email; 
    }

    @Override
    public String toString() {
        return "Student[id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}

// Course class
class Course {
    int id;
    String name;
    double fee;

    public Course(int id, String name, double fee) {
        this.id = id;
        this.name = name;
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Course[id=" + id + ", name=" + name + ", fee=" + fee + "]";
    }
}

// Custom exception for invalid fee
class InvalidFeeException extends Exception {
    public InvalidFeeException(String message) {
        super(message);
    }
}

// Custom thread class for enrollment processing
class EnrollmentThread extends Thread {
    private String studentName;

    public EnrollmentThread(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public void run() {
        System.out.println("Processing enrollment for: " + studentName);
        try {
            Thread.sleep(500); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class smartcampus {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Map<Integer, Student> students = new HashMap<>();
    Map<Integer, Course> courses = new HashMap<>();
    Map<Integer, ArrayList<Course>> enrollments = new HashMap<>();
    int choice = 0;

    do {
        System.out.println("\n===== Smart Campus Menu =====");
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Enroll Student");
        System.out.println("4. View Students");
        System.out.println("5. View Enrollments");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");

        try {
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    students.put(id, new Student(id, name, email));
                    System.out.println("✅ Student added!");
                    break;

                case 2:
                    System.out.print("Enter Course ID: ");
                    int cid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Course Name: ");
                    String cname = sc.nextLine();

                    System.out.print("Enter Fee: ");
                    double fee = sc.nextDouble();

                    courses.put(cid, new Course(cid, cname, fee));
                    System.out.println("✅ Course added!");
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();

                    System.out.print("Enter Course ID: ");
                    int coid = sc.nextInt();

                    if (!students.containsKey(sid) || !courses.containsKey(coid)) {
                        System.out.println("❌ Invalid Student or Course!");
                        break;
                    }

                    enrollments.putIfAbsent(sid, new ArrayList<>());
                    enrollments.get(sid).add(courses.get(coid));

                    new EnrollmentThread(students.get(sid).name).start();

                    System.out.println("✅ Enrollment successful!");
                    break;

                case 4:
                    if (students.isEmpty()) {
                        System.out.println("No students found!");
                    } else {
                        for (Student s : students.values()) {
                            System.out.println(s);
                        }
                    }
                    break;

                case 5:
                    if (enrollments.isEmpty()) {
                        System.out.println("No enrollments found!");
                    } else {
                        for (int key : enrollments.keySet()) {
                            System.out.println("Student: " + students.get(key).name);

                            double total = 0;

                            for (Course c : enrollments.get(key)) {
                                System.out.println("   " + c);
                                total += c.fee;
                            }

                            System.out.println("Total Fee: " + total);
                        }
                    }
                    break;

                case 6:
                    System.out.println("👋 Exiting program...");
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
            }

        } catch (Exception e) {
            System.out.println("❌ Invalid input! Try again.");
            sc.nextLine(); // clear buffer
        }

    } while (choice != 6);

        sc.close();
    }
}