package chocan.persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Saves and loads a list of objects as a plain CSV text file so that records
 * survive between program sessions (extra credit: data persistence).
 * Author: Long Huynh
 *
 * Generic on purpose: any team class (Member, ServiceRecord, ...) can be
 * stored by giving a "to row" and a "from row" function.
 *
 * @param <T> type of object stored in the file
 */
public class CsvStore<T> {
    private final Path file;
    private final Function<T, String[]> toRow;
    private final Function<String[], T> fromRow;

    public CsvStore(Path file, Function<T, String[]> toRow, Function<String[], T> fromRow) {
        this.file = file;
        this.toRow = toRow;
        this.fromRow = fromRow;
    }

    /** @return true if the data file exists (a previous session saved it). */
    public boolean exists() {
        return Files.exists(file);
    }

    /** Reads every record. Returns an empty list when the file does not exist. */
    public List<T> load() throws IOException {
        List<T> out = new ArrayList<>();
        if (!exists()) {
            return out;
        }
        for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
            if (line.isBlank()) {
                continue;
            }
            out.add(fromRow.apply(parseLine(line)));
        }
        return out;
    }

    /** Writes every record. Writes a temp file first so a crash never corrupts the old data. */
    public void save(List<T> items) throws IOException {
        Path dir = file.toAbsolutePath().getParent();
        if (dir != null) {
            Files.createDirectories(dir);
        }
        List<String> lines = new ArrayList<>();
        for (T item : items) {
            lines.add(formatLine(toRow.apply(item)));
        }
        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        Files.write(tmp, lines, StandardCharsets.UTF_8);
        Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING);
    }

    static String formatLine(String[] fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            String f = fields[i] == null ? "" : fields[i];
            if (f.contains(",") || f.contains("\"")) {
                sb.append('"').append(f.replace("\"", "\"\"")).append('"');
            } else {
                sb.append(f);
            }
        }
        return sb.toString();
    }

    static String[] parseLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (quoted) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else if (c == '"') {
                    quoted = false;
                } else {
                    cur.append(c);
                }
            } else if (c == '"') {
                quoted = true;
            } else if (c == ',') {
                fields.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        fields.add(cur.toString());
        return fields.toArray(new String[0]);
    }
}
