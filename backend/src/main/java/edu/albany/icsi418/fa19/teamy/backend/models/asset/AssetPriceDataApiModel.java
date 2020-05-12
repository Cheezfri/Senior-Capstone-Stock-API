package edu.albany.icsi418.fa19.teamy.backend.models.asset;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;

import java.time.OffsetDateTime;

/**
 * Represents an opening price, closing price,
 * high and low price for an asset at a given date.
 */
public class AssetPriceDataApiModel {

    private long id;
    private long assetId;
    private OffsetDateTime dateTime;
    private double openPrice;
    private double closePrice;
    private double highPrice;
    private double lowPrice;
    private double adjustedClosePrice;

    public AssetPriceDataApiModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
