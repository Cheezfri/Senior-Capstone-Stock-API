package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTransactionType;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The transaction made in a portfolio
 */
@ApiModel(description = "The transaction made in a portfolio")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class PortfolioTransaction   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("portfolioId")
  private Long portfolioId = null;

  @JsonProperty("portfolioname")
  private String portfolioname = null;

  @JsonProperty("type")
  private PortfolioTransactionType type = null;

  @JsonProperty("assetId")
  private Long assetId = null;

  @JsonProperty("assetName")
  private String assetName = null;

  @JsonProperty("dateTime")
  private OffsetDateTime dateTime = null;

  @JsonProperty("quantity")
  private Double quantity = null;

  @JsonProperty("price")
  private Double price = null;

  public PortfolioTransaction id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * The unique id of the portfolio transaction
   * @return id
  **/
  @ApiModelProperty(value = "The unique id of the portfolio transaction")
  
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
  @ApiModelProperty(value = "The id of the portfolio in which transaction occurs")
  
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
  @ApiModelProperty(example = "Aggressive", value = "The name of the portfolio")
  
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
  @ApiModelProperty(value = "")
  
    @Valid
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
  @ApiModelProperty(example = "12", value = "The unique id of the asset associated with the portfolio")
  
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
  @ApiModelProperty(example = "apple", value = "The name of the asset")

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
  @ApiModelProperty(value = "The time at which the transaction occurs")
  
    @Valid
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
  @ApiModelProperty(value = "The quantity of the asset")
  
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
  @ApiModelProperty(value = "The price of the asset")
  
    public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }


  @Override
  public boolean equals(Object o) {
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
