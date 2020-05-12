package edu.albany.icsi418.fa19.teamy.backend.models.analytics;

import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceDataApiModel;
import org.threeten.bp.OffsetDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The aggregatte values of assets in a portfolio over a time period
 */
public class AssetAggregateData {

    private Long portfolioId;
    private String name;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Map<String, List<AssetPriceDataApiModel>> assetValueHashMap;
    private List<Asset> assetsInPortfolio;

    public List<Asset> getAssetsInPortfolio() {
        return assetsInPortfolio;
    }

    public void setAssetsInPortfolio(List<Asset> assetsInPortfolio) {
        this.assetsInPortfolio = assetsInPortfolio;
    }
    
    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public AssetAggregateData putAssetValueHashMapItem(String key, List<AssetPriceDataApiModel> assetValueHashMapItem) {
        if (this.assetValueHashMap == null) {
            this.assetValueHashMap = new HashMap<String, List<AssetPriceDataApiModel>>();
        }
        this.assetValueHashMap.put(key, assetValueHashMapItem);
        return this;
    }

    public Map<String, List<AssetPriceDataApiModel>> getAssetValueHashMap() {
        return assetValueHashMap;
    }

    public void setAssetValueHashMap(Map<String, List<AssetPriceDataApiModel>> assetValueHashMap) {
        this.assetValueHashMap = assetValueHashMap;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssetAggregateData assetAggregateData = (AssetAggregateData) o;
        return Objects.equals(this.portfolioId, assetAggregateData.portfolioId) &&
                Objects.equals(this.name, assetAggregateData.name) &&
                Objects.equals(this.startDate, assetAggregateData.startDate) &&
                Objects.equals(this.endDate, assetAggregateData.endDate) &&
                Objects.equals(this.assetValueHashMap, assetAggregateData.assetValueHashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, name, startDate, endDate, assetValueHashMap);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AssetAggregateData {\n");

        sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
        sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
        sb.append("    assetValueHashMap: ").append(toIndentedString(assetValueHashMap)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}