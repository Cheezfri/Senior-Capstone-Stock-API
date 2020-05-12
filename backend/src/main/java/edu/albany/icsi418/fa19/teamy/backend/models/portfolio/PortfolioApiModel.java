package edu.albany.icsi418.fa19.teamy.backend.models.portfolio;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;

/**
 * Represents an asset portfolio. A given portfolio is
 * registered to a user and can have 1 to many
 * transactions of assets.
 */
public class PortfolioApiModel {

    private long id;
    private String name;
    private boolean deleted;
    private int serialNumber;
    private PortfolioType type;
    private long ownerUserId;

    public PortfolioApiModel() {
    }

    public long getId() {
        return id;
    }

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

    public long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Portfolio Name: ");
        result.append(this.getName() + "\n");
        result.append("Owner Id: " + this.getOwnerUserId());
        return result.toString();
    }
}

