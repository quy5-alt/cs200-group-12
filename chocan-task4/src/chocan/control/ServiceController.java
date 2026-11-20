/*
 * Class: ServiceController
 * Purpose: Manages service recording and billing.
 * Author: <teammate name>
 * Use cases: Record Service, Request Provider Directory
 */
package chocan.control;

import chocan.entity.Service;
import chocan.entity.ServiceRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class ServiceController {
    private String providerNo;
    private String memberNo;
    private String serviceCode;
    private LocalDate serviceDate;
    private String comments = "";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM-dd-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    /** Creates a service recording controller. */
    public ServiceController() {
    }

    /** Starts a record with a valid, nonfuture service date. */
    public boolean beginRecord(String providerNo, String memberNo, String date) {
        if (date == null) {
            return false;
        }
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return false;
        }
        if (parsedDate.isAfter(LocalDate.now())) {
            return false;
        }
        this.providerNo = providerNo;
        this.memberNo = memberNo;
        this.serviceDate = parsedDate;
        this.serviceCode = null;
        this.comments = "";
        return true;
    }

    /** Looks up a service and stores its code if valid. */
    public Service lookupService(String code) {
        this.serviceCode = null;
        if (code == null || !code.matches("[0-9]{6}")) {
            return null;
        }
        Service service = Service.findByCode(code);
        if (service != null) {
            this.serviceCode = code;
        }
        return service;
    }

    /** Stores optional comments for the current service. */
    public void setComments(String text) {
        this.comments = (text == null) ? "" : text;
    }

    /** Saves the service record and returns its fee, or -1.0. */
    public double commitRecord() {
        if (providerNo == null || memberNo == null ||
                serviceDate == null || serviceCode == null) {
            return -1.0;
        }
        double fee = Service.getFee(serviceCode);
        if (fee < 0) {
            return -1.0;
        }
        try {
            ServiceRecord.create(LocalDateTime.now(), serviceDate,
                    providerNo, memberNo, serviceCode, comments);
        } catch (IllegalStateException e) {
            return -1.0;
        }
        providerNo = null;
        memberNo = null;
        serviceDate = null;
        serviceCode = null;
        comments = "";
        return fee;
    }

    /** Directory generation stub, implemented by Task 7. */
    public String requestDirectory() {
        return null;
    }

    /** Directory content stub, implemented by Task 7. */
    public String getDirectoryText() {
        return "";
    }
}
