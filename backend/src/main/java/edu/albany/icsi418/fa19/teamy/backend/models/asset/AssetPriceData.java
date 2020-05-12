package edu.albany.icsi418.fa19.teamy.backend.models.asset;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;

import javax.persistence.*;
import java.time.OffsetDateTime;

/**
 * Represents an opening price, closing price,
 * high and low price for an asset at a given date.
 */
@Entity
public class AssetPriceData implements DBObject {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(optional = false)
    private Asset asset;
    @Column(name = "dateTime")
    private OffsetDateTime dateTime;
    @Column(name = "openPrice")
    private double openPrice;
    @Column(name = "closePrice")
    private double closePrice;
    @Column(name = "highPrice")
    private double highPrice;
    @Column(name = "lowPrice")
    private double lowPrice;
    @Column(name = "adjustedClosePrice")
    private double adjustedClosePrice;

    public AssetPriceData() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getAdjustedClosePrice() {
        return adjustedClosePrice;
    }

    public void setAdjustedClosePrice(double adjustedClosePrice) {
        this.adjustedClosePrice = adjustedClosePrice;
    }

    public AssetPriceDataApiModel toApiModel() {

        AssetPriceDataApiModel result = new AssetPriceDataApiModel();
        result.setId(this.getId());
        result.setAssetId(this.getAsset().getId());
        result.setDateTime(this.getDateTime());
        result.setOpenPrice(this.getOpenPrice());
        result.setClosePrice(this.getClosePrice());
        result.setHighPrice(this.getHighPrice());
        result.setLowPrice(this.getLowPrice());
        result.setAdjustedClosePrice(this.getAdjustedClosePrice());
        return result;

    }
    
}
