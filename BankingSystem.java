
package BankingSystem;

import java.util.Scanner;
public class BankingSystem {
 
// Static variables to allow the variables to be used everywhere
static Scanner input = new Scanner(System.in); 

// Tracks the current customer's account number and password
static String currentAccountNumber;
static String currentPassword;

// Arrays to store customer data in memory
static String[] accountNumbers = new String[100];
static String[] passwords = new String[100];
static String[] names = new String[100];
static double[] balances = new double [100];
static int accountCount = 0;

// Admin login credentials
static final String ADMIN_USERNAME = "admin";
static final String ADMIN_PASSWORD = "admin123";


    public static void mainMenu(){
    
        // Loop to keep showing the menu until the user exits
        while (true){
                System.out.print("=================== MAIN MENU ===================\n"+
                                 "1. Sign Up / Create New Account\n"+
                                 "2. Login\n"+
                                 "3. Administrative Menu\n"+ 
                                 "4. Exit\n"+
                                 "Choose an option (1-4): ");

    
        int choice = input.nextInt();       
        input.nextLine(); // Consume leftover line
        
        
        switch (choice){
            case 1:  createAccount(); 
            break;
            case 2:  login();
            break;
            case 3:  // Prompt the user to login to admin menu
                    System.out.print("Enter admin username: ");
                    String user = input.nextLine().trim();
                    System.out.print("Enter admin password: ");
                    String pass = input.nextLine().trim();
                   
                    // Verify admin credentials
                    if(user.equals(ADMIN_USERNAME) && pass.equals(ADMIN_PASSWORD))
                        adminMenu(); 
                    else {
                        System.out.println("Error: Invalid admin credentials!");}
            break;
            case 4:  System.out.println("\nExiting program... Goodbye!\n"); 
            return;
            default: System.out.println("\nError: Invalid input. Please choose between 1–4.\n");}

      } 
    }

