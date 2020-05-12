package edu.albany.icsi418.fa19.teamy.backend.models.user;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.Portfolio;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Represents details of the users.
 */
@Entity
@Table(name="appUser")
public class User implements DBObject {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "email")
    private String email;
    @Column(name = "pwhash")
    private String passwordHash;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @ManyToOne(optional = false)
    private AccessLevel accessLevel;
    @Column(name = "locked")
    private boolean locked;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "salt")
    private String salt;
    @Column(name = "localCurrency")
    private String localCurrency;

    public String getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    public User() {
    }

    public User(String email, String passwordHash, String firstName, String lastName, AccessLevel accessLevel, boolean locked) {
        setEmail(email);
        setPasswordHash(passwordHash);
        setFirstName(firstName);
        setLastName(lastName);
        setAccessLevel(accessLevel);
        lock(locked);
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
        return this.email;
    }
    public String getPasswordHash() {
        return this.passwordHash;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }
    public boolean isLocked() {
        return this.locked;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    public void setPasswordHash(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
    }
    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }
    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }
    public void setAccessLevel(AccessLevel newAccessLevel) {
        this.accessLevel = newAccessLevel;
    }
    public void lock(boolean lock) {
        this.locked = lock;
    }

}
