package chocan.directory;

import chocan.model.Provider;
import chocan.model.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Builds the Provider Directory: every ChocAn service in alphabetical order
 * with its service code and fee. A provider can request it at any time and
 * the system creates a directory file for that provider.
 * Author: Long Huynh
 */
public class ProviderDirectory {
    private final ServiceCatalog catalog;

    public ProviderDirectory(ServiceCatalog catalog) {
        this.catalog = catalog;
    }

    /** Steps 2 and 3 of the use case: get the current services and sort them by name. */
    public List<Service> sortedServices() {
        List<Service> out = new ArrayList<>(catalog.list());
        out.sort(Comparator.comparing(Service::getName, String.CASE_INSENSITIVE_ORDER));
        return out;
    }

    /** Step 4: text of the directory with service name, code and fee for each service. */
    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("CHOCAN PROVIDER DIRECTORY\n");
        sb.append(String.format("%-24s %-8s %8s%n", "Service Name", "Code", "Fee"));
        sb.append("-".repeat(42)).append('\n');
        for (Service s : sortedServices()) {
            sb.append(String.format("%-24s %-8s %8s%n", s.getName(), s.getCode(), "$" + s.getFee().toPlainString()));
        }
        return sb.toString();
    }

    /**
     * Step 5: creates the directory file for the provider, for example
     * reports/ProviderDirectory_200000001.txt. A new request replaces the old file.
     *
     * @return the file that was written
     */
    public Path writeFor(Provider provider, Path folder) throws IOException {
        Files.createDirectories(folder);
        Path file = folder.resolve("ProviderDirectory_" + provider.getNumber() + ".txt");
        String header = "Provider: " + provider.getName() + " (" + provider.getNumber() + ")\n\n";
        Files.writeString(file, header + format(), StandardCharsets.UTF_8);
        return file;
    }
}
