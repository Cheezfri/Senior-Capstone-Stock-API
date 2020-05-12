package edu.albany.icsi418.fa19.teamy.backend.models.portfolio;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;

import java.time.OffsetDateTime;

/**
 * Represents a transaction of an asset, either buying or
 * selling, in a particular portfolio.
 */
public class PortfolioTransactionApiModel {

    private long id;
    private long portfolioId;
    private PortfolioTransactionType type;
    private long assetId;
    private OffsetDateTime dateTime;
    private double quantity;
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public PortfolioTransactionType getType() {
        return type;
    }

    public void setType(PortfolioTransactionType type) {
        this.type = type;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
