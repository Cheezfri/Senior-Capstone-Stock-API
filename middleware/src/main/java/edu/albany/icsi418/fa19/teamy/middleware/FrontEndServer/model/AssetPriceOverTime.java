package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetPriceData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * This the assetPrice over a certain time period
 */
@ApiModel(description = "This the assetPrice over a certain time period")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-26T18:04:30.614Z[GMT]")
public class AssetPriceOverTime   {
  @JsonProperty("assetName")
  private String assetName = null;

  @JsonProperty("assetId")
  private Long assetId = null;

  @JsonProperty("AssetPriceDataList")
  @Valid
  private List<AssetPriceData> assetPriceDataList = null;

  public AssetPriceOverTime assetName(String assetName) {
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

  public AssetPriceOverTime assetId(Long assetId) {
    this.assetId = assetId;
    return this;
  }

  /**
   * Get assetId
   * @return assetId
  **/
  @ApiModelProperty(value = "")
  
    public Long getAssetId() {
    return assetId;
  }

  public void setAssetId(Long assetId) {
    this.assetId = assetId;
  }

  public AssetPriceOverTime assetPriceDataList(List<AssetPriceData> assetPriceDataList) {
    this.assetPriceDataList = assetPriceDataList;
    return this;
  }

  public AssetPriceOverTime addAssetPriceDataListItem(AssetPriceData assetPriceDataListItem) {
    if (this.assetPriceDataList == null) {
      this.assetPriceDataList = new ArrayList<AssetPriceData>();
    }
    this.assetPriceDataList.add(assetPriceDataListItem);
    return this;
  }

  /**
   * Get assetPriceDataList
   * @return assetPriceDataList
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<AssetPriceData> getAssetPriceDataList() {
    return assetPriceDataList;
  }

  public void setAssetPriceDataList(List<AssetPriceData> assetPriceDataList) {
    this.assetPriceDataList = assetPriceDataList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssetPriceOverTime assetPriceOverTime = (AssetPriceOverTime) o;
    return Objects.equals(this.assetName, assetPriceOverTime.assetName) &&
        Objects.equals(this.assetId, assetPriceOverTime.assetId) &&
        Objects.equals(this.assetPriceDataList, assetPriceOverTime.assetPriceDataList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assetName, assetId, assetPriceDataList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssetPriceOverTime {\n");
    
    sb.append("    assetName: ").append(toIndentedString(assetName)).append("\n");
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    assetPriceDataList: ").append(toIndentedString(assetPriceDataList)).append("\n");
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
