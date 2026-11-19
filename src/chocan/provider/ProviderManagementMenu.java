package chocan.provider;

import chocan.model.Provider;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Operator menu for the "Manage Provider Records" use case:
 * Add Provider, Update Provider, Delete Provider (plus a list view), then Exit.
 * Author: Long Huynh
 */
public class ProviderManagementMenu {
    private final ProviderRepository repo;
    private final Scanner in;
    private final PrintStream out;

    public ProviderManagementMenu(ProviderRepository repo, Scanner in, PrintStream out) {
        this.repo = repo;
        this.in = in;
        this.out = out;
    }

    /** Shows the menu until the Operator picks Exit, then returns to the caller's menu. */
    public void run() {
        while (true) {
            out.println();
            out.println("=== Manage Provider Records ===");
            out.println("1. Add Provider");
            out.println("2. Update Provider");
            out.println("3. Delete Provider");
            out.println("4. List Providers");
            out.println("0. Exit");
            String choice = ask("Choose an option: ");
            try {
                switch (choice) {
                    case "1": addProvider(); break;
                    case "2": updateProvider(); break;
                    case "3": deleteProvider(); break;
                    case "4": listProviders(); break;
                    case "0": return;
                    default: out.println("Invalid option. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                out.println("Error: " + e.getMessage());
            } catch (IOException e) {
                out.println("Error: could not save the data (" + e.getMessage() + ").");
            }
        }
    }

    private void addProvider() throws IOException {
        out.println("-- Add Provider (the system assigns the 9-digit number) --");
        String name = ask("Name (max 25): ");
        String address = ask("Street address (max 25): ");
        String city = ask("City (max 14): ");
        String state = ask("State (2 letters): ");
        String zip = ask("ZIP (5 digits): ");
        Provider p = repo.add(name, address, city, state, zip);
        out.println("Provider added. Number: " + p.getNumber());
    }

    private void updateProvider() throws IOException {
        String number = ask("Provider number to update: ");
        Provider p = repo.find(number);
        if (p == null) {
            throw new IllegalArgumentException("No provider with number " + number + ".");
        }
        out.println("Current: " + p);
        out.println("Press Enter to keep the current value.");
        String name = askKeep("Name", p.getName());
        String address = askKeep("Street address", p.getAddress());
        String city = askKeep("City", p.getCity());
        String state = askKeep("State", p.getState());
        String zip = askKeep("ZIP", p.getZip());
        repo.update(p.getNumber(), name, address, city, state, zip);
        out.println("Provider updated.");
    }

    private void deleteProvider() throws IOException {
        String number = ask("Provider number to delete: ");
        Provider p = repo.find(number);
        if (p == null) {
            throw new IllegalArgumentException("No provider with number " + number + ".");
        }
        out.println("Provider: " + p);
        String sure = ask("Delete this provider? (y/n): ");
        if (sure.equalsIgnoreCase("y")) {
            repo.delete(p.getNumber());
            out.println("Provider deleted.");
        } else {
            out.println("Delete cancelled.");
        }
    }

    private void listProviders() {
        List<Provider> all = repo.list();
        if (all.isEmpty()) {
            out.println("No providers on file.");
            return;
        }
        out.println("Providers (" + all.size() + "):");
        for (Provider p : all) {
            out.println("  " + p);
        }
    }

    private String ask(String prompt) {
        out.print(prompt);
        out.flush();
        return in.hasNextLine() ? in.nextLine().trim() : "0";
    }

    private String askKeep(String label, String current) {
        String v = ask(label + " [" + current + "]: ");
        return v.isEmpty() ? current : v;
    }
}
