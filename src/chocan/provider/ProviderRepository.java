package chocan.provider;

import chocan.model.Provider;
import chocan.persistence.CsvStore;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Holds every provider record and enforces the ChocAn field rules.
 * Every change is written to disk right away, so records are still there
 * after the program is closed and restarted (persistence extra credit).
 * Author: Long Huynh
 */
public class ProviderRepository {
    public static final int MAX_NAME = 25;
    public static final int MAX_ADDRESS = 25;
    public static final int MAX_CITY = 14;
    private static final long FIRST_NUMBER = 200000001L;

    // keys are 9-digit strings, so TreeMap order is numeric order
    private final TreeMap<String, Provider> providers = new TreeMap<>();
    private final CsvStore<Provider> store;

    /** @param file CSV file used to keep providers between sessions */
    public ProviderRepository(Path file) throws IOException {
        this.store = new CsvStore<>(file,
                p -> new String[]{p.getNumber(), p.getName(), p.getAddress(), p.getCity(), p.getState(), p.getZip()},
                r -> new Provider(r[0], r[1], r[2], r[3], r[4], r[5]));
        for (Provider p : store.load()) {
            providers.put(p.getNumber(), p);
        }
    }

    /** Adds a provider with the next free 9-digit number and returns it. */
    public Provider add(String name, String address, String city, String state, String zip) throws IOException {
        validate(name, address, city, state, zip);
        Provider p = new Provider(nextNumber(), name.trim(), address.trim(), city.trim(),
                state.trim().toUpperCase(), zip.trim());
        providers.put(p.getNumber(), p);
        save();
        return p;
    }

    /** Replaces the fields of an existing provider. The number cannot change. */
    public Provider update(String number, String name, String address, String city, String state, String zip)
            throws IOException {
        Provider p = require(number);
        validate(name, address, city, state, zip);
        p.setName(name.trim());
        p.setAddress(address.trim());
        p.setCity(city.trim());
        p.setState(state.trim().toUpperCase());
        p.setZip(zip.trim());
        save();
        return p;
    }

    /** Removes a provider and returns the removed record. */
    public Provider delete(String number) throws IOException {
        Provider p = require(number);
        providers.remove(p.getNumber());
        save();
        return p;
    }

    /** @return the provider with this number, or null if there is none. */
    public Provider find(String number) {
        return number == null ? null : providers.get(number.trim());
    }

    /** @return all providers sorted by name (a copy, safe to modify). */
    public List<Provider> list() {
        List<Provider> out = new ArrayList<>(providers.values());
        out.sort(Comparator.comparing(Provider::getName, String.CASE_INSENSITIVE_ORDER));
        return out;
    }

    public int size() {
        return providers.size();
    }

    private Provider require(String number) {
        Provider p = find(number);
        if (p == null) {
            throw new IllegalArgumentException("No provider with number " + number + ".");
        }
        return p;
    }

    private String nextNumber() {
        long next = FIRST_NUMBER;
        if (!providers.isEmpty()) {
            next = Math.max(next, Long.parseLong(providers.lastKey()) + 1);
        }
        return String.valueOf(next);
    }

    private void save() throws IOException {
        store.save(new ArrayList<>(providers.values()));
    }

    static void validate(String name, String address, String city, String state, String zip) {
        checkText("Name", name, MAX_NAME);
        checkText("Street address", address, MAX_ADDRESS);
        checkText("City", city, MAX_CITY);
        if (state == null || !state.trim().matches("[A-Za-z]{2}")) {
            throw new IllegalArgumentException("State must be 2 letters.");
        }
        if (zip == null || !zip.trim().matches("\\d{5}")) {
            throw new IllegalArgumentException("ZIP code must be 5 digits.");
        }
    }

    private static void checkText(String label, String value, int max) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " cannot be empty.");
        }
        if (value.trim().length() > max) {
            throw new IllegalArgumentException(label + " can be at most " + max + " characters.");
        }
    }
}
