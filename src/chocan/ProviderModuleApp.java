package chocan;

import chocan.directory.ProviderDirectory;
import chocan.directory.ServiceCatalog;
import chocan.model.Provider;
import chocan.provider.ProviderManagementMenu;
import chocan.provider.ProviderRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Stand-alone launcher for provider management, the provider directory and
 * data persistence. Records are loaded at start-up and saved after every change.
 *
 *   java -cp out chocan.ProviderModuleApp [dataFolder]
 *
 * When the team's main menu is ready, call ProviderManagementMenu.run() from the
 * Operator menu and ProviderDirectory.writeFor(...) from the Provider menu.
 * Author: Long Huynh
 */
public class ProviderModuleApp {
    public static void main(String[] args) throws IOException {
        Path dataDir = Paths.get(args.length > 0 ? args[0] : "data");
        ProviderRepository providers = new ProviderRepository(dataDir.resolve("providers.csv"));
        ServiceCatalog catalog = new ServiceCatalog(dataDir.resolve("services.csv"));
        ProviderDirectory directory = new ProviderDirectory(catalog);
        Scanner in = new Scanner(System.in);

        System.out.println("ChocAn Provider Module (" + providers.size() + " providers loaded from " + dataDir + ")");
        while (true) {
            System.out.println();
            System.out.println("=== Main Menu ===");
            System.out.println("1. Operator: Manage Provider Records");
            System.out.println("2. Provider: Request Provider Directory");
            System.out.println("0. Exit (all data is already saved)");
            System.out.print("Choose an option: ");
            String choice = in.hasNextLine() ? in.nextLine().trim() : "0";
            if (choice.equals("1")) {
                new ProviderManagementMenu(providers, in, System.out).run();
            } else if (choice.equals("2")) {
                requestDirectory(providers, directory, in);
            } else if (choice.equals("0")) {
                System.out.println("Goodbye.");
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void requestDirectory(ProviderRepository providers, ProviderDirectory directory, Scanner in)
            throws IOException {
        System.out.print("Enter your 9-digit provider number: ");
        String number = in.hasNextLine() ? in.nextLine().trim() : "";
        Provider p = providers.find(number);
        if (p == null) {
            System.out.println("Invalid provider number. Directory not created.");
            return;
        }
        System.out.println();
        System.out.print(directory.format());
        Path file = directory.writeFor(p, Paths.get("reports"));
        System.out.println();
        System.out.println("Directory file created: " + file);
    }
}
