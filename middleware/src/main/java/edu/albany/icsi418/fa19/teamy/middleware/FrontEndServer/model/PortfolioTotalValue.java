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
 * The total value of the portfolio on a particular date
 */
@ApiModel(description = "The total value of the portfolio on a particular date")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-07T21:36:00.664Z[GMT]")
public class PortfolioTotalValue   {
  @JsonProperty("portfolioId")
  private Long portfolioId = null;

  @JsonProperty("portfolioValue")
  private Double portfolioValue = null;

  @JsonProperty("date")
  private OffsetDateTime date = null;

  @JsonProperty("percentIncrease")
  private Double percentIncrease = null;

  @JsonProperty("macdIndexValue")
  private Double macdIndexValue = null;

  public PortfolioTotalValue portfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
    return this;
  }

  /**
   * Get portfolioId
   * @return portfolioId
  **/
  @ApiModelProperty(value = "")
  
    public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public PortfolioTotalValue portfolioValue(Double portfolioValue) {
    this.portfolioValue = portfolioValue;
    return this;
  }

  /**
   * Get portfolioValue
   * @return portfolioValue
  **/
  @ApiModelProperty(value = "")
  
    public Double getPortfolioValue() {
    return portfolioValue;
  }

  public void setPortfolioValue(Double portfolioValue) {
    this.portfolioValue = portfolioValue;
  }

  public PortfolioTotalValue date(OffsetDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(example = "1996-12-19T16:39:57-08:00", value = "")
  
    @Valid
    public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public PortfolioTotalValue percentIncrease(Double percentIncrease) {
    this.percentIncrease = percentIncrease;
    return this;
  }

  /**
   * Get percentIncrease
   * @return percentIncrease
  **/
  @ApiModelProperty(value = "")
  
    public Double getPercentIncrease() {
    return percentIncrease;
  }

  public void setPercentIncrease(Double percentIncrease) {
    this.percentIncrease = percentIncrease;
  }

  public PortfolioTotalValue macdIndexValue(Double macdIndexValue) {
    this.macdIndexValue = macdIndexValue;
    return this;
  }

  /**
   * Get macdIndexValue
   * @return macdIndexValue
  **/
  @ApiModelProperty(value = "")
  
    public Double getMacdIndexValue() {
    return macdIndexValue;
  }

  public void setMacdIndexValue(Double macdIndexValue) {
    this.macdIndexValue = macdIndexValue;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortfolioTotalValue portfolioTotalValue = (PortfolioTotalValue) o;
    return Objects.equals(this.portfolioId, portfolioTotalValue.portfolioId) &&
        Objects.equals(this.portfolioValue, portfolioTotalValue.portfolioValue) &&
        Objects.equals(this.date, portfolioTotalValue.date) &&
        Objects.equals(this.percentIncrease, portfolioTotalValue.percentIncrease) &&
        Objects.equals(this.macdIndexValue, portfolioTotalValue.macdIndexValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portfolioId, portfolioValue, date, percentIncrease, macdIndexValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PortfolioTotalValue {\n");
    
    sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
    sb.append("    portfolioValue: ").append(toIndentedString(portfolioValue)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    percentIncrease: ").append(toIndentedString(percentIncrease)).append("\n");
    sb.append("    macdIndexValue: ").append(toIndentedString(macdIndexValue)).append("\n");
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
