package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The data related to the price of an asset
 */
@ApiModel(description = "The data related to the price of an asset")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-07T21:36:00.664Z[GMT]")
public class AssetPriceDataWithAnalytic   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("assetId")
  private Long assetId = null;

  @JsonProperty("dateTime")
  private OffsetDateTime dateTime = null;

  @JsonProperty("openPrice")
  private Double openPrice = null;

  @JsonProperty("closePrice")
  private Double closePrice = null;

  @JsonProperty("highPrice")
  private Double highPrice = null;

  @JsonProperty("lowPrice")
  private Double lowPrice = null;

  @JsonProperty("adjustedClosePrice")
  private Double adjustedClosePrice = null;

  @JsonProperty("percentageGrowth")
  private Double percentageGrowth = null;

  @JsonProperty("macdIndex")
  private Double macdIndex = null;

  public AssetPriceDataWithAnalytic id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "1", value = "")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AssetPriceDataWithAnalytic assetId(Long assetId) {
    this.assetId = assetId;
    return this;
  }

  /**
   * Get assetId
   * @return assetId
  **/
  @ApiModelProperty(example = "123", value = "")
  
    public Long getAssetId() {
    return assetId;
  }

  public void setAssetId(Long assetId) {
    this.assetId = assetId;
  }

  public AssetPriceDataWithAnalytic dateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  /**
   * Get dateTime
   * @return dateTime
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public AssetPriceDataWithAnalytic openPrice(Double openPrice) {
    this.openPrice = openPrice;
    return this;
  }

  /**
   * Get openPrice
   * @return openPrice
  **/
  @ApiModelProperty(value = "")
  
    public Double getOpenPrice() {
    return openPrice;
  }

  public void setOpenPrice(Double openPrice) {
    this.openPrice = openPrice;
  }

  public AssetPriceDataWithAnalytic closePrice(Double closePrice) {
    this.closePrice = closePrice;
    return this;
  }

  /**
   * Get closePrice
   * @return closePrice
  **/
  @ApiModelProperty(value = "")
  
    public Double getClosePrice() {
    return closePrice;
  }

  public void setClosePrice(Double closePrice) {
    this.closePrice = closePrice;
  }

  public AssetPriceDataWithAnalytic highPrice(Double highPrice) {
    this.highPrice = highPrice;
    return this;
  }

  /**
   * Get highPrice
   * @return highPrice
  **/
  @ApiModelProperty(value = "")
  
    public Double getHighPrice() {
    return highPrice;
  }

  public void setHighPrice(Double highPrice) {
    this.highPrice = highPrice;
  }

  public AssetPriceDataWithAnalytic lowPrice(Double lowPrice) {
    this.lowPrice = lowPrice;
    return this;
  }

  /**
   * Get lowPrice
   * @return lowPrice
  **/
  @ApiModelProperty(value = "")
  
    public Double getLowPrice() {
    return lowPrice;
  }

  public void setLowPrice(Double lowPrice) {
    this.lowPrice = lowPrice;
  }

  public AssetPriceDataWithAnalytic adjustedClosePrice(Double adjustedClosePrice) {
    this.adjustedClosePrice = adjustedClosePrice;
    return this;
  }

  /**
   * Get adjustedClosePrice
   * @return adjustedClosePrice
  **/
  @ApiModelProperty(value = "")
  
    public Double getAdjustedClosePrice() {
    return adjustedClosePrice;
  }

  public void setAdjustedClosePrice(Double adjustedClosePrice) {
    this.adjustedClosePrice = adjustedClosePrice;
  }

  public AssetPriceDataWithAnalytic percentageGrowth(Double percentageGrowth) {
    this.percentageGrowth = percentageGrowth;
    return this;
  }

  /**
   * Get percentageGrowth
   * @return percentageGrowth
  **/
  @ApiModelProperty(value = "")
  
    public Double getPercentageGrowth() {
    return percentageGrowth;
  }

  public void setPercentageGrowth(Double percentageGrowth) {
    this.percentageGrowth = percentageGrowth;
  }

  public AssetPriceDataWithAnalytic macdIndex(Double macdIndex) {
    this.macdIndex = macdIndex;
    return this;
  }

  /**
   * Get macdIndex
   * @return macdIndex
  **/
  @ApiModelProperty(value = "")
  
    public Double getMacdIndex() {
    return macdIndex;
  }

  public void setMacdIndex(Double macdIndex) {
    this.macdIndex = macdIndex;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssetPriceDataWithAnalytic assetPriceDataWithAnalytic = (AssetPriceDataWithAnalytic) o;
    return Objects.equals(this.id, assetPriceDataWithAnalytic.id) &&
        Objects.equals(this.assetId, assetPriceDataWithAnalytic.assetId) &&
        Objects.equals(this.dateTime, assetPriceDataWithAnalytic.dateTime) &&
        Objects.equals(this.openPrice, assetPriceDataWithAnalytic.openPrice) &&
        Objects.equals(this.closePrice, assetPriceDataWithAnalytic.closePrice) &&
        Objects.equals(this.highPrice, assetPriceDataWithAnalytic.highPrice) &&
        Objects.equals(this.lowPrice, assetPriceDataWithAnalytic.lowPrice) &&
        Objects.equals(this.adjustedClosePrice, assetPriceDataWithAnalytic.adjustedClosePrice) &&
        Objects.equals(this.percentageGrowth, assetPriceDataWithAnalytic.percentageGrowth) &&
        Objects.equals(this.macdIndex, assetPriceDataWithAnalytic.macdIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, assetId, dateTime, openPrice, closePrice, highPrice, lowPrice, adjustedClosePrice, percentageGrowth, macdIndex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssetPriceDataWithAnalytic {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    dateTime: ").append(toIndentedString(dateTime)).append("\n");
    sb.append("    openPrice: ").append(toIndentedString(openPrice)).append("\n");
    sb.append("    closePrice: ").append(toIndentedString(closePrice)).append("\n");
    sb.append("    highPrice: ").append(toIndentedString(highPrice)).append("\n");
    sb.append("    lowPrice: ").append(toIndentedString(lowPrice)).append("\n");
    sb.append("    adjustedClosePrice: ").append(toIndentedString(adjustedClosePrice)).append("\n");
    sb.append("    percentageGrowth: ").append(toIndentedString(percentageGrowth)).append("\n");
    sb.append("    macdIndex: ").append(toIndentedString(macdIndex)).append("\n");
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
