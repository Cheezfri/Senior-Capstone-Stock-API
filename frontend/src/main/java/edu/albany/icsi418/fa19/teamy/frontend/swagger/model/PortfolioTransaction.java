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
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.PortfolioTransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import org.threeten.bp.OffsetDateTime;
/**
 * The transaction made in a portfolio
 */
@Schema(description = "The transaction made in a portfolio")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-12-08T18:15:06.312Z[GMT]")
public class PortfolioTransaction {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("portfolioId")
  private Long portfolioId = null;

  @SerializedName("portfolioname")
  private String portfolioname = null;

  @SerializedName("type")
  private PortfolioTransactionType type = null;

  @SerializedName("assetId")
  private Long assetId = null;

  @SerializedName("assetName")
  private String assetName = null;

  @SerializedName("dateTime")
  private OffsetDateTime dateTime = null;

  @SerializedName("quantity")
  private Double quantity = null;

  @SerializedName("price")
  private Double price = null;

  public PortfolioTransaction id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * The unique id of the portfolio transaction
   * @return id
  **/
  @Schema(description = "The unique id of the portfolio transaction")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PortfolioTransaction portfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
    return this;
  }

   /**
   * The id of the portfolio in which transaction occurs
   * @return portfolioId
  **/
  @Schema(description = "The id of the portfolio in which transaction occurs")
  public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public PortfolioTransaction portfolioname(String portfolioname) {
    this.portfolioname = portfolioname;
    return this;
  }

   /**
   * The name of the portfolio
   * @return portfolioname
  **/
  @Schema(example = "Aggressive", description = "The name of the portfolio")
  public String getPortfolioname() {
    return portfolioname;
  }

  public void setPortfolioname(String portfolioname) {
    this.portfolioname = portfolioname;
  }

  public PortfolioTransaction type(PortfolioTransactionType type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @Schema(description = "")
  public PortfolioTransactionType getType() {
    return type;
  }

  public void setType(PortfolioTransactionType type) {
    this.type = type;
  }

  public PortfolioTransaction assetId(Long assetId) {
    this.assetId = assetId;
    return this;
  }

   /**
   * The unique id of the asset associated with the portfolio
   * @return assetId
  **/
  @Schema(example = "12", description = "The unique id of the asset associated with the portfolio")
  public Long getAssetId() {
    return assetId;
  }

  public void setAssetId(Long assetId) {
    this.assetId = assetId;
  }

  public PortfolioTransaction assetName(String assetName) {
    this.assetName = assetName;
    return this;
  }

   /**
   * The name of the asset
   * @return assetName
  **/
  @Schema(example = "apple", description = "The name of the asset")
  public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public PortfolioTransaction dateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

   /**
   * The time at which the transaction occurs
   * @return dateTime
  **/
  @Schema(description = "The time at which the transaction occurs")
  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public PortfolioTransaction quantity(Double quantity) {
    this.quantity = quantity;
    return this;
  }

   /**
   * The quantity of the asset
   * @return quantity
  **/
  @Schema(description = "The quantity of the asset")
  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public PortfolioTransaction price(Double price) {
    this.price = price;
    return this;
  }

   /**
   * The price of the asset
   * @return price
  **/
  @Schema(description = "The price of the asset")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortfolioTransaction portfolioTransaction = (PortfolioTransaction) o;
    return Objects.equals(this.id, portfolioTransaction.id) &&
        Objects.equals(this.portfolioId, portfolioTransaction.portfolioId) &&
        Objects.equals(this.portfolioname, portfolioTransaction.portfolioname) &&
        Objects.equals(this.type, portfolioTransaction.type) &&
        Objects.equals(this.assetId, portfolioTransaction.assetId) &&
        Objects.equals(this.assetName, portfolioTransaction.assetName) &&
        Objects.equals(this.dateTime, portfolioTransaction.dateTime) &&
        Objects.equals(this.quantity, portfolioTransaction.quantity) &&
        Objects.equals(this.price, portfolioTransaction.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, portfolioId, portfolioname, type, assetId, assetName, dateTime, quantity, price);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PortfolioTransaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
    sb.append("    portfolioname: ").append(toIndentedString(portfolioname)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    assetName: ").append(toIndentedString(assetName)).append("\n");
    sb.append("    dateTime: ").append(toIndentedString(dateTime)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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
