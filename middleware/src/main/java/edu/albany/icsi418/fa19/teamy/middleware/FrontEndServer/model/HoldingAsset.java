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
 * An asset held by a portfolio
 */
@ApiModel(description = "An asset held by a portfolio")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class HoldingAsset   {
  @JsonProperty("ownedAssetId")
  private Long ownedAssetId = null;

  @JsonProperty("assetName")
  private String assetName = null;

  @JsonProperty("assetTicker")
  private String assetTicker = null;

  @JsonProperty("assetType")
  private String assetType = null;

  @JsonProperty("quantity")
  private Double quantity = null;

  @JsonProperty("netValue")
  private Double netValue = null;

  public HoldingAsset ownedAssetId(Long ownedAssetId) {
    this.ownedAssetId = ownedAssetId;
    return this;
  }

  /**
   * Get ownedAssetId
   * @return ownedAssetId
  **/
  @ApiModelProperty(example = "123", value = "")
  
    public Long getOwnedAssetId() {
    return ownedAssetId;
  }

  public void setOwnedAssetId(Long ownedAssetId) {
    this.ownedAssetId = ownedAssetId;
  }

  public HoldingAsset assetName(String assetName) {
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

  public HoldingAsset assetTicker(String assetTicker) {
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

  public HoldingAsset assetType(String assetType) {
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

  public HoldingAsset quantity(Double quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * @return quantity
  **/
  @ApiModelProperty(example = "3.5", value = "")
  
    public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public HoldingAsset netValue(Double netValue) {
    this.netValue = netValue;
    return this;
  }

  /**
   * Get netValue
   * @return netValue
  **/
  @ApiModelProperty(example = "100", value = "")
  
    public Double getNetValue() {
    return netValue;
  }

  public void setNetValue(Double netValue) {
    this.netValue = netValue;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HoldingAsset holdingAsset = (HoldingAsset) o;
    return Objects.equals(this.ownedAssetId, holdingAsset.ownedAssetId) &&
        Objects.equals(this.assetName, holdingAsset.assetName) &&
        Objects.equals(this.assetTicker, holdingAsset.assetTicker) &&
        Objects.equals(this.assetType, holdingAsset.assetType) &&
        Objects.equals(this.quantity, holdingAsset.quantity) &&
        Objects.equals(this.netValue, holdingAsset.netValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ownedAssetId, assetName, assetTicker, assetType, quantity, netValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HoldingAsset {\n");
    
    sb.append("    ownedAssetId: ").append(toIndentedString(ownedAssetId)).append("\n");
    sb.append("    assetName: ").append(toIndentedString(assetName)).append("\n");
    sb.append("    assetTicker: ").append(toIndentedString(assetTicker)).append("\n");
    sb.append("    assetType: ").append(toIndentedString(assetType)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    netValue: ").append(toIndentedString(netValue)).append("\n");
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
