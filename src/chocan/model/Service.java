package chocan.model;

import java.math.BigDecimal;

/**
 * An entry in the Provider Directory (service code, name, fee).
 * Author: Long Huynh
 */
public class Service {
    private final String code;     // 6 digits
    private final String name;     // max 20 chars
    private final BigDecimal fee;  // up to $999.99

    public Service(String code, String name, BigDecimal fee) {
        this.code = code;
        this.name = name;
        this.fee = fee;
    }

    public String getCode()    { return code; }
    public String getName()    { return name; }
    public BigDecimal getFee() { return fee; }
}
