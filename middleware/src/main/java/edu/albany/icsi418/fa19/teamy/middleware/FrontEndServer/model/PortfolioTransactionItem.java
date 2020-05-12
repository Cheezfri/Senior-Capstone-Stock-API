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
 * filled out PortfolioTransaction details
 */
@ApiModel(description = "filled out PortfolioTransaction details")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class PortfolioTransactionItem   {
  @JsonProperty("portfolioId")
  private Long portfolioId = null;

  @JsonProperty("assetId")
  private Long assetId = null;

  @JsonProperty("quantity")
  private Double quantity = null;

  @JsonProperty("date")
  private OffsetDateTime date = null;

  @JsonProperty("transactionType")
  private PortfolioTransactionType transactionType = null;

  @JsonProperty("assetPrice")
  private Double assetPrice = null;

  public PortfolioTransactionItem portfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
    return this;
  }

  /**
   * The portfolio in which the transaction that occurs
   * @return portfolioId
  **/
  @ApiModelProperty(example = "12345", value = "The portfolio in which the transaction that occurs")
  
    public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public PortfolioTransactionItem assetId(Long assetId) {
    this.assetId = assetId;
    return this;
  }

  /**
   * The Id of the asset which is being bought or sold in the transaction
   * @return assetId
  **/
  @ApiModelProperty(example = "1", value = "The Id of the asset which is being bought or sold in the transaction")
  
    public Long getAssetId() {
    return assetId;
  }

  public void setAssetId(Long assetId) {
    this.assetId = assetId;
  }

  public PortfolioTransactionItem quantity(Double quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * The quantity of asset that is being transacted
   * @return quantity
  **/
  @ApiModelProperty(example = "1.5", value = "The quantity of asset that is being transacted")
  
    public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public PortfolioTransactionItem date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * The date on which the transaction will occur
   * @return date
  **/
  @ApiModelProperty(value = "The date on which the transaction will occur")
  
    @Valid
    public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public PortfolioTransactionItem transactionType(PortfolioTransactionType transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * Get transactionType
   * @return transactionType
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public PortfolioTransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(PortfolioTransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public PortfolioTransactionItem assetPrice(Double assetPrice) {
    this.assetPrice = assetPrice;
    return this;
  }

  /**
   * the price of the asset incase a closing price is not provided
   * @return assetPrice
  **/
  @ApiModelProperty(example = "79.5", value = "the price of the asset incase a closing price is not provided")
  
    public Double getAssetPrice() {
    return assetPrice;
  }

  public void setAssetPrice(Double assetPrice) {
    this.assetPrice = assetPrice;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortfolioTransactionItem portfolioTransactionItem = (PortfolioTransactionItem) o;
    return Objects.equals(this.portfolioId, portfolioTransactionItem.portfolioId) &&
        Objects.equals(this.assetId, portfolioTransactionItem.assetId) &&
        Objects.equals(this.quantity, portfolioTransactionItem.quantity) &&
        Objects.equals(this.date, portfolioTransactionItem.date) &&
        Objects.equals(this.transactionType, portfolioTransactionItem.transactionType) &&
        Objects.equals(this.assetPrice, portfolioTransactionItem.assetPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portfolioId, assetId, quantity, date, transactionType, assetPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PortfolioTransactionItem {\n");
    
    sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    assetPrice: ").append(toIndentedString(assetPrice)).append("\n");
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
