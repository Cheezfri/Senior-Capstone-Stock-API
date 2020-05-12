package edu.albany.icsi418.fa19.teamy.backend.models.asset;

import edu.albany.icsi418.fa19.teamy.backend.models.DBObject;

import javax.persistence.*;

/**
 * Represents the assets such as a stock, bond, or cryptocurrency.
 */
@Entity
public class Asset implements DBObject {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "ticker")
    private String ticker;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    private AssetCategory category;
    @Column(name = "description")
    private String description;

    public Asset() {
    }

    public Asset(String ticker, String name, String category, String description) {
        setTicker(ticker);
        setName(name);
        setCategory(category);
        setDescription(description);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String newTicker) {
        this.ticker = newTicker;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public AssetCategory getCategory() {
        return this.category;
    }

    public void setCategory(String newCat) {
        this.category = AssetCategory.valueOf(newCat);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String newDesc) {
        this.description = newDesc;
    }
}
