/*
 * ProviderTerminalUI
 * Purpose: The provider's terminal screen: provider login and the provider menu.
 * Author: Lu
 * Use cases: Provider Login (Validate Member, Record Service and Request Provider Directory are filled in by Tasks 4 and 7)
 */
package chocan.boundary;

import chocan.control.ServiceController;
import chocan.control.ValidationController;
import chocan.control.VerificationController;

import java.util.Scanner;

/**
 * Simulates the provider terminal: turns on, logs in a provider and shows the provider menu.
 */
public class ProviderTerminalUI {

    private static final int MAX_ATTEMPTS = 3;

    private static final String PROVIDER_MENU =
            "=== Provider Menu ===\n"
            + "1 Validate Member\n"
            + "2 Record Service\n"
            + "3 Request Provider Directory\n"
            + "0 Log off";

    private final Scanner in;
    private final VerificationController vc;
    private final ValidationController val;
    private final ServiceController sc;
    private String providerNumber;

    /**
     * Creates the provider terminal with the shared Scanner and its controllers.
     *
     * @param in  the one shared keyboard Scanner
     * @param vc  controller that checks the provider number
     * @param val controller that validates member numbers
     * @param sc  controller that records services and builds the directory
     */
    public ProviderTerminalUI(Scanner in, VerificationController vc,
                              ValidationController val, ServiceController sc) {
        this.in = in;
        this.vc = vc;
        this.val = val;
        this.sc = sc;
    }

    /** Turns the terminal on and asks the provider to log in. */
    public void turnOnTerminal() {
        display("ChocAn Provider Terminal - ON");
        enterProviderNumber();
    }

    /** Logs in a provider (up to 3 attempts) and runs the provider menu on success. */
    public void enterProviderNumber() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            System.out.print("Enter provider number: ");
            String number = readLine();
            if (vc.verifyProvider(number)) {
                providerNumber = number;
                display("Validated");
                providerMenu();
                return;
            }
            display("Invalid number");
        }
        display("Too many failed attempts. Returning to main menu.");
    }

    /** Asks for a member number and shows the validation result. Stub: Task 4 replaces this body. */
    public void validateMember() {
        display("Not implemented yet");
    }

    /** Records one service for the logged-in provider. Stub: Task 4 replaces this body. */
    public void recordService() {
        display("Not implemented yet");
    }

    /** Asks for the date of service. Stub: Task 4 replaces this body. */
    public void enterServiceDate() {
        display("Not implemented yet");
    }

    /** Asks for the service code. Stub: Task 4 replaces this body. */
    public void enterServiceCode() {
        display("Not implemented yet");
    }

    /** Asks for optional comments. Stub: Task 4 replaces this body. */
    public void enterComments() {
        display("Not implemented yet");
    }

    /** Requests the provider directory. Stub: Task 7 replaces this body. */
    public void requestDirectory() {
        display("Not implemented yet");
    }

    /**
     * Prints one message on its own line.
     *
     * @param message the text to show
     */
    public void display(String message) {
        System.out.println(message);
    }

    /**
     * Prints an error message exactly as given.
     *
     * @param message the error text
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /** Asks the provider to re-enter the service code. Stub: Task 4 replaces this body. */
    public void promptReenterCode() {
        display("Not implemented yet");
    }

    /** Provider menu loop; returns when the provider logs off. */
    private void providerMenu() {
        while (true) {
            display(PROVIDER_MENU);
            System.out.print("Choose an option: ");
            String choice = readLine();
            switch (choice) {
                case "1" -> validateMember();
                case "2" -> recordService();
                case "3" -> requestDirectory();
                case "0" -> {
                    display("Provider logged off.");
                    providerNumber = null;
                    return;
                }
                default -> display("Invalid option");
            }
        }
    }

    /** Reads one trimmed line; end of input is treated as "0" so every loop can exit. */
    private String readLine() {
        return in.hasNextLine() ? in.nextLine().trim() : "0";
    }
}
