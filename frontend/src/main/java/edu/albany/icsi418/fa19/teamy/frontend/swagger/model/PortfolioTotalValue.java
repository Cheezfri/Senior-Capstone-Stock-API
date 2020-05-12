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
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import org.threeten.bp.OffsetDateTime;
/**
 * The total value of the portfolio on a particular date
 */
@Schema(description = "The total value of the portfolio on a particular date")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-12-08T18:15:06.312Z[GMT]")
public class PortfolioTotalValue {
  @SerializedName("portfolioId")
  private Long portfolioId = null;

  @SerializedName("portfolioValue")
  private Double portfolioValue = null;

  @SerializedName("date")
  private OffsetDateTime date = null;

  @SerializedName("percentIncrease")
  private Double percentIncrease = null;

  @SerializedName("macdIndexValue")
  private Double macdIndexValue = null;

  public PortfolioTotalValue portfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
    return this;
  }

   /**
   * Get portfolioId
   * @return portfolioId
  **/
  @Schema(description = "")
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
  @Schema(description = "")
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
  @Schema(example = "1996-12-19T16:39:57-08:00", description = "")
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
  @Schema(description = "")
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
  @Schema(description = "")
  public Double getMacdIndexValue() {
    return macdIndexValue;
  }

  public void setMacdIndexValue(Double macdIndexValue) {
    this.macdIndexValue = macdIndexValue;
  }


  @Override
  public boolean equals(java.lang.Object o) {
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}