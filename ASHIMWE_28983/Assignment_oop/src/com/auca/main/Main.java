package com.auca.main;

import com.auca.interfaces.Identifiable;
import com.auca.abstracts.Person;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import com.auca.administrator.Administrator;
import com.auca.department.Department;
import com.auca.course.Course;
import com.auca.classroom.Classroom;
import com.auca.enrollment.Enrollment;
import com.auca.assessment.Assessment;
import com.auca.feepayment.FeePayment;
import com.auca.librarybook.LibraryBook;
import com.auca.bookloan.BookLoan;
import com.auca.exceptions.ValidationException;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.RecordNotFoundException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.BookUnavailableException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> payments = new ArrayList<>();
    private static final List<LibraryBook> books = new ArrayList<>();
    private static final List<BookLoan> loans = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = readText("Select an option: ");

            try {
                switch (choice) {
                    case "1" -> registerDepartment();
                    case "2" -> registerStudent();
                    case "3" -> registerTeacher();
                    case "4" -> registerAdministrator();
                    case "5" -> registerCourse();
                    case "6" -> registerClassroom();
                    case "7" -> registerEnrollment();
                    case "8" -> registerAssessment();
                    case "9" -> registerPayment();
                    case "10" -> registerBook();
                    case "11" -> borrowBook();
                    case "12" -> returnBook();
                    case "13" -> viewAllRecords();
                    case "14" -> searchRecord();
                    case "15" -> updateRecord();
                    case "16" -> studentAcademicSummary();
                    case "17" -> studentPaymentHistory();
                    case "18" -> viewAvailableBooks();
                    case "19" -> demonstratePolymorphism();
                    case "0" -> {
                        running = false;
                        System.out.println("Thank you for using AUCA_ID.");
                    }
                    default -> System.out.println("Invalid option. Choose a number from 0 to 19.");
                }
            } catch (ValidationException | DuplicateRecordException |
                     RecordNotFoundException | PrerequisiteMissingException |
                     BookUnavailableException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (NumberFormatException | DateTimeParseException e) {
                System.out.println("Invalid input format. Please try again.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress ENTER to continue.");
                input.nextLine();
            }
        }

        input.close();
    }

    private static void showMenu() {
        System.out.println("\n============== AUCA_ID SCHOOL MANAGEMENT ==============");
        System.out.println("1. Register Department          11. Borrow Library Book");
        System.out.println("2. Register Student             12. Return Library Book");
        System.out.println("3. Register Teacher             13. View All Records");
        System.out.println("4. Register Administrator      14. Search Record by ID");
        System.out.println("5. Register Course              15. Update Existing Record");
        System.out.println("6. Register Classroom           16. Student Academic Summary");
        System.out.println("7. Enroll Student               17. Student Payment History");
        System.out.println("8. Record Assessment            18. View Available Books");
        System.out.println("9. Record Fee Payment           19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book        0. Exit");
        System.out.println("========================================================");
    }

    private static String readText(String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                return Double.parseDouble(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static BigDecimal readMoney(String message) {
        while (true) {
            try {
                return new BigDecimal(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid monetary amount.");
            }
        }
    }

    private static LocalDate readDate(String message) {
        while (true) {
            try {
                return LocalDate.parse(readText(message));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
            }
        }
    }

    private static boolean readYesNo(String message) {
        while (true) {
            String answer = readText(message + " (Y/N): ");
            if (answer.equalsIgnoreCase("Y")) {
                return true;
            }
            if (answer.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Enter Y or N.");
        }
    }

    private static void checkUniqueId(String id) throws DuplicateRecordException {
        for (Identifiable record : allIdentifiableRecords()) {
            if (record.getId().equalsIgnoreCase(id)) {
                throw new DuplicateRecordException("ID already exists: " + id);
            }
        }
    }

    private static List<Identifiable> allIdentifiableRecords() {
        List<Identifiable> records = new ArrayList<>();
        records.addAll(departments);
        records.addAll(students);
        records.addAll(teachers);
        records.addAll(administrators);
        records.addAll(courses);
        records.addAll(classrooms);
        records.addAll(enrollments);
        records.addAll(assessments);
        records.addAll(payments);
        records.addAll(books);
        records.addAll(loans);
        return records;
    }

    private static Department chooseDepartment() throws PrerequisiteMissingException {
        if (departments.isEmpty()) {
            throw new PrerequisiteMissingException("No department exists. Register a department first.");
        }

        System.out.println("\nChoose a Department:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select department: ");
        if (choice < 1 || choice > departments.size()) {
            throw new PrerequisiteMissingException("Invalid department selection.");
        }

        return departments.get(choice - 1);
    }

    private static Student chooseStudent() throws PrerequisiteMissingException {
        if (students.isEmpty()) {
            throw new PrerequisiteMissingException("No student exists. Register a student first.");
        }

        System.out.println("\nChoose a Student:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select student: ");
        if (choice < 1 || choice > students.size()) {
            throw new PrerequisiteMissingException("Invalid student selection.");
        }

        return students.get(choice - 1);
    }

    private static Teacher chooseTeacher() throws PrerequisiteMissingException {
        if (teachers.isEmpty()) {
            throw new PrerequisiteMissingException("No teacher exists. Register a teacher first.");
        }

        System.out.println("\nChoose a Teacher:");
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i + 1) + ". " + teachers.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select teacher: ");
        if (choice < 1 || choice > teachers.size()) {
            throw new PrerequisiteMissingException("Invalid teacher selection.");
        }

        return teachers.get(choice - 1);
    }

    private static Course chooseCourse() throws PrerequisiteMissingException {
        if (courses.isEmpty()) {
            throw new PrerequisiteMissingException("No course exists. Register a course first.");
        }

        System.out.println("\nChoose a Course:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select course: ");
        if (choice < 1 || choice > courses.size()) {
            throw new PrerequisiteMissingException("Invalid course selection.");
        }

        return courses.get(choice - 1);
    }

    private static Enrollment chooseEnrollment() throws PrerequisiteMissingException {
        if (enrollments.isEmpty()) {
            throw new PrerequisiteMissingException("No enrollment exists. Enroll a student first.");
        }

        System.out.println("\nChoose an Enrollment:");
        for (int i = 0; i < enrollments.size(); i++) {
            System.out.println((i + 1) + ". " + enrollments.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select enrollment: ");
        if (choice < 1 || choice > enrollments.size()) {
            throw new PrerequisiteMissingException("Invalid enrollment selection.");
        }

        return enrollments.get(choice - 1);
    }

    private static LibraryBook chooseBook() throws PrerequisiteMissingException {
        if (books.isEmpty()) {
            throw new PrerequisiteMissingException("No library book exists. Register a book first.");
        }

        System.out.println("\nChoose a Library Book:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select book: ");
        if (choice < 1 || choice > books.size()) {
            throw new PrerequisiteMissingException("Invalid book selection.");
        }

        return books.get(choice - 1);
    }

    private static void registerDepartment() throws Exception {
        System.out.println("\n---------- REGISTER DEPARTMENT ----------");

        String id = readText("Department ID: ");
        checkUniqueId(id);

        String name = readText("Department name: ");
        String code = readText("Department code: ");
        String office = readText("Office location: ");
        String email = readText("Contact email: ");

        Teacher head = null;
        if (!teachers.isEmpty() && readYesNo("Assign a head teacher now?")) {
            head = chooseTeacher();
        }

        Department department = new Department(id, name, code, office, email, head);
        department.validate();
        departments.add(department);

        System.out.println("Department registered successfully.");
        department.printDetails();
    }

    private static void registerStudent() throws Exception {
        System.out.println("\n---------- REGISTER STUDENT ----------");

        if (departments.isEmpty()) {
            System.out.println("A department is required before registering a student.");
            if (readYesNo("Register a department now?")) {
                registerDepartment();
            } else {
                return;
            }
        }

        String personId = readText("Person ID: ");
        checkUniqueId(personId);

        String firstName = readText("First name: ");
        String lastName = readText("Last name: ");
        String email = readText("Personal email: ");
        String phone = readText("Phone number: ");
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");

        String memberNumber = readText("Member number: ");
        checkUniqueId(memberNumber);

        Department department = chooseDepartment();
        LocalDate joinDate = readDate("Join date (YYYY-MM-DD): ");
        String status = readText("Academic status (ACTIVE/INACTIVE): ").toUpperCase();
        String institutionalEmail = readText("Institutional email: ");
        String advisor = readText("Advisor name: ");

        String studentNumber = readText("Student number: ");
        checkUniqueId(studentNumber);

        String program = readText("Program name: ");
        LocalDate admissionDate = readDate("Admission date (YYYY-MM-DD): ");
        int year = readInt("Study year (1-6): ");
        String guardianPhone = readText("Guardian phone: ");
        boolean scholarship = readYesNo("Scholarship");

        Student student = new Student(
                personId, firstName, lastName, email, phone, dob,
                memberNumber, department, joinDate, status, institutionalEmail, advisor,
                studentNumber, program, admissionDate, year, guardianPhone, scholarship
        );

        student.validate();
        students.add(student);

        System.out.println("Student registered successfully.");
        student.printDetails();
    }

    private static void registerTeacher() throws Exception {
        System.out.println("\n---------- REGISTER TEACHER ----------");

        if (departments.isEmpty()) {
            System.out.println("A department is required before registering a teacher.");
            if (readYesNo("Register a department now?")) {
                registerDepartment();
            } else {
                return;
            }
        }

        String personId = readText("Person ID: ");
        checkUniqueId(personId);

        String firstName = readText("First name: ");
        String lastName = readText("Last name: ");
        String email = readText("Personal email: ");
        String phone = readText("Phone number: ");
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");

        String memberNumber = readText("Member number: ");
        checkUniqueId(memberNumber);

        Department department = chooseDepartment();
        LocalDate joinDate = readDate("Join date (YYYY-MM-DD): ");
        String status = readText("Academic status (ACTIVE/INACTIVE): ").toUpperCase();
        String institutionalEmail = readText("Institutional email: ");
        String advisor = readText("Advisor name: ");

        String employeeNumber = readText("Employee number: ");
        checkUniqueId(employeeNumber);

        String specialization = readText("Specialization: ");
        LocalDate hireDate = readDate("Hire date (YYYY-MM-DD): ");
        BigDecimal salary = readMoney("Salary: ");
        String officeNumber = readText("Office number: ");
        String employmentType = readText("Employment type: ");

        Teacher teacher = new Teacher(
                personId, firstName, lastName, email, phone, dob,
                memberNumber, department, joinDate, status, institutionalEmail, advisor,
                employeeNumber, specialization, hireDate, salary, officeNumber, employmentType
        );

        teacher.validate();
        teachers.add(teacher);

        System.out.println("Teacher registered successfully.");
        teacher.printDetails();
    }

    private static void registerAdministrator() throws Exception {
        System.out.println("\n---------- REGISTER ADMINISTRATOR ----------");

        String personId = readText("Person ID: ");
        checkUniqueId(personId);

        String firstName = readText("First name: ");
        String lastName = readText("Last name: ");
        String email = readText("Email: ");
        String phone = readText("Phone number: ");
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");

        String staffNumber = readText("Staff number: ");
        checkUniqueId(staffNumber);

        String jobTitle = readText("Job title: ");
        String office = readText("Assigned office: ");
        LocalDate employmentDate = readDate("Employment date (YYYY-MM-DD): ");
        int accessLevel = readInt("Access level (1-5): ");
        boolean active = readYesNo("Is active");

        Administrator administrator = new Administrator(
                personId, firstName, lastName, email, phone, dob,
                staffNumber, jobTitle, office, employmentDate, accessLevel, active
        );

        administrator.validate();
        administrators.add(administrator);

        System.out.println("Administrator registered successfully.");
        administrator.printDetails();
    }

    private static void registerCourse() throws Exception {
        System.out.println("\n---------- REGISTER COURSE ----------");

        if (departments.isEmpty()) {
            System.out.println("A department is required before registering a course.");
            if (readYesNo("Register a department now?")) {
                registerDepartment();
            } else {
                return;
            }
        }

        if (teachers.isEmpty()) {
            System.out.println("A teacher is required before registering a course.");
            if (readYesNo("Register a teacher now?")) {
                registerTeacher();
            } else {
                return;
            }
        }

        String id = readText("Course ID: ");
        checkUniqueId(id);

        String code = readText("Course code: ");
        String title = readText("Course title: ");
        int hours = readInt("Credit hours (1-6): ");
        Department department = chooseDepartment();
        Teacher teacher = chooseTeacher();

        Course course = new Course(id, code, title, hours, department, teacher);
        course.validate();
        courses.add(course);

        System.out.println("Course registered successfully.");
        course.printDetails();
    }

    private static void registerClassroom() throws Exception {
        System.out.println("\n---------- REGISTER CLASSROOM ----------");

        String id = readText("Classroom ID: ");
        checkUniqueId(id);

        String building = readText("Building name: ");
        String room = readText("Room number: ");
        int capacity = readInt("Capacity: ");
        int floor = readInt("Floor number: ");
        boolean available = readYesNo("Is available");

        Classroom classroom = new Classroom(id, building, room, capacity, floor, available);
        classroom.validate();
        classrooms.add(classroom);

        System.out.println("Classroom registered successfully.");
        classroom.printDetails();
    }

    private static void registerEnrollment() throws Exception {
        System.out.println("\n---------- ENROLL STUDENT ----------");

        if (students.isEmpty()) {
            System.out.println("No student exists.");
            if (readYesNo("Register a student now?")) {
                registerStudent();
            } else {
                return;
            }
        }

        if (courses.isEmpty()) {
            System.out.println("No course exists.");
            if (readYesNo("Register a course now?")) {
                registerCourse();
            } else {
                return;
            }
        }

        String id = readText("Enrollment ID: ");
        checkUniqueId(id);

        Student student = chooseStudent();
        Course course = chooseCourse();
        LocalDate date = readDate("Enrollment date (YYYY-MM-DD): ");
        String semester = readText("Semester: ");
        String status = readText("Enrollment status (ACTIVE/INACTIVE/COMPLETED): ").toUpperCase();

        for (Enrollment existing : enrollments) {
            if (existing.getStudent() == student &&
                    existing.getCourse() == course &&
                    existing.getSemester().equalsIgnoreCase(semester) &&
                    existing.isActive()) {
                throw new DuplicateRecordException("This student already has an active enrollment in this course and semester.");
            }
        }

        Enrollment enrollment = new Enrollment(id, student, course, date, semester, status);
        enrollment.validate();
        enrollments.add(enrollment);

        System.out.println("Enrollment registered successfully.");
        enrollment.printDetails();
    }

    private static void registerAssessment() throws Exception {
        System.out.println("\n---------- RECORD ASSESSMENT ----------");

        if (enrollments.isEmpty()) {
            System.out.println("No enrollment exists.");
            if (readYesNo("Register an enrollment now?")) {
                registerEnrollment();
            } else {
                return;
            }
        }

        String id = readText("Assessment ID: ");
        checkUniqueId(id);

        Enrollment enrollment = chooseEnrollment();
        String type = readText("Assessment type: ");
        double maximum = readDouble("Maximum score: ");
        double obtained = readDouble("Obtained score: ");
        LocalDate date = readDate("Assessment date (YYYY-MM-DD): ");

        Assessment assessment = new Assessment(id, enrollment, type, maximum, obtained, date);
        assessment.validate();
        assessments.add(assessment);

        System.out.println("Assessment recorded successfully.");
        assessment.printDetails();
    }

    private static void registerPayment() throws Exception {
        System.out.println("\n---------- RECORD FEE PAYMENT ----------");

        if (students.isEmpty()) {
            System.out.println("No student exists.");
            if (readYesNo("Register a student now?")) {
                registerStudent();
            } else {
                return;
            }
        }

        String id = readText("Payment ID: ");
        checkUniqueId(id);

        Student student = chooseStudent();
        BigDecimal amount = readMoney("Amount: ");
        LocalDate date = readDate("Payment date (YYYY-MM-DD): ");
        String method = readText("Payment method (CASH/MOBILE MONEY/BANK TRANSFER/CARD): ").toUpperCase();
        String receipt = readText("Receipt number: ");

        for (FeePayment payment : payments) {
            if (payment.getReceiptNumber().equalsIgnoreCase(receipt)) {
                throw new DuplicateRecordException("Receipt number already exists.");
            }
        }

        FeePayment payment = new FeePayment(id, student, amount, date, method, receipt);
        payment.validate();
        payments.add(payment);

        System.out.println("Payment recorded successfully.");
        payment.printDetails();
    }

    private static void registerBook() throws Exception {
        System.out.println("\n---------- REGISTER LIBRARY BOOK ----------");

        String id = readText("Book ID: ");
        checkUniqueId(id);

        String isbn = readText("ISBN: ");
        String title = readText("Book title: ");
        String author = readText("Author: ");
        int total = readInt("Total copies: ");
        int available = readInt("Available copies: ");

        LibraryBook book = new LibraryBook(id, isbn, title, author, total, available);
        book.validate();
        books.add(book);

        System.out.println("Library book registered successfully.");
        book.printDetails();
    }
    private static void borrowBook() throws Exception {
        System.out.println("\n---------- BORROW LIBRARY BOOK ----------");

        if (students.isEmpty()) {
            System.out.println("No student exists.");
            if (readYesNo("Register a student now?")) {
                registerStudent();
            } else {
                return;
            }
        }

        if (books.isEmpty()) {
            System.out.println("No library book exists.");
            if (readYesNo("Register a book now?")) {
                registerBook();
            } else {
                return;
            }
        }

        String id = readText("Loan ID: ");
        checkUniqueId(id);

        Student student = chooseStudent();
        LibraryBook book = chooseBook();

        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("No available copies of this book.");
        }

        LocalDate borrowedDate = readDate("Borrowed date (YYYY-MM-DD): ");
        LocalDate dueDate = readDate("Due date (YYYY-MM-DD): ");

        BookLoan loan = new BookLoan(id, student, book, borrowedDate, dueDate, null);
        loan.validate();

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        loans.add(loan);

        System.out.println("Book borrowed successfully.");
        loan.printDetails();
    }

    private static void returnBook() throws Exception {
        System.out.println("\n---------- RETURN LIBRARY BOOK ----------");

        List<BookLoan> activeLoans = new ArrayList<>();

        for (BookLoan loan : loans) {
            if (loan.isActive()) {
                activeLoans.add(loan);
            }
        }

        if (activeLoans.isEmpty()) {
            throw new RecordNotFoundException("There are no active book loans.");
        }

        System.out.println("Active loans:");
        for (int i = 0; i < activeLoans.size(); i++) {
            System.out.println((i + 1) + ". " + activeLoans.get(i).getRegistrationSummary());
        }

        int choice = readInt("Select loan to return: ");

        if (choice < 1 || choice > activeLoans.size()) {
            throw new RecordNotFoundException("Invalid loan selection.");
        }

        BookLoan loan = activeLoans.get(choice - 1);
        LocalDate returnedDate = readDate("Return date (YYYY-MM-DD): ");

        if (returnedDate.isBefore(loan.getBorrowedDate())) {
            throw new ValidationException("Return date cannot be before the borrowed date.");
        }

        loan.setReturnedDate(returnedDate);
        loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() + 1);

        System.out.println("Book returned successfully.");
        loan.printDetails();
    }

    private static void viewAllRecords() {
        System.out.println("\n============== ALL REGISTERED RECORDS ==============");

        printCollection("DEPARTMENTS", departments);
        printCollection("STUDENTS", students);
        printCollection("TEACHERS", teachers);
        printCollection("ADMINISTRATORS", administrators);
        printCollection("COURSES", courses);
        printCollection("CLASSROOMS", classrooms);
        printCollection("ENROLLMENTS", enrollments);
        printCollection("ASSESSMENTS", assessments);
        printCollection("FEE PAYMENTS", payments);
        printCollection("LIBRARY BOOKS", books);
        printCollection("BOOK LOANS", loans);
    }

    private static void printCollection(String title, List<?> records) {
        System.out.println("\n---------- " + title + " ----------");

        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Object record : records) {
            System.out.println(record);
        }
    }

    private static void searchRecord() throws RecordNotFoundException {
        System.out.println("\n---------- SEARCH RECORD BY ID ----------");

        String id = readText("Enter record ID: ");

        for (Identifiable record : allIdentifiableRecords()) {
            if (record.getId().equalsIgnoreCase(id)) {
                System.out.println("Record found:");
                System.out.println("ID: " + record.getId());
                System.out.println("Name: " + record.getDisplayName());
                System.out.println("Active: " + record.isActive());
                return;
            }
        }

        throw new RecordNotFoundException("No record found with ID: " + id);
    }

    private static void updateRecord() throws Exception {
        System.out.println("\n---------- UPDATE EXISTING RECORD ----------");

        String id = readText("Enter the Person ID to update: ");

        for (Student student : students) {
            if (student.getPersonId().equalsIgnoreCase(id)) {
                System.out.println(student);
                if (readYesNo("Update this student's email?")) {
                    String newEmail = readText("New email: ");
                    student.setEmail(newEmail);
                    student.validate();
                    System.out.println("Student updated successfully.");
                }
                return;
            }
        }

        for (Teacher teacher : teachers) {
            if (teacher.getPersonId().equalsIgnoreCase(id)) {
                System.out.println(teacher);
                if (readYesNo("Update this teacher's email?")) {
                    String newEmail = readText("New email: ");
                    teacher.setEmail(newEmail);
                    teacher.validate();
                    System.out.println("Teacher updated successfully.");
                }
                return;
            }
        }

        for (Administrator administrator : administrators) {
            if (administrator.getPersonId().equalsIgnoreCase(id)) {
                System.out.println(administrator);
                if (readYesNo("Update this administrator's email?")) {
                    String newEmail = readText("New email: ");
                    administrator.setEmail(newEmail);
                    administrator.validate();
                    System.out.println("Administrator updated successfully.");
                }
                return;
            }
        }

        throw new RecordNotFoundException("No person found with ID: " + id);
    }

    private static void studentAcademicSummary() throws Exception {
        System.out.println("\n---------- STUDENT ACADEMIC SUMMARY ----------");

        Student student = chooseStudent();

        System.out.println("\nStudent details:");
        student.printDetails();

        System.out.println("\nEnrollments:");

        boolean foundEnrollment = false;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent() == student) {
                System.out.println(enrollment.getRegistrationSummary());
                foundEnrollment = true;

                for (Assessment assessment : assessments) {
                    if (assessment.getEnrollment() == enrollment) {
                        System.out.println("  " + assessment.getRegistrationSummary());
                    }
                }
            }
        }

        if (!foundEnrollment) {
            System.out.println("No enrollments found for this student.");
        }
    }

    private static void studentPaymentHistory() throws Exception {
        System.out.println("\n---------- STUDENT PAYMENT HISTORY ----------");

        Student student = chooseStudent();

        boolean found = false;
        BigDecimal total = BigDecimal.ZERO;

        for (FeePayment payment : payments) {
            if (payment.getStudent() == student) {
                System.out.println(payment.getRegistrationSummary()
                        + " | Date: " + payment.getPaymentDate()
                        + " | Method: " + payment.getPaymentMethod());

                total = total.add(payment.getAmount());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No payments found for this student.");
        } else {
            System.out.println("Total payments: " + total);
        }
    }

    private static void viewAvailableBooks() {
        System.out.println("\n---------- AVAILABLE LIBRARY BOOKS ----------");

        boolean found = false;

        for (LibraryBook book : books) {
            if (book.getAvailableCopies() > 0) {
                System.out.println(book.getRegistrationSummary());
                found = true;
            }
        }

        if (!found) {
            System.out.println("There are currently no available books.");
        }
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n---------- POLYMORPHISM DEMONSTRATION ----------");

        List<Person> people = new ArrayList<>();
        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);

        if (people.isEmpty()) {
            System.out.println("Register a student, teacher or administrator first.");
        } else {
            System.out.println("\nSuperclass polymorphism using Person:");

            for (Person person : people) {
                System.out.println(person.getRoleDescription()
                        + " | " + person.getDisplayName()
                        + " | Active: " + person.isActive());
            }
        }

        List<Identifiable> records = allIdentifiableRecords();

        if (records.isEmpty()) {
            System.out.println("No identifiable records exist yet.");
        } else {
            System.out.println("\nInterface polymorphism using Identifiable:");

            for (Identifiable record : records) {
                System.out.println("ID: " + record.getId()
                        + " | Name: " + record.getDisplayName()
                        + " | Active: " + record.isActive());
            }
        }
    }
}