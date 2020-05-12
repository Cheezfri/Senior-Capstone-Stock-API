package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.Asset;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetPriceDataWithAnalytic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The aggregatte values of assets in a portfolio over a time period
 */
@ApiModel(description = "The aggregatte values of assets in a portfolio over a time period")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-07T21:36:00.664Z[GMT]")
public class AssetAggregateData   {
  @JsonProperty("portfolioId")
  private Long portfolioId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("startDate")
  private OffsetDateTime startDate = null;

  @JsonProperty("endDate")
  private OffsetDateTime endDate = null;

  @JsonProperty("assetsInPortfolio")
  @Valid
  private List<Asset> assetsInPortfolio = null;

  @JsonProperty("assetValueHashMap")
  @Valid
  private Map<String, List<AssetPriceDataWithAnalytic>> assetValueHashMap = null;

  public AssetAggregateData portfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
    return this;
  }

  /**
   * Unique Database Primary Key
   * @return portfolioId
  **/
  @ApiModelProperty(example = "1", value = "Unique Database Primary Key")
  
    public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public AssetAggregateData name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "Portfolio A", value = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AssetAggregateData startDate(OffsetDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
  **/
  @ApiModelProperty(example = "1996-12-19T16:39:57-08:00", value = "")
  
    @Valid
    public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(OffsetDateTime startDate) {
    this.startDate = startDate;
  }

  public AssetAggregateData endDate(OffsetDateTime endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
  **/
  @ApiModelProperty(example = "1996-12-19T16:39:57-08:00", value = "")
  
    @Valid
    public OffsetDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(OffsetDateTime endDate) {
    this.endDate = endDate;
  }

  public AssetAggregateData assetsInPortfolio(List<Asset> assetsInPortfolio) {
    this.assetsInPortfolio = assetsInPortfolio;
    return this;
  }

  public AssetAggregateData addAssetsInPortfolioItem(Asset assetsInPortfolioItem) {
    if (this.assetsInPortfolio == null) {
      this.assetsInPortfolio = new ArrayList<Asset>();
    }
    this.assetsInPortfolio.add(assetsInPortfolioItem);
    return this;
  }

  /**
   * The list of assets for which transactions happened in a potfolio
   * @return assetsInPortfolio
  **/
  @ApiModelProperty(value = "The list of assets for which transactions happened in a potfolio")
      @Valid
    public List<Asset> getAssetsInPortfolio() {
    return assetsInPortfolio;
  }

  public void setAssetsInPortfolio(List<Asset> assetsInPortfolio) {
    this.assetsInPortfolio = assetsInPortfolio;
  }

  public AssetAggregateData assetValueHashMap(Map<String, List<AssetPriceDataWithAnalytic>> assetValueHashMap) {
    this.assetValueHashMap = assetValueHashMap;
    return this;
  }

  public AssetAggregateData putAssetValueHashMapItem(String key, List<AssetPriceDataWithAnalytic> assetValueHashMapItem) {
    if (this.assetValueHashMap == null) {
      this.assetValueHashMap = new HashMap<String, List<AssetPriceDataWithAnalytic>>();
    }
    this.assetValueHashMap.put(key, assetValueHashMapItem);
    return this;
  }

  /**
   * The hashmap with key as assetId and values as an array of pricedata of asset over time
   * @return assetValueHashMap
  **/
  @ApiModelProperty(value = "The hashmap with key as assetId and values as an array of pricedata of asset over time")
      @Valid
    public Map<String, List<AssetPriceDataWithAnalytic>> getAssetValueHashMap() {
    return assetValueHashMap;
  }

  public void setAssetValueHashMap(Map<String, List<AssetPriceDataWithAnalytic>> assetValueHashMap) {
    this.assetValueHashMap = assetValueHashMap;
  }


  @Override
  public boolean equals(Object o) {
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
        Objects.equals(this.assetsInPortfolio, assetAggregateData.assetsInPortfolio) &&
        Objects.equals(this.assetValueHashMap, assetAggregateData.assetValueHashMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portfolioId, name, startDate, endDate, assetsInPortfolio, assetValueHashMap);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssetAggregateData {\n");
    
    sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    assetsInPortfolio: ").append(toIndentedString(assetsInPortfolio)).append("\n");
    sb.append("    assetValueHashMap: ").append(toIndentedString(assetValueHashMap)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
