package projectWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    String username;
    String password;
    String role; // "admin" or "customer"

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role.toLowerCase();
    }
}

class Medicine {
    int id;
    String name;
    int quantity;
    double price;

    Medicine(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + quantity + " | Rs.  " + price;
    }
}

public class PharmacyManagementSystem {
    private List<User> users = new ArrayList<>();
    private List<Medicine> medicines = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private User loggedInUser = null;

    public PharmacyManagementSystem() {
        // Default Admin
        users.add(new User("admin", "admin123", "admin"));
    }

    public void register() {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();
        System.out.println("Entre the role");
        String role;
       

        do {
            System.out.println("Enter role (admin/customer):");
            role = scanner.next();
            if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("customer")) {
                System.out.println("Invalid role. Please enter 'admin' or 'customer'.");
            }
        } while (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("customer"));

        users.add(new User(username, password, role.toLowerCase()));
        System.out.println("User registered successfully!");
    }

    public void login() {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful! Welcome, " + user.username);
                return;
            }
        }
        System.out.println("Invalid credentials. Try again.");
    }

    public void addMedicine() {
        if (!isLoggedInAdmin()) return;

        System.out.println("Enter medicine ID:");
        int id = scanner.nextInt();

        for (Medicine medicine : medicines) {
            if (medicine.id == id) {
                System.out.println("Medicine ID already exists. Please try again.");
                return;
            }
        }

        System.out.println("Enter medicine name:");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        System.out.println("Enter price:");
        double price = scanner.nextDouble();

        medicines.add(new Medicine(id, name, quantity, price));
        System.out.println("Medicine added successfully!");
    }

    public void viewMedicines() {
        if (medicines.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }
        System.out.println("ID | Name | Quantity | Price");
        for (Medicine medicine : medicines) {
            System.out.println(medicine);
        }
    }

    public void updateStock() {
        if (!isLoggedInAdmin()) return;

        System.out.println("Enter medicine ID to update:");
        int id = scanner.nextInt();

        for (Medicine medicine : medicines) {
            if (medicine.id == id) {
                System.out.println("Enter new quantity:");
                medicine.quantity = scanner.nextInt();
                System.out.println("Stock updated successfully!");
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    public void placeOrder() {
        if (loggedInUser == null || !loggedInUser.role.equals("customer")) {
            System.out.println("Only customers can place orders.");
            return;
        }

        System.out.println("Enter medicine ID to order:");
        int id = scanner.nextInt();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();

        for (Medicine medicine : medicines) {
            if (medicine.id == id) {
                if (medicine.quantity >= quantity) {
                    medicine.quantity -= quantity;
                    System.out.println("Order placed successfully! Total cost: Rs.  " + (quantity * medicine.price));
                } else {
                    System.out.println("Insufficient stock.");
                }
                return;
            }
        }
        System.out.println("Medicine not found.");
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Add Medicine\n4. View Medicines\n5. Update Stock\n6. Place Order\n7. Logout\n8. Exit");
            System.out.println("Enter your choice:");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
                continue;
            }
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    addMedicine();
                    break;
                case 4:
                    viewMedicines();
                    break;
                case 5:
                    updateStock();
                    break;
                case 6:
                    placeOrder();
                    break;
                case 7:
                    loggedInUser = null;
                    System.out.println("Logged out successfully.");
                    break;
                case 8:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }

    private boolean isLoggedInAdmin() {
        if (loggedInUser == null) {
            System.out.println("You need to log in first.");
            return false;
        }
        if (!loggedInUser.role.equals("admin")) {
            System.out.println("Only admins can perform this action.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        PharmacyManagementSystem obj = new PharmacyManagementSystem();
        obj.run();
    }
}
