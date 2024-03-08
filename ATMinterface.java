import java.util.Scanner;
import java.util.ArrayList;

// Class for depositing the amount
class deposit {
    Scanner depSc = new Scanner(System.in);
    ArrayList<String> deps = new ArrayList<>();
    protected double amount;

    // Method to deposit amount into the account
    public void getDeposit() {
        System.out.println("Enter amount to deposit : ");
        double balance = 0;
        balance = depSc.nextDouble();
        this.amount += balance;
        deps.add("Amount deposited : " + balance); //Adding transaction to history using Arraylist

        // Handling invalid amount
        if (this.amount < 0) {
            System.out.println("Invalid figure");
            getDeposit();
        }
    }

    // Method to show deposit
    public void showDeposit() {
        System.out.println("Balance : " + this.amount);
    }

    // Method to pass the amount to the subclass and main function
    public double getamount() {
        return this.amount;
    }
}

// Class for withdrawing the amount
class withdraw extends deposit {
    Scanner withSc = new Scanner(System.in);
    private double withdrawalAmount;
    ArrayList<String> withs = new ArrayList<>();

    //Constructor passing the amount
    public withdraw(double amount) {
        this.amount = amount;
    }

    public withdraw() {

    }

    // Method to take withdrawal amount from user
    public void getWithdrawal(deposit obj) {
        System.out.println("Enter amount to withdraw : ");
        withdrawalAmount = withSc.nextDouble();

        if (withdrawalAmount > obj.getamount()) {

            System.out.println("Insufficient balance");
            getWithdrawal(obj);
        }

        // Handling edge cases
        else if(withdrawalAmount<0) 
        {
            System.out.println("Enter Valid amount");
            getWithdrawal(obj);
        }
        else {
            obj.amount -= withdrawalAmount;
            withs.add("Amount withdrawn : " + withdrawalAmount); //Adding transaction to history using Arraylist
            System.out.println("Amount Withdrawn Successfully!");
        }
        System.out.println("Current amount: " + obj.amount);
    }
}

class transactionHistory {
    private ArrayList<String> deps;    // Arraylists for maintaing transactions
    private ArrayList<String> withs;   // Arraylists for maintaing transactions
    private ArrayList<String> transac; // Arraylists for maintaing transactions

    //Constructor for assigning objects to arraylist
    public transactionHistory(ArrayList<String> deps, ArrayList<String> withs, ArrayList<String> transac) {
        this.deps = deps;
        this.withs = withs;
        this.transac = transac;
    }

    // Method to display history
    public void showHistory() {
        System.out.println("1. Deposit history:");
        for (String dep : deps) {
            System.out.println(dep);
        }
        System.out.println("--------------------------------------");

        System.out.println("2. Withdrawal history:");
        for (String with : withs) {
            System.out.println(with);
        }
        System.out.println("--------------------------------------");
        System.out.println("3. Transfer history:");
        for (String tr : transac) {
            System.out.println(tr);
        }
        System.out.println("--------------------------------------");
    }
}

// Class For transferring the amount
class Transfer extends deposit {
    Scanner TransSc = new Scanner(System.in);
    ArrayList<String> transList = new ArrayList<>();

    public Transfer() {

    }

    // Method for transferring amount using deposit class object
    public void transfer(deposit obj) {
        System.out.println("Enter amount to transfer to other account : ");
        double tr = TransSc.nextDouble();
        if (tr > obj.getamount()) {

            System.out.println("Insufficient balance");
            transfer(obj);
        } 
        else if(tr<0) 
        {
            System.out.println("Enter Valid amount");
            transfer(obj);
        }
        else {
            obj.amount -= tr;
            System.out.println("Amount transferred Successfully!");
            transList.add("Amount Transferred : " + tr); //Adding transaction to the transaction history
        }
        System.out.println("Current amount: " + obj.amount);
    }
}

public class ATMinterface {
    private static final Scanner scanner = new Scanner(System.in);

    static boolean askCredentials() {
        boolean correct = false;

        // Get user credentials from user
        String id1 = "User1";
        String pin1 = "1234";

        System.out.println("Enter user Id : ");
        String checkid = scanner.nextLine();
        System.out.println("Enter pin : ");
        String checkPin = scanner.nextLine();

        if (checkid.equalsIgnoreCase(id1) && checkPin.equalsIgnoreCase(pin1)) {
            correct = true;
        }
        else{
            System.out.println("Invalid Credentials ,  Try again ");
            if(askCredentials())
            {
                correct = true;
            }
        }

        return correct;
    }

    // Method for console beautification and interaction with user
    static void line() {
        System.out.println("--------------------------------------");
    }

    // Method for showing menu 
    static void showMenu() {
        System.out.println("Following services are provided :\n" +
                "\t1. Transactions History\n" +
                "\t2. Withdraw\n" +
                "\t3. Deposit\n" +
                "\t4. Transfer\n" +
                "\t5. Quit");
    }

    public static void main(String[] args) {

        // Scanner sc = new Scanner(System.in);
        System.out.println("\t\nWelcome to JAVA ATM interface application !");

        // Creating objects of class for accessing methods
        deposit d = new deposit();
        withdraw w = new withdraw();
        Transfer trans = new Transfer();
        transactionHistory t = new transactionHistory(d.deps, w.withs, trans.transList);

        int choice = 0;
        // The main portion of the code handling the credentials from the user and if they are true then working on the methods according to the user's input service
        if (askCredentials() == true) {   
            do {
                showMenu();
                line();
                System.out.print("Enter Service : ");
                choice = scanner.nextInt();
                if (choice == 1) {
                    t.showHistory();
                    line();
                }

                else if (choice == 2) {
                    w.getWithdrawal(d);
                    line();
                }

                else if (choice == 3) {
                    d.getDeposit();
                    line();
                }

                else if (choice == 4) {
                    trans.transfer(d);
                    line();
                }

                else if (choice == 5) {
                    System.out.println("Thank you! Exiting");
                    line();
                    return;
                } else {
                    System.out.println("Invalid choice , please try again");
                    line();
                }

            } while (true);
        }
    }
}
