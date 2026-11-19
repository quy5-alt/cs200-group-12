package chocan.directory;

import chocan.model.Service;
import chocan.persistence.CsvStore;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The list of services ChocAn offers (code, name, fee). It is the data behind
 * the Provider Directory and is saved to disk like every other record.
 * Author: Long Huynh
 */
public class ServiceCatalog {
    private final List<Service> services = new ArrayList<>();
    private final CsvStore<Service> store;

    /**
     * Loads services from the file. If this is the very first run (no file yet),
     * the catalog starts with a few sample services and saves them.
     */
    public ServiceCatalog(Path file) throws IOException {
        store = new CsvStore<>(file,
                s -> new String[]{s.getCode(), s.getName(), s.getFee().toPlainString()},
                r -> new Service(r[0], r[1], new BigDecimal(r[2])));
        if (store.exists()) {
            services.addAll(store.load());
        } else {
            services.add(new Service("598470", "Dietitian Session", new BigDecimal("75.00")));
            services.add(new Service("883948", "Aerobics Class", new BigDecimal("30.50")));
            services.add(new Service("100200", "Counseling", new BigDecimal("120.00")));
            services.add(new Service("312045", "Internist Consultation", new BigDecimal("150.00")));
            services.add(new Service("720114", "Personal Training", new BigDecimal("55.00")));
            store.save(services);
        }
    }

    /** @return a copy of every service, in the order stored. */
    public List<Service> list() {
        return new ArrayList<>(services);
    }

    /** @return the service with this 6-digit code, or null. */
    public Service find(String code) {
        for (Service s : services) {
            if (s.getCode().equals(code)) {
                return s;
            }
        }
        return null;
    }
}
