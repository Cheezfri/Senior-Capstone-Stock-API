package edu.albany.icsi418.fa19.teamy.backend.models.audit;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;

/**
 * Represents a login attempt for auditing purposes.
 * Logs email, ip address, date and time, and whether
 * attempt was successful.
 */
@Entity
public class LoginRecord implements DBObject {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "email")
    private String email;
    @Column(name = "success")
    private boolean success;
    @Column(name = "ipAddress")
    private String ipAddress;
    @Column(name = "date")
    private OffsetDateTime date;

    public LoginRecord() {
    }

    public LoginRecord(String email, boolean succeed, String ipAddress) {
        this.email = email;
        this.success = succeed;
        this.ipAddress = ipAddress;
        this.date = OffsetDateTime.now();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }
}
