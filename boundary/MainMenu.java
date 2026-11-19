/*
 * MainMenu
 * Purpose: Top-level menu of the ChocAn system; routes each actor to its role-based menu.
 * Author: Lu
 * Use cases: Operator Login, Manager Login, Manager Reports (Run Weekly Accounting, Member/Provider/Summary Reports, EFT File)
 */
package chocan.boundary;

import chocan.control.ReportController;
import chocan.control.VerificationController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Shows the main menu, logs in operators and managers, and runs the manager menu.
 */
public class MainMenu {

    private static final int MAX_ATTEMPTS = 3;

    /** Strict MM-DD-YYYY: rejects impossible dates such as 13-45-2026 or 02-30-2026. */
    private static final DateTimeFormatter WEEK_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM-dd-uuuu").withResolverStyle(ResolverStyle.STRICT);

    private static final String MAIN_MENU =
            "=== ChocAn Main Menu ===\n"
            + "1 Provider Terminal\n"
            + "2 Operator\n"
            + "3 Manager\n"
            + "4 Acme update (simulation)\n"
            + "0 Exit";

    private static final String MANAGER_MENU =
            "=== Manager Menu ===\n"
            + "1 Run Weekly Accounting\n"
            + "2 Member Reports\n"
            + "3 Provider Reports\n"
            + "4 Summary Report\n"
            + "5 EFT File\n"
            + "0 Log off";

    private final Scanner in;
    private final VerificationController vc;
    private final ProviderTerminalUI pt;
    private final OperatorUI ou;
    private final ReportController rc;
    private final AcmeInterface ai;

    /**
     * Creates the main menu with the shared Scanner, controllers and other screens.
     *
     * @param in the one shared keyboard Scanner
     * @param vc controller that checks login numbers
     * @param pt the provider terminal screen
     * @param ou the operator screen
     * @param rc controller that produces reports (used by the manager menu)
     * @param ai the simulated Acme interface
     */
    public MainMenu(Scanner in, VerificationController vc, ProviderTerminalUI pt,
                    OperatorUI ou, ReportController rc, AcmeInterface ai) {
        this.in = in;
        this.vc = vc;
        this.pt = pt;
        this.ou = ou;
        this.rc = rc;
        this.ai = ai;
    }

    /** Shows the main menu repeatedly until the user chooses 0 Exit. */
    public void run() {
        while (true) {
            display(MAIN_MENU);
            String choice = readChoice();
            switch (choice) {
                case "1" -> pt.turnOnTerminal();
                case "2" -> enterOperatorNumber();
                case "3" -> enterManagerNumber();
                case "4" -> ai.runNightlyUpdateSimulation();
                case "0" -> {
                    display("Goodbye.");
                    return;
                }
                default -> display("Invalid option");
            }
        }
    }

    /** Logs in an operator (up to 3 attempts) and opens the operator menu on success. */
    public void enterOperatorNumber() {
        if (login("Enter operator number: ", vc::verifyOperator, "Invalid operator number")) {
            ou.run();
        }
    }

    /** Logs in a manager (up to 3 attempts) and opens the manager menu on success. */
    public void enterManagerNumber() {
        if (login("Enter manager number: ", vc::verifyManager, "Invalid manager number")) {
            managerMenu();
        }
    }

    // ===== Task 6: manager reports (pasted into chocan/boundary/MainMenu.java) =====

    /** Runs the full weekly accounting procedure, lists the files written and shows the summary. */
    public void requestWeeklyAccounting() {
        if (!chooseWeek()) {
            return;
        }
        List<String> files = rc.runWeeklyAccounting();
        display("Weekly accounting complete");
        for (String path : files) {
            display(path);
        }
        String summaryPath = files.stream()
                .filter(path -> path.contains("Summary_Report"))
                .findFirst()
                .orElse(null);
        if (summaryPath == null) {
            display("Report could not be written: " + rc.getLastError());
        } else {
            display(rc.readReport(summaryPath));
        }
    }

    /** Produces the member reports for a chosen week and shows each one. */
    public void requestMemberReports() {
        if (chooseWeek()) {
            showReportList(rc.generateMemberReports());
        }
    }

    /** Produces the provider reports for a chosen week and shows each one. */
    public void requestProviderReports() {
        if (chooseWeek()) {
            showReportList(rc.generateProviderReports());
        }
    }

    /** Produces the summary report for a chosen week and shows it. */
    public void requestSummaryReport() {
        if (!chooseWeek()) {
            return;
        }
        String path = rc.generateSummaryReport();
        if (path == null) {
            display("Report could not be written: " + rc.getLastError());
            return;
        }
        display("Report generated");
        display(rc.readReport(path));
    }

    /** Produces the EFT data file for a chosen week and shows it. */
    public void requestEFT() {
        if (!chooseWeek()) {
            return;
        }
        String path = rc.generateEFT();
        if (path == null) {
            display("Report could not be written: " + rc.getLastError());
            return;
        }
        display("EFT file ready");
        display(path);
        display(rc.readReport(path));
    }

    /**
     * Asks which week to report on and passes it to the report controller.
     *
     * @return true if a week was set; false if the date was invalid
     */
    private boolean chooseWeek() {
        System.out.print("Report week: press Enter for the current week, "
                + "or type any date in the week (MM-DD-YYYY): ");
        String text = in.hasNextLine() ? in.nextLine().trim() : "";
        if (text.isEmpty()) {
            rc.setReportingWeekContaining(LocalDate.now());
            return true;
        }
        try {
            rc.setReportingWeekContaining(LocalDate.parse(text, WEEK_DATE_FORMAT));
            return true;
        } catch (DateTimeParseException e) {
            display("Invalid date. Use MM-DD-YYYY.");
            return false;
        }
    }

    /** Shows "No services this week", or "Reports generated" followed by each path and its text. */
    private void showReportList(List<String> files) {
        if (files.isEmpty()) {
            display("No services this week");
            return;
        }
        display("Reports generated");
        for (String path : files) {
            display(path);
            display(rc.readReport(path));
        }
    }

    // ===== end of Task 6 =====

    /**
     * Prints one message on its own line.
     *
     * @param message the text to show
     */
    public void display(String message) {
        System.out.println(message);
    }

    /** Manager menu loop; returns when the manager logs off. */
    private void managerMenu() {
        while (true) {
            display(MANAGER_MENU);
            String choice = readChoice();
            switch (choice) {
                case "1" -> requestWeeklyAccounting();
                case "2" -> requestMemberReports();
                case "3" -> requestProviderReports();
                case "4" -> requestSummaryReport();
                case "5" -> requestEFT();
                case "0" -> {
                    display("Manager logged off.");
                    return;
                }
                default -> display("Invalid option");
            }
        }
    }

    /**
     * Shared login loop: up to 3 attempts.
     *
     * @return true if the number was verified
     */
    private boolean login(String prompt, Predicate<String> verifier, String failMessage) {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            System.out.print(prompt);
            String number = readLine();
            if (verifier.test(number)) {
                display("Validated");
                return true;
            }
            display(failMessage);
        }
        display("Too many failed attempts. Returning to main menu.");
        return false;
    }

    private String readChoice() {
        System.out.print("Choose an option: ");
        return readLine();
    }

    /** Reads one trimmed line; end of input is treated as "0" so every loop can exit. */
    private String readLine() {
        return in.hasNextLine() ? in.nextLine().trim() : "0";
    }
}
