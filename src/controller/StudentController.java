package controller;

import exception.StudentException;
import model.dto.StudentRequestDto;
import model.dto.StudentResponseDto;
import model.service.StudentService;
import view.StudentView;

public class StudentController {
    private final StudentView studentView;
    private final StudentService studentService;

    public StudentController(StudentView studentView, StudentService studentService) {
        this.studentView = studentView;
        this.studentService = studentService;
    }

    public void create(){
        try {
            StudentRequestDto request = studentView.displayStudentCreateDto();
            StudentResponseDto studentResponseDto = studentService.createStudent(request);
            studentView.displaySingleStudent(studentResponseDto);
            System.out.println("\n✓ Student created successfully!");

        } catch (StudentException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Unexpected error: " + e.getMessage());
        }
    }

    public void update() {
        try {
            int id = studentView.getStudentIdForUpdate();

            // First, show the current student data
            StudentResponseDto currentStudent = studentService.getStudentById(id);
            System.out.println("\nCurrent Student Information:");
            studentView.displaySingleStudent(currentStudent);

            // Get new data
            StudentRequestDto request = studentView.displayStudentUpdateDto();
            StudentResponseDto updatedStudent = studentService.updateStudent(id, request);

            System.out.println("\nUpdated Student Information:");
            studentView.displaySingleStudent(updatedStudent);
            System.out.println("\n✓ Student updated successfully!");

        } catch (StudentException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Unexpected error: " + e.getMessage());
        }
    }

    public void removeById(){
        try {
            int id = studentView.removeStudent();
            studentService.removeStudentById(id);
            System.out.println("\n✓ Student removed successfully!");

        } catch (StudentException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Unexpected error: " + e.getMessage());
        }
    }

    // NEW METHOD: Search student by ID
    public void searchById() {
        try {
            int id = studentView.getStudentIdForSearch();
            StudentResponseDto student = studentService.getStudentById(id);

            System.out.println("\n=== Student Found ===");
            studentView.displaySingleStudent(student);

        } catch (StudentException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Unexpected error: " + e.getMessage());
        }
    }

    // IMPROVED METHOD: List students with pagination navigation (Next/Previous)
    public void showAllStudents(){
        try {
            int currentOffset = 0;
            int limit = 10;
            boolean continueViewing = true;

            while (continueViewing) {
                // Get students for current page
                var students = studentService.getAllStudents(currentOffset, limit);

                // Display students
                if (students.isEmpty()) {
                    System.out.println("\n! No students found on this page.");
                    if (currentOffset > 0) {
                        System.out.println("! You've reached the end. Going back to previous page...");
                        currentOffset = Math.max(0, currentOffset - limit);
                        continue;
                    } else {
                        System.out.println("! No students in the database yet.");
                        break;
                    }
                }

                studentView.displayStudentList(students);

                // Show pagination info
                int currentPage = (currentOffset / limit) + 1;
                int startRecord = currentOffset + 1;
                int endRecord = currentOffset + students.size();

                System.out.println("\n--- Page " + currentPage + " (Records " + startRecord + "-" + endRecord + ") ---");

                // Get navigation choice
                int choice = studentView.showPaginationMenu();

                switch (choice) {
                    case 1 -> {
                        // Next page
                        currentOffset += limit;
                    }
                    case 2 -> {
                        // Previous page
                        currentOffset = Math.max(0, currentOffset - limit);
                    }
                    case 3 -> {
                        // Change page size
                        limit = studentView.getPageSize();
                        currentOffset = 0; // Reset to first page
                    }
                    case 4 -> {
                        // Go to specific page
                        int pageNumber = studentView.getPageNumber();
                        currentOffset = (pageNumber - 1) * limit;
                        if (currentOffset < 0) currentOffset = 0;
                    }
                    case 0 -> {
                        // Exit pagination
                        continueViewing = false;
                    }
                }
            }

        } catch (StudentException e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n✗ Unexpected error: " + e.getMessage());
        }
    }

    public void run(){
        while (true){
            int option = studentView.showMenuAndGetOption();
            switch (option) {
                case 1 -> create();
                case 2 -> showAllStudents();
                case 3 -> update();
                case 4 -> removeById();
                case 5 -> searchById();
                case 0 -> {
                    System.out.println("\nThank you for using Student Management System!");
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("\n✗ Invalid option! Please try again.");
            }
        }
    }
}