/*
 * OperatorUI
 * Purpose: The operator's screen: operator menu and shared input helpers for member/provider records.
 * Author: Lu
 * Use cases: Operator menu (Manage Member Records and Manage Provider Records are filled in by Tasks 2 and 3)
 */
package chocan.boundary;

import chocan.control.ManagementController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Shows the operator menu and reads member and provider data from the keyboard.
 */
public class OperatorUI {

    private static final String OPERATOR_MENU =
            "=== Operator Menu ===\n"
            + "1 Manage Member Records\n"
            + "2 Manage Provider Records\n"
            + "0 Log off";

    private final Scanner in;
    private final ManagementController mc;

    /**
     * Creates the operator screen with the shared Scanner and its controller.
     *
     * @param in the one shared keyboard Scanner
     * @param mc controller that adds, updates and deletes members and providers
     */
    public OperatorUI(Scanner in, ManagementController mc) {
        this.in = in;
        this.mc = mc;
    }

    /** Shows the operator menu repeatedly until the operator logs off. */
    public void run() {
        while (true) {
            displayMenu(OPERATOR_MENU);
            String choice = selectOption("Choose an option: ");
            switch (choice) {
                case "1" -> manageMemberRecords();
                case "2" -> manageProviderRecords();
                case "0" -> {
                    returnToMainMenu();
                    return;
                }
                default -> displayError("Invalid option");
            }
        }
    }

    /** Add, update or delete member records. Stub: Task 2 replaces this body. */
    public void manageMemberRecords() {
        displayError("Not implemented yet");
    }

    /** Add, update or delete provider records. Stub: Task 3 replaces this body. */
    public void manageProviderRecords() {
        displayError("Not implemented yet");
    }

    /**
     * Prints a menu, one option per line.
     *
     * @param options the menu text (options separated by line breaks)
     */
    public void displayMenu(String options) {
        System.out.println(options);
    }

    /**
     * Prints a prompt and reads the user's choice.
     *
     * @param prompt the text shown before reading
     * @return the trimmed line typed by the user ("0" at end of input)
     */
    public String selectOption(String prompt) {
        System.out.print(prompt);
        return readLine();
    }

    /**
     * Reads all fields of a new member.
     *
     * @return map with keys name, street, city, state, zip
     */
    public Map<String, String> enterMemberData() {
        return readRecordFields(false);
    }

    /**
     * Reads all fields of a new provider.
     *
     * @return map with keys name, street, city, state, zip
     */
    public Map<String, String> enterProviderData() {
        return readRecordFields(false);
    }

    /**
     * Asks for a member number.
     *
     * @return the trimmed number typed
     */
    public String enterMemberNumber() {
        return selectOption("Enter member number: ");
    }

    /**
     * Asks for a provider number.
     *
     * @return the trimmed number typed
     */
    public String enterProviderNumber() {
        return selectOption("Enter provider number: ");
    }

    /**
     * Reads changes to an existing record; blank answers keep the current value.
     *
     * @return map containing only the fields the user changed
     */
    public Map<String, String> enterChanges() {
        System.out.println("Press Enter to keep the current value.");
        return readRecordFields(true);
    }

    /**
     * Prints a confirmation message.
     *
     * @param message the text to show
     */
    public void displayConfirmation(String message) {
        System.out.println(message);
    }

    /**
     * Prints an error message.
     *
     * @param message the text to show
     */
    public void displayError(String message) {
        System.out.println(message);
    }

    /** Tells the operator the screen is closing. */
    public void returnToMainMenu() {
        System.out.println("Returning to previous menu.");
    }

    /**
     * Prompts for the five address fields in a fixed order.
     *
     * @param keepBlank true to leave blank answers out of the map (used for updates)
     * @return map with keys name, street, city, state, zip
     */
    private Map<String, String> readRecordFields(boolean keepBlank) {
        String[][] fields = {
            {"name", "Name (max 25): "},
            {"street", "Street address (max 25): "},
            {"city", "City (max 14): "},
            {"state", "State (2 letters): "},
            {"zip", "ZIP code (5 digits): "}
        };
        Map<String, String> data = new LinkedHashMap<>();
        for (String[] field : fields) {
            System.out.print(field[1]);
            String value = in.hasNextLine() ? in.nextLine().trim() : "";
            if (keepBlank && value.isEmpty()) {
                continue;
            }
            data.put(field[0], value);
        }
        return data;
    }

    /** Reads one trimmed line; end of input is treated as "0" so every loop can exit. */
    private String readLine() {
        return in.hasNextLine() ? in.nextLine().trim() : "0";
    }
}
