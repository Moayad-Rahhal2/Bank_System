package com.banksystem.app;

import com.banksystem.entity.Account;
import com.banksystem.service.AdminService;
import com.banksystem.service.AuthService;
import com.banksystem.service.BankService;
import com.banksystem.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;

// this project made by ChatGPT

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static final BankService bankService = new BankService();
    private static final AdminService adminService = new AdminService();


    public static void main(String[] args) {
        System.out.println("\uD83C\uDFE6 Welcome to Bank System!");

        while (true) {
            try {
                System.out.println("\n1️⃣ Register User");
                System.out.println("2️⃣ Open Account");
                System.out.println("3️⃣ Deposit");
                System.out.println("4️⃣ Withdraw");
                System.out.println("5️⃣ Transfer");
                System.out.println("6️⃣ Admin Mode");
                System.out.println("7\uFE0F⃣ Exit");

                System.out.print("\nChoose option: ");
                int option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1 -> registerUser();
                    case 2 -> openAccount();
                    case 3 -> deposit();
                    case 4 -> withdraw();
                    case 5 -> transfer();
                    case 6 -> adminMode();
                    case 7 -> {
                        System.out.println("👋 Goodbye!");
                        System.exit(0);
                    }
                    default -> System.out.println("❌ Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number for the menu option.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        if (!InputValidator.isNotEmpty(firstName)) {
            System.out.println("❌ First name cannot be empty.");
            return;
        }

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        if (!InputValidator.isNotEmpty(lastName)) {
            System.out.println("❌ Last name cannot be empty.");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        if (!InputValidator.isValidEmail(email)) {
            System.out.println("❌ Invalid email format.");
            return;
        }

        System.out.print("Phone (07Xxxxxxxx): ");
        String phone = scanner.nextLine();
        if (!InputValidator.isValidPhone(phone)) {
            System.out.println("❌ Invalid Jordanian phone number.");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (!InputValidator.isValidPassword(password)) {
            System.out.println("❌ Password must be 8-16 characters, include at least one number, one uppercase and one lowercase letter.");
            return;
        }

        if (authService.registerUser(firstName, lastName, email, phone, password)) {
            System.out.println("✅ User registered successfully.");
        }



    }

    private static void openAccount() {
        try {
            System.out.print("User ID: ");
            String uidInput = scanner.nextLine();
            if (!InputValidator.isValidId(uidInput)) {
                System.out.println("❌ Invalid User ID.");
                return;
            }
            int userId = Integer.parseInt(uidInput);
            System.out.print("Account type (CHECKING/SAVINGS): ");
            String type = scanner.nextLine();
            if (!InputValidator.isNotEmpty(type)) {
                System.out.println("❌ Account type cannot be empty.");
                return;
            }

            bankService.openAccount(userId, Account.AccountType.valueOf(type.toUpperCase()));
        } catch (NumberFormatException e) {
            System.out.println("❌ User ID must be a number.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid account type.");
        }
    }

    private static void deposit() {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine();

        if (!InputValidator.isValidAccountNumber(accountNumber)) {
            System.out.println("❌ Invalid account number. Must be exactly 12 digits.");
            return;
        }

        System.out.print("Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        if (!InputValidator.isPositiveAmount(amount)) {
            System.out.println("❌ Amount must be positive.");
            return;
        }

        bankService.deposit(accountNumber, amount, "Cash Deposit");
    }


    private static void withdraw() {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine();

        if (!InputValidator.isValidAccountNumber(accountNumber)) {
            System.out.println("❌ Invalid account number. Must be exactly 12 digits.");
            return;
        }

        System.out.print("Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        if (!InputValidator.isPositiveAmount(amount)) {
            System.out.println("❌ Amount must be positive.");
            return;
        }

        bankService.withdraw(accountNumber, amount, "ATM Withdrawal");
    }


    private static void transfer() {
        System.out.print("From account: ");
        String fromAccount = scanner.nextLine();

        if (!InputValidator.isValidAccountNumber(fromAccount)) {
            System.out.println("❌ Invalid 'From' account number. Must be exactly 12 digits.");
            return;
        }

        System.out.print("To account: ");
        String toAccount = scanner.nextLine();

        if (!InputValidator.isValidAccountNumber(toAccount)) {
            System.out.println("❌ Invalid 'To' account number. Must be exactly 12 digits.");
            return;
        }

        // Prevent transferring to the same account
        if (fromAccount.equals(toAccount)) {
            System.out.println("❌ Cannot transfer to the same account.");
            return;
        }

        System.out.print("Amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        if (!InputValidator.isPositiveAmount(amount)) {
            System.out.println("❌ Amount must be positive.");
            return;
        }

        bankService.transfer(fromAccount, toAccount, amount, "Account Transfer");
    }



    private static BigDecimal readAmount() {
        try {
            System.out.print("Amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            if (!InputValidator.isPositiveAmount(amount)) {
                System.out.println("❌ Amount must be positive.");
                return null;
            }

            return amount;
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid amount format.");
            return null;
        }
    }
    private static void adminMode() {
        while (true) {
            System.out.println("\n🛠️  Admin Mode");
            System.out.println("1️⃣ View All Users");
            System.out.println("2️⃣ Delete User by ID");
            System.out.println("3️⃣ View Accounts by User ID");
            System.out.println("4️⃣ Delete Account by Account Number");
            System.out.println("5️⃣ View All Transactions");
            System.out.println("6️⃣ Exit Admin Mode");

            System.out.print("\nChoose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    adminService.getAllUsers().forEach(user ->
                            System.out.println(user.getUserId() + " | " + user.getFirstName() + " " + user.getLastName() + " | " + user.getEmail())
                    );
                    break;

                case "2":
                    System.out.print("Enter User ID to delete: ");
                    String userIdInput = scanner.nextLine();
                    if (!InputValidator.isValidId(userIdInput)) {
                        System.out.println("❌ Invalid User ID.");
                        break;
                    }
                    int userId = Integer.parseInt(userIdInput);
                    if (!adminService.userExists(userId)) {
                        System.out.println("❌ User not found.");
                        break;
                    }
                    adminService.deleteUser(userId);
                    System.out.println("✅ User deleted.");
                    break;

                case "3":
                    System.out.print("Enter User ID to view accounts: ");
                    String uidInput = scanner.nextLine();
                    if (!InputValidator.isValidId(uidInput)) {
                        System.out.println("❌ Invalid User ID.");
                        break;
                    }
                    int uid = Integer.parseInt(uidInput);
                    if (!adminService.userExists(uid)) {
                        System.out.println("❌ User not found.");
                        break;
                    }
                    adminService.getAccountsByUserId(uid).forEach(acc ->
                            System.out.println(acc.getAccountId() + " | " + acc.getAccountNumber() + " | Balance: " + acc.getBalance())
                    );
                    break;

                case "4":
                    System.out.print("Enter 12-digit Account Number to delete: ");
                    String accNumInput = scanner.nextLine();
                    if (!InputValidator.isValidAccountNumber(accNumInput)) {
                        System.out.println("❌ Invalid Account Number format.");
                        break;
                    }
                    if (!adminService.accountExistsByNumber(accNumInput)) {
                        System.out.println("❌ Account not found.");
                        break;
                    }
                    Account account = adminService.getAccountByNumber(accNumInput);
                    adminService.deleteAccount(account.getAccountId());
                    System.out.println("✅ Account deleted.");
                    break;

                case "5":
                    adminService.getAllTransactions().forEach(tx ->
                            System.out.println(tx.getTransactionId() + " | " + tx.getAccount().getAccountNumber() + " | " +
                                    tx.getAmount() + " | " + tx.getTransactionType() + " | " + tx.getDescription())
                    );
                    break;

                case "6":
                    System.out.println("👋 Exiting Admin Mode...");
                    return;

                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }


}
