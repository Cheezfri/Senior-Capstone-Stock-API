package edu.albany.icsi418.fa19.teamy.backend.models.portfolio;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Represents a transaction of an asset, either buying or
 * selling, in a particular portfolio.
 */
@Entity
public class PortfolioTransaction implements DBObject, Comparable<PortfolioTransaction> {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(optional = false)
    private Portfolio portfolio;
    @Enumerated(EnumType.STRING)
    private PortfolioTransactionType type;
    @ManyToOne(optional = false)
    private Asset asset;
    @Column(name = "dateTime")
    private OffsetDateTime dateTime;
    @Column(name = "quantity")
    private double quantity;
    @Column(name = "price")
    private double price;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public PortfolioTransactionType getType() {
        return type;
    }

    public void setType(PortfolioTransactionType type) {
        this.type = type;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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

    public PortfolioTransactionApiModel toApiModel() {
        PortfolioTransactionApiModel result = new PortfolioTransactionApiModel();
        result.setId(this.getId());
        result.setPortfolioId(this.getPortfolio().getId());
        result.setType(this.getType());
        result.setAssetId(this.getAsset().getId());
        result.setDateTime(this.getDateTime());
        result.setQuantity(this.getQuantity());
        result.setPrice(this.getPrice());
        return result;
    }

    public int compareTo(PortfolioTransaction other) {
        return getDateTime().compareTo(other.getDateTime());
    }
}
