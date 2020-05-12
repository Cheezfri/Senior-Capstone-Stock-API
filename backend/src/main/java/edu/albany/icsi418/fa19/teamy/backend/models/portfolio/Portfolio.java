package edu.albany.icsi418.fa19.teamy.backend.models.portfolio;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;
import edu.albany.icsi418.fa19.teamy.backend.models.user.User;

import javax.persistence.*;

/**
 * Represents an asset portfolio. A given portfolio is
 * registered to a user and can have 1 to many
 * transactions of assets.
 */
@Entity
public class Portfolio implements DBObject {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "serialNumber")
    private int serialNumber;
    @Column(name = "type")
    private PortfolioType type;
    @ManyToOne(optional = false)
    private User owner;

    public Portfolio() {
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public PortfolioType getType() {
        return type;
    }

    public void setType(PortfolioType type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PortfolioApiModel toApiModel() {

        PortfolioApiModel result = new PortfolioApiModel();
        result.setId(this.getId());
        result.setName(this.getName());
        result.setDeleted(this.isDeleted());
        result.setSerialNumber(this.getSerialNumber());
        result.setType(this.getType());
        result.setOwnerUserId(this.getOwner().getId());
        return result;

    }

}
