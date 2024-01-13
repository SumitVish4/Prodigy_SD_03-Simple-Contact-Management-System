
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPhone Number: " + phoneNumber + "\nEmail Address: " + emailAddress + "\n";
    }
}

class ContactManager {
    private ArrayList<Contact> contacts;
    private String fileName;

    public ContactManager(String fileName) {
        this.contacts = new ArrayList<>();
        this.fileName = fileName;
        loadContacts();
    }

    private void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            contacts = (ArrayList<Contact>) ois.readObject();
            System.out.println("Contacts loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting with an empty contact list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(contacts);
            System.out.println("Contacts saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        System.out.println("Contact added successfully!\n");
        saveContacts();
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.\n");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void editContact(int index, Contact newContact) {
        if (index >= 0 && index < contacts.size()) {
            contacts.set(index, newContact);
            System.out.println("Contact updated successfully!\n");
            saveContacts();
        } else {
            System.out.println("Invalid index. Contact not found.\n");
        }
    }

    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("Contact deleted successfully!\n");
            saveContacts();
        } else {
            System.out.println("Invalid index. Contact not found.\n");
        }
    }
}

public class ContactManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Specify the file name for persistent storage
        String fileName = "contacts.ser";
        ContactManager contactManager = new ContactManager(fileName);

        while (true) {
            System.out.println("1. Add a new contact");
            System.out.println("2. View contacts");
            System.out.println("3. Edit a contact");
            System.out.println("4. Delete a contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String emailAddress = scanner.nextLine();

                    Contact newContact = new Contact(name, phoneNumber, emailAddress);
                    contactManager.addContact(newContact);
                    break;

                case 2:
                    contactManager.viewContacts();
                    break;

                case 3:
                    System.out.print("Enter the index of the contact to edit: ");
                    int editIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmailAddress = scanner.nextLine();

                    Contact editedContact = new Contact(newName, newPhoneNumber, newEmailAddress);
                    contactManager.editContact(editIndex, editedContact);
                    break;

                case 4:
                    System.out.print("Enter the index of the contact to delete: ");
                    int deleteIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    contactManager.deleteContact(deleteIndex);
                    break;

                case 5:
                    System.out.println("Exiting the Contact Management System. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.\n");
            }
        }
    }
}