    public static void createAccount(){
    System.out.print("Enter your full name: ");
    String name = input.nextLine();
    
    char gender = ' ';  // Variable to store customer's gender
    
    // Loop until the user provides a valid gender (M/F)
    do {
    System.out.print("Enter your gender (M/F): ");
    gender = Character.toUpperCase(input.nextLine().charAt(0));
    
    if(gender != 'M' && gender !='F') 
        System.out.println("\nInvalid input. Please enter 'M' or 'F'.\n");
    
    }
    while (gender != 'M' && gender !='F'); // Repeat until valid input is entered
     
String phone;
while (true) {
     // Prompt the user to enter their phone number (UAE format without +971)
    System.out.print("Enter your phone number (+971): ");
    phone = input.nextLine().trim(); // Remove any leading/trailing spaces

    // Check that the phone number is exactly 9 digits
    if (phone.length() != 9) {
        System.out.println("\nInvalid phone number. Must be 9 digits only.\n");
        continue;} // Skip further checks and asks again

    boolean allDigits = true;
    for (int i = 0; i < phone.length(); i++) {
        if (!Character.isDigit(phone.charAt(i))) {
            allDigits = false;
            break; // Stop checking if a non-digit is found
        }
    }
    
     // If any character was not a digit, print an error and repeat
    if (!allDigits) {
        System.out.println("\nInvalid phone number. Only digits are allowed.\n");
        continue;}

    break;  // Phone number is valid --> Exit Loop
}


    String password = "";
    while (true){
    
    // Define allowed special characters for password
    String specialChar = "!@#$%"; 
    
    // Display password requirements to the user
    System.out.print( "Create password (requirements): \n"+
                      " |*| At least 8 characters long\n"+
                      " |*| Must include at least one special character from\n" + 
                      specialChar + "\n" +
                      "Enter password: " );

    
    password = input.nextLine().trim();
   
    // Check minimum length requirement
    if (password.length()<8){
       System.out.println("\nPassword must be at least 8 characters long.\n");
       continue;}
    
    boolean hasCharacters = false;

     // Check if password contains at least one required special character
    for (int i = 0; i < specialChar.length(); i++){
    if (password.contains(""+specialChar.charAt(i))){
        hasCharacters = true; 
        break; // Found a valid special character --> Exit Loop
    }}
    
    // If password does not contain a special character, ask again    
    if (!hasCharacters)
        System.out.println("\nPassword must contain at least one special character from " + specialChar + ".\n");
    else 
        break;
      }

    
String accountNumber;
boolean isUnique;

do {
    isUnique = true;
    // Generate a random six-digit number 
    accountNumber = "" + (int)(100000 + (Math.random() * 900000));
    
     // Check for duplicates among existing account numbers
    for (int i = 0; i < accountCount; i++) { 
        if (accountNumbers[i].equals(accountNumber)) {
            isUnique = false; // Duplicate found, New account number will be generated
            break;
        }
    }
} while (!isUnique); // Repeat until a unique account number is generated

// Save the new account's details in the arrays and each index is a single customer
accountNumbers[accountCount] = accountNumber;
passwords[accountCount] = password;
names[accountCount] = name;
accountCount++; //Incerement the number of accounts

// Display confirmation message with customer's name and generated account number
System.out.println("Account created successfully!\n"+
                   "Name: "+ name +
                   "\nYour Account Number: " + accountNumber);
   

// Save current session info so the customer doesn't need to login again
currentAccountNumber = accountNumber;
currentPassword = password;

// Automatically redirect the newly created account to the customer menu
customerMenu(); 
}
        

public static void login() {
    int attempts = 0; // Tracks the number of failed login attempts
    boolean found = false;

    // Loop until successful login or maximum attempts reached
    while (attempts < 3 && !found) {
            System.out.print("=================== LOGIN MENU ===================\n"+
                             "Enter your account number: ");
            
            String account = input.nextLine().trim();

            System.out.print("Enter your password: ");
            String userPassword = input.nextLine().trim();

         // Check credentials against all existing accounts
        for (int i = 0; i < accountCount; i++) {
            if (accountNumbers[i].equals(account) && passwords[i].equals(userPassword)) {
                
                // Login successful    
                System.out.println("Successful Login!\n"+
                            "Welcome, " + names[i] + "!\n");
                
                // Set current session variables
                currentAccountNumber = account;
                currentPassword = userPassword;
                found = true;
                
                // Redirect to customer menu for account operations
                customerMenu();
                
                break; // Exit the loop after successful login
              }}
 
        // Handle failed login attempts
        if (!found) {
            attempts++; // Increment failed attempts
            if (attempts < 3) {
                System.out.println("Invalid account number or password. You have " 
                                   + (3 - attempts) + " attempt(s) remaining.\n");} 
            else {
                System.out.println("Too many failed attempts. Try again later.\n");}
    }}}


public static void adminMenu(){
    
    // Keep showing the admin menu until logout
    while(true){
        System.out.print("=================== ADMIN MENU ===================\n"+
                         "1. View all accounts\n"+
                         "2. Delete an account\n"+
                         "3. Logout\n"+
                         "Choose an option (1-3):");
        
        int choice = input.nextInt();
        input.nextLine();
        
        switch(choice){
            case 1:  // Loop through all stored accounts and display account number, name, and balance
                for(int i=0;i<accountCount;i++){
                    System.out.println("Account #" + accountNumbers[i] + " | Name: " + names[i] + " | Balance: " + balances[i]);}
            break;
            case 2:  // Delete a specific account
                System.out.print("Enter account number to delete: ");
                String acc = input.nextLine().trim();
                
                boolean found = false; // Check if the account exists
                for(int i=0; i<accountCount; i++){
                    
                    if(accountNumbers[i].equals(acc)){  // Shift all array elements left to remove the account
                       for(int j=i;j<accountCount-1;j++){
                            accountNumbers[j] = accountNumbers[j+1];
                            passwords[j] = passwords[j+1];
                            names[j] = names[j+1];
                            balances[j] = balances[j+1];
                        }
                       
                accountCount--; // Reduce total account count
                System.out.println("\nAccount successful deleted!");
                found = true;
                break;
                    }
                }
                
                if(!found) // Print message if account number not found
                    System.out.println("\nError: Account not found!");
            break;
            case 3:  System.out.println("Logging out..."); return;
            default: System.out.println("\nError: Invalid input!");
        }
    }
}

    
    public static void customerMenu(){
    
        while(true){ // Keep showing the menu until the customer logs out
            System.out.print("=================== CUSTOMER MENU ===================\n"+
                             "1. View Balance\n"+
                             "2. Deposit Money\n"+
                             "3. Withdraw Money\n"+
                             "4. Transfer Money\n"+
                             "5. Delete Account\n"+
                             "6. Logout\n"+
                             "Choose an option (1-6): ");

        
        int customerChoice = input.nextInt();
        input.nextLine();
     
    switch (customerChoice){
            case 1: viewBalance(); break; // Show current balance
            case 2: depositMoney(); break; // Deposit funds
            case 3: withdrawMoney(); break; // Withdraw funds
            case 4: transferMoney(); break; // Transfer funds to another account
            case 5: deleteAccount(); break; // Delete own account
            case 6:  System.out.println("\nLogging out... Returning to Main Menu...\n");
                     return;
            default: System.out.println("\nError: Invalid input. Please choose between 1–6.\n");
            } 
        }}
    
