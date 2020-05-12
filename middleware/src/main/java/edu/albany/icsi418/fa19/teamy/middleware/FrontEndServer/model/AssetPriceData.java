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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class AssetPriceData   {
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

  public AssetPriceData id(Long id) {
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

  public AssetPriceData assetId(Long assetId) {
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

  public AssetPriceData dateTime(OffsetDateTime dateTime) {
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

  public AssetPriceData openPrice(Double openPrice) {
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

  public AssetPriceData closePrice(Double closePrice) {
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

  public AssetPriceData highPrice(Double highPrice) {
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

  public AssetPriceData lowPrice(Double lowPrice) {
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

  public AssetPriceData adjustedClosePrice(Double adjustedClosePrice) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssetPriceData assetPriceData = (AssetPriceData) o;
    return Objects.equals(this.id, assetPriceData.id) &&
        Objects.equals(this.assetId, assetPriceData.assetId) &&
        Objects.equals(this.dateTime, assetPriceData.dateTime) &&
        Objects.equals(this.openPrice, assetPriceData.openPrice) &&
        Objects.equals(this.closePrice, assetPriceData.closePrice) &&
        Objects.equals(this.highPrice, assetPriceData.highPrice) &&
        Objects.equals(this.lowPrice, assetPriceData.lowPrice) &&
        Objects.equals(this.adjustedClosePrice, assetPriceData.adjustedClosePrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, assetId, dateTime, openPrice, closePrice, highPrice, lowPrice, adjustedClosePrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssetPriceData {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    dateTime: ").append(toIndentedString(dateTime)).append("\n");
    sb.append("    openPrice: ").append(toIndentedString(openPrice)).append("\n");
    sb.append("    closePrice: ").append(toIndentedString(closePrice)).append("\n");
    sb.append("    highPrice: ").append(toIndentedString(highPrice)).append("\n");
    sb.append("    lowPrice: ").append(toIndentedString(lowPrice)).append("\n");
    sb.append("    adjustedClosePrice: ").append(toIndentedString(adjustedClosePrice)).append("\n");
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
