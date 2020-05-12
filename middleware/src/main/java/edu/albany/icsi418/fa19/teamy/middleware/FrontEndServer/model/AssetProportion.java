package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Name of asset and its proportion in a portfolio
 */
@ApiModel(description = "Name of asset and its proportion in a portfolio")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class AssetProportion   {
  @JsonProperty("assetName")
  private String assetName = null;

  @JsonProperty("assetQuantityProportion")
  private Double assetQuantityProportion = null;

  @JsonProperty("assetNetPriceProportion")
  private Double assetNetPriceProportion = null;

  @JsonProperty("assetQuantity")
  private Double assetQuantity = null;

  @JsonProperty("assetNetPrice")
  private Double assetNetPrice = null;

  @JsonProperty("assetType")
  private String assetType = null;

  @JsonProperty("assetTicker")
  private String assetTicker = null;

  public AssetProportion assetName(String assetName) {
    this.assetName = assetName;
    return this;
  }

  /**
   * Get assetName
   * @return assetName
  **/
  @ApiModelProperty(example = "apple", value = "")
  
    public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public AssetProportion assetQuantityProportion(Double assetQuantityProportion) {
    this.assetQuantityProportion = assetQuantityProportion;
    return this;
  }

  /**
   * Get assetQuantityProportion
   * @return assetQuantityProportion
  **/
  @ApiModelProperty(example = "3.5", value = "")
  
    public Double getAssetQuantityProportion() {
    return assetQuantityProportion;
  }

  public void setAssetQuantityProportion(Double assetQuantityProportion) {
    this.assetQuantityProportion = assetQuantityProportion;
  }

  public AssetProportion assetNetPriceProportion(Double assetNetPriceProportion) {
    this.assetNetPriceProportion = assetNetPriceProportion;
    return this;
  }

  /**
   * Get assetNetPriceProportion
   * @return assetNetPriceProportion
  **/
  @ApiModelProperty(example = "453", value = "")
  
    public Double getAssetNetPriceProportion() {
    return assetNetPriceProportion;
  }

  public void setAssetNetPriceProportion(Double assetNetPriceProportion) {
    this.assetNetPriceProportion = assetNetPriceProportion;
  }

  public AssetProportion assetQuantity(Double assetQuantity) {
    this.assetQuantity = assetQuantity;
    return this;
  }

  /**
   * Get assetQuantity
   * @return assetQuantity
  **/
  @ApiModelProperty(example = "3.5", value = "")
  
    public Double getAssetQuantity() {
    return assetQuantity;
  }

  public void setAssetQuantity(Double assetQuantity) {
    this.assetQuantity = assetQuantity;
  }

  public AssetProportion assetNetPrice(Double assetNetPrice) {
    this.assetNetPrice = assetNetPrice;
    return this;
  }

  /**
   * Get assetNetPrice
   * @return assetNetPrice
  **/
  @ApiModelProperty(example = "453", value = "")
  
    public Double getAssetNetPrice() {
    return assetNetPrice;
  }

  public void setAssetNetPrice(Double assetNetPrice) {
    this.assetNetPrice = assetNetPrice;
  }

  public AssetProportion assetType(String assetType) {
    this.assetType = assetType;
    return this;
  }

  /**
   * Get assetType
   * @return assetType
  **/
  @ApiModelProperty(example = "STOCK", value = "")
  
    public String getAssetType() {
    return assetType;
  }

  public void setAssetType(String assetType) {
    this.assetType = assetType;
  }

  public AssetProportion assetTicker(String assetTicker) {
    this.assetTicker = assetTicker;
    return this;
  }

  /**
   * Get assetTicker
   * @return assetTicker
  **/
  @ApiModelProperty(example = "aapl", value = "")
  
    public String getAssetTicker() {
    return assetTicker;
  }

  public void setAssetTicker(String assetTicker) {
    this.assetTicker = assetTicker;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssetProportion assetProportion = (AssetProportion) o;
    return Objects.equals(this.assetName, assetProportion.assetName) &&
        Objects.equals(this.assetQuantityProportion, assetProportion.assetQuantityProportion) &&
        Objects.equals(this.assetNetPriceProportion, assetProportion.assetNetPriceProportion) &&
        Objects.equals(this.assetQuantity, assetProportion.assetQuantity) &&
        Objects.equals(this.assetNetPrice, assetProportion.assetNetPrice) &&
        Objects.equals(this.assetType, assetProportion.assetType) &&
        Objects.equals(this.assetTicker, assetProportion.assetTicker);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assetName, assetQuantityProportion, assetNetPriceProportion, assetQuantity, assetNetPrice, assetType, assetTicker);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssetProportion {\n");
    
    sb.append("    assetName: ").append(toIndentedString(assetName)).append("\n");
    sb.append("    assetQuantityProportion: ").append(toIndentedString(assetQuantityProportion)).append("\n");
    sb.append("    assetNetPriceProportion: ").append(toIndentedString(assetNetPriceProportion)).append("\n");
    sb.append("    assetQuantity: ").append(toIndentedString(assetQuantity)).append("\n");
    sb.append("    assetNetPrice: ").append(toIndentedString(assetNetPrice)).append("\n");
    sb.append("    assetType: ").append(toIndentedString(assetType)).append("\n");
    sb.append("    assetTicker: ").append(toIndentedString(assetTicker)).append("\n");
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
