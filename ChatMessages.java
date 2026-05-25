package com.mycompany.chatmessages;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ChatMessages {
    public static void main(String[] args) {
        Scanner myMessage = new Scanner(System.in);

        //Registration
        System.out.println(" Registration");
        System.out.print("Enter First Name: ");
        String firstName = myMessage.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = myMessage.nextLine();
        System.out.print("Enter Username: ");
        String username = myMessage.nextLine();
        System.out.print("Enter Password: ");
        String password = myMessage.nextLine();

        Login user = new Login(firstName, lastName, username, password);

        //Login
        System.out.println("Login");
        System.out.print("Enter Username: ");
        String enteredUser = myMessage.nextLine();
        System.out.print("Enter Password: ");
        String enteredPassword = myMessage.nextLine();

        if (!user.loginUser(enteredUser, enteredPassword)) {
            System.out.println("Access denied. Exiting.");
            return; 
        }

        //Chat menu
        MessageCheck handler = new MessageCheck();
        String choice;

        System.out.println("\nWelcome to QuickChat.");
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            choice = myMessage.nextLine();

            switch (choice) {
                case "1":
                    handler.sentMessage(myMessage);
                    break;
                case "2":
                    System.out.println(handler.showMessages());
                    System.out.println("Total messages: " + handler.getTotalMessages());
                    break;
                case "3":
                    System.out.println("Exiting program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

//Login Class
class Login {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Login(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public boolean loginUser(String enteredUser, String enteredPassword) {
        if (enteredUser.equals(userName) && enteredPassword.equals(password)) {
            System.out.println("Welcome " + firstName + " " + lastName + ", it is great to see you again.");
            return true;
        } else {
            System.out.println("Username or password incorrect.");
            return false;
        }
    }
}

//MessageCheck Class
class MessageCheck {
    private final HashMap<String, String> messages = new HashMap<>();
    private final Random number = new Random();
    private int totalMessages = 0;

    public boolean checkMessageID(String id) {
        return id != null && id.length() == 10;
    }

    public String checkRecipientCell(String cell) {
        if (cell.length() == 10 && cell.startsWith("+27")) {
            return cell;
        }
        return "Invalid Cell Number";
    }

    public String createMessageHash(String id, String userMessage) {
        String[] words = userMessage.split(" ");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String firstTwoDigits = id.substring(0, 2);
        return (firstTwoDigits + ":" + totalMessages + ":" + firstWord + lastWord).toUpperCase();
    }

    public void sentMessage(Scanner sc) {
        System.out.println("Enter message with less than 250 characters:");
        String text = sc.nextLine();

        if (text.length() > 250) {
            System.out.println("Message too long!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) sb.append(number.nextInt(10));
        String id = sb.toString();

        if (checkMessageID(id)) {
            String hash = createMessageHash(id, text);
            System.out.println("Hash Generated: " + hash);

            System.out.println("Do you want to (1) Send & Store or (2) Disregard?");
            String action = sc.nextLine();

            if (action.equals("1")) {
                messages.put(id, text);
                totalMessages++;
                System.out.println("Message sent successfully!");
            } else {
                System.out.println("Message disregarded.");
            }
        }
    }

    public String showMessages() {
        if (messages.isEmpty()) return "No messages sent yet.";
        return messages.toString();
    }

    public int getTotalMessages() {
        return totalMessages;
    }
}
      
 
