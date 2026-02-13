package view;

import model.dto.StudentRequestDto;
import model.dto.StudentResponseDto;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class StudentView {

    private final static Scanner scanner = new Scanner(System.in);
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    private String getValidFullName() {
        while (true) {
            System.out.print("[+] Enter student full name: ");
            String fullName = scanner.nextLine().trim();

            // Validation: Name cannot be empty
            if (fullName.isEmpty()) {
                System.out.println("✗ Name cannot be empty! Please try again.");
                continue;
            }

            // Validation: Name must be at least 3 characters
            if (fullName.length() < 3) {
                System.out.println("✗ Name must be at least 3 characters! Please try again.");
                continue;
            }

            // Validation: Name should only contain letters and spaces
            if (!fullName.matches("[a-zA-Z ]+")) {
                System.out.println("✗ Name should only contain letters and spaces! Please try again.");
                continue;
            }

            return fullName;
        }
    }


    private String getValidGender() {
        while (true) {
            System.out.print("[+] Enter student gender (MALE/FEMALE): ");
            String gender = scanner.nextLine().trim().toUpperCase();

            // Validation: Gender must be MALE or FEMALE
            if (gender.equals("MALE") || gender.equals("FEMALE")) {
                return gender;
            }

            System.out.println("✗ Invalid gender! Please enter MALE or FEMALE.");
        }
    }

    /**
     * INPUT VALIDATION: Get valid birth date
     */
    private LocalDate getValidBirthDate() {
        while (true) {
            System.out.print("[+] Enter student date of birth (DD-MM-YYYY): ");
            String dateInput = scanner.nextLine().trim();

            try {
                // Parse the date
                LocalDate birthDate = LocalDate.parse(dateInput, DATE_FORMATTER);

                // Validation: Birth date cannot be in the future
                if (birthDate.isAfter(LocalDate.now())) {
                    System.out.println("✗ Birth date cannot be in the future! Please try again.");
                    continue;
                }

                // Validation: Birth date should be reasonable (not more than 100 years ago)
                if (birthDate.isBefore(LocalDate.now().minusYears(100))) {
                    System.out.println("✗ Birth date is too far in the past! Please try again.");
                    continue;
                }

                return birthDate;

            } catch (DateTimeParseException e) {
                System.out.println("✗ Invalid date format! Please use DD-MM-YYYY format (e.g., 15-03-2010).");
            }
        }
    }


    private int getValidStudentId(String action) {
        while (true) {
            System.out.println();
            System.out.print("[+] Enter student ID to " + action + ": ");
            String input = scanner.nextLine().trim();

            try {
                int id = Integer.parseInt(input);

                // Validation: ID must be positive
                if (id <= 0) {
                    System.out.println("✗ Student ID must be a positive number! Please try again.");
                    continue;
                }

                return id;

            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a valid number.");
            }
        }
    }


    public int[] getPaginationParams() {
        System.out.println("\n--- Pagination Options ---");

        // Get offset
        int offset = 0;
        while (true) {
            System.out.print("[+] Enter offset (starting position, default 0): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                offset = 0;
                break;
            }

            try {
                offset = Integer.parseInt(input);
                if (offset < 0) {
                    System.out.println("✗ Offset must be non-negative! Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a valid number.");
            }
        }

        // Get limit
        int limit = 10;
        while (true) {
            System.out.print("[+] Enter limit (number of records, default 10): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                limit = 10;
                break;
            }

            try {
                limit = Integer.parseInt(input);
                if (limit <= 0) {
                    System.out.println("✗ Limit must be positive! Please try again.");
                    continue;
                }
                if (limit > 100) {
                    System.out.println("✗ Limit cannot exceed 100! Please try again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a valid number.");
            }
        }

        return new int[]{offset, limit};
    }

    public int showPaginationMenu() {
        while (true) {
            System.out.println("\n┌─────────────────────────────────────┐");
            System.out.println("│     Pagination Navigation           │");
            System.out.println("├─────────────────────────────────────┤");
            System.out.println("│  1. Next Page                       │");
            System.out.println("│  2. Previous Page                   │");
            System.out.println("│  3. Change Page Size                │");
            System.out.println("│  4. Go to Page                      │");
            System.out.println("│  0. Back to Menu                    │");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("  Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice >= 0 && choice <= 4) {
                    return choice;
                } else {
                    System.out.println("\n✗ Invalid option! Please enter a number between 0 and 4.");
                }

            } catch (NumberFormatException e) {
                System.out.println("\n✗ Invalid input! Please enter a valid number.");
            }
        }
    }


    public int getPageSize() {
        while (true) {
            System.out.print("\n[+] Enter number of records per page (default 10, max 100): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return 10;
            }

            try {
                int pageSize = Integer.parseInt(input);
                if (pageSize <= 0) {
                    System.out.println("✗ Page size must be positive! Please try again.");
                    continue;
                }
                if (pageSize > 100) {
                    System.out.println("✗ Page size cannot exceed 100! Please try again.");
                    continue;
                }
                return pageSize;

            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a valid number.");
            }
        }
    }


    public int getPageNumber() {
        while (true) {
            System.out.print("\n[+] Enter page number to go to: ");
            String input = scanner.nextLine().trim();

            try {
                int pageNumber = Integer.parseInt(input);
                if (pageNumber <= 0) {
                    System.out.println("✗ Page number must be positive! Please try again.");
                    continue;
                }
                return pageNumber;

            } catch (NumberFormatException e) {
                System.out.println("✗ Invalid input! Please enter a valid number.");
            }
        }
    }

    public StudentRequestDto displayStudentCreateDto() {
        System.out.println("\n=== Create New Student ===");
        String fullName = getValidFullName();
        String gender = getValidGender();
        LocalDate birthDate = getValidBirthDate();

        return new StudentRequestDto(fullName, birthDate, gender);
    }

    public StudentRequestDto displayStudentUpdateDto() {
        System.out.println("\n=== Enter New Student Information ===");
        String fullName = getValidFullName();
        String gender = getValidGender();
        LocalDate birthDate = getValidBirthDate();

        return new StudentRequestDto(fullName, birthDate, gender);
    }

    public int getStudentIdForUpdate() {
        return getValidStudentId("update");
    }

    public int removeStudent() {
        return getValidStudentId("remove");
    }

    /**
     * NEW: Get student ID for search
     */
    public int getStudentIdForSearch() {
        return getValidStudentId("search");
    }

    public void displaySingleStudent(StudentResponseDto student) {
        Table table = new Table(4, BorderStyle.CLASSIC);
        table.addCell("Student Information", new CellStyle(CellStyle.HorizontalAlign.center), 4);
        table.addCell("ID");
        table.addCell(student.id().toString(), 3);
        table.addCell("Full Name");
        table.addCell(student.fullName(), 3);
        table.addCell("Gender");
        table.addCell(student.gender(), 3);
        table.addCell("Date of Birth");
        table.addCell(student.birthDate(), 3);
        System.out.println(table.render());
    }

    public void displayStudentList(List<StudentResponseDto> students) {
        if (students.isEmpty()) {
            System.out.println("\n! No students found in the specified range.");
            return;
        }

        Table table = new Table(4, BorderStyle.CLASSIC);
        table.addCell("Student List (" + students.size() + " records)",
                new CellStyle(CellStyle.HorizontalAlign.center), 4);

        String[] header = {"ID", "Full Name", "Gender", "Date of Birth"};
        for (String column : header) {
            table.addCell(column);
        }

        students.forEach(student -> {
            table.addCell(student.id().toString());
            table.addCell(student.fullName());
            table.addCell(student.gender());
            table.addCell(student.birthDate());
        });

        System.out.println(table.render());
    }

    public int showMenuAndGetOption() {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║   STUDENT MANAGEMENT SYSTEM - MENU     ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("  1. Create Student");
            System.out.println("  2. List Students");
            System.out.println("  3. Update Student");
            System.out.println("  4. Remove Student");
            System.out.println("  5. Search Student ");
            System.out.println("  0. Exit");
            System.out.println("─────────────────────────────────────────");
            System.out.print("  Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice >= 0 && choice <= 5) {  // Changed from 4 to 5
                    return choice;
                } else {
                    System.out.println("\n✗ Invalid option! Please enter a number between 0 and 5.");
                }

            } catch (NumberFormatException e) {
                System.out.println("\n✗ Invalid input! Please enter a valid number.");
            }
        }
    }
}