    // Method finds the index of a given account number in the account arrays
    public static int findAccountIndex(String accNumber){ 
        for (int i=0; i<accountCount; i++){
            if (accountNumbers[i].equals(accNumber))
                return i;  }  // Return index if account exists
        return -1; // Return -1 if account does not exist
}

//Displays the current balance of the logged-in customer    
public static void viewBalance(){
    int index = findAccountIndex(currentAccountNumber); // Find index of current account
    System.out.printf("Your current balance is: AED %.2f\n" ,balances[index]);
}

//Allows the customer to deposit money into their account
public static void depositMoney(){
    int index = findAccountIndex(currentAccountNumber);
    System.out.print("Enter amount to deposit: AED ");
    double amount = input.nextDouble();
    input.nextLine();
    
     // Validate deposit amount
    if (amount<=0){
        System.out.println("Error: Invalid amount.\n");
        return;  }
    
    // Update account balance
    balances[index] += amount; 
    System.out.printf("Deposit successful! New balance: AED %.2f\n", balances[index]);
}

//Allows the customer to withdraw money from their account
public static void withdrawMoney(){
    int index = findAccountIndex(currentAccountNumber);
    System.out.print("Enter amount to withdraw: AED ");
    double amount = input.nextDouble();
    input.nextLine();
    
     // Validate withdrawal amount
    if (amount<=0){
        System.out.println("Error: Invalid amount.\n");
        return;  }
    
    // Check if balance is sufficient
    if (amount > balances[index]){
        System.out.println("Error: Insufficient balance.\n");
        return; }
    
     // Deduct amount from balance
    balances[index] -= amount;
    System.out.printf("Withdrawal successful! Remaining balance: AED %.2f\n", balances[index]);   
}

//Allows the logged-in customer to transfer money to another account
public static void transferMoney(){
    int senderIndex = findAccountIndex(currentAccountNumber); // Find sender account index
   
    // Prompt for recipient account number
    System.out.print("Enter recipient account number: ");
    String recipentAccount = input.nextLine().trim();
    int recipientIndex = findAccountIndex(recipentAccount); // Find recipient account index

    //Check if recipient account exists
    if (recipientIndex == -1){
        System.out.println("Error: Recipient account not found.\n");
        return; }
    
    // Prompt the user for transfer amount
    System.out.print("Enter amount to transfer: AED ");
    double amount = input.nextDouble();
    input.nextLine();
    
    // Validate transfer amount
    if (amount<=0){
        System.out.println("Error: Invalid amount.\n");
        return;  }
    
    // Check if sender has enough balance
    if (amount > balances[senderIndex]){
        System.out.println("Error: Insufficient balance.\n");
        return; }
    
    // Update balances for sender and recipient
    balances[senderIndex] -= amount;
    balances[recipientIndex] += amount;
    
    System.out.printf("Transfer successful! You sent AED %.2f to %s (Account #: %s)\n", amount,names[recipientIndex], recipentAccount );
    System.out.printf("Your remaining balance: AED %.2f \n", balances[senderIndex]);
}

// Allows the logged-in customer to delete their account
public static void deleteAccount(){
    int index = findAccountIndex(currentAccountNumber); // Index of the current account
    
    // Confirmation message to delete the account
    System.out.print("Are you sure you want to delete your account (Y/N): ");
    char confirm = Character.toUpperCase(input.nextLine().charAt(0));
    
    if (confirm == 'Y'){
        // Shift all arrays to the left to remove the account
        for (int i=index; i < accountCount - 1; i++){
            accountNumbers[i] = accountNumbers[i + 1];
            passwords[i] = passwords[i + 1];
            names[i] = names[i + 1];
            balances[i] = balances[i + 1];}
        
        accountCount--; // Reduce total account count
        System.out.println("Account deleted successfully.\n");} 
    else 
        System.out.println("Account deletion cancelled.\n");
    
}



    public static void main(String[] args) {

    mainMenu();}
    
}
