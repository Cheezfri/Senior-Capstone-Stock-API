package edu.albany.icsi418.fa19.teamy.backend.models.user;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents the access level of a user, either user,
 * admin or support.
 */
@Entity
public class AccessLevel implements DBObject {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "role")
    private AccessRole role;

    public AccessLevel() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getRole() {
        return this.role.name();
    }

    public void setRole(AccessRole newRole) {
        this.role = newRole;
    }

}
