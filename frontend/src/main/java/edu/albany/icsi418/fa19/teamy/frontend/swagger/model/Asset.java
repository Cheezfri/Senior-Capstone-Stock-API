/*
 * FrontEndApi
 * This api is connects the middleware and frontend of a portfolio management web application 
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package edu.albany.icsi418.fa19.teamy.frontend.swagger.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.AssetCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * Asset model
 */
@Schema(description = "Asset model")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-12-08T18:15:06.312Z[GMT]")
public class Asset {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("ticker")
  private String ticker = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("category")
  private AssetCategory category = null;

  @SerializedName("description")
  private String description = null;

  public Asset id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @Schema(example = "1", description = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Asset ticker(String ticker) {
    this.ticker = ticker;
    return this;
  }

   /**
   * Get ticker
   * @return ticker
  **/
  @Schema(example = "$APPL", description = "")
  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public Asset name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @Schema(example = "Apple, Inc.", description = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Asset category(AssetCategory category) {
    this.category = category;
    return this;
  }

   /**
   * Get category
   * @return category
  **/
  @Schema(description = "")
  public AssetCategory getCategory() {
    return category;
  }

  public void setCategory(AssetCategory category) {
    this.category = category;
  }

  public Asset description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @Schema(example = "App is an up and coming tech startup", description = "")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Asset asset = (Asset) o;
    return Objects.equals(this.id, asset.id) &&
        Objects.equals(this.ticker, asset.ticker) &&
        Objects.equals(this.name, asset.name) &&
        Objects.equals(this.category, asset.category) &&
        Objects.equals(this.description, asset.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ticker, name, category, description);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Asset {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ticker: ").append(toIndentedString(ticker)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
