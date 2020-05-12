package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioType;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The user portfolio model
 */
@ApiModel(description = "The user portfolio model")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class Portfolio   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("deleted")
  private Boolean deleted = null;

  @JsonProperty("serialNumber")
  private Integer serialNumber = null;

  @JsonProperty("type")
  private PortfolioType type = null;

  @JsonProperty("ownerUserId")
  private Long ownerUserId = null;

  @JsonProperty("ownerFirstName")
  private String ownerFirstName = null;

  @JsonProperty("ownerLastName")
  private String ownerLastName = null;

  @JsonProperty("ownerEmail")
  private String ownerEmail = null;

  public Portfolio id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Unique Database Primary Key
   * @return id
  **/
  @ApiModelProperty(example = "1", value = "Unique Database Primary Key")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Portfolio name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "Portfolio A", value = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Portfolio deleted(Boolean deleted) {
    this.deleted = deleted;
    return this;
  }

  /**
   * Get deleted
   * @return deleted
  **/
  @ApiModelProperty(example = "false", value = "")
  
    public Boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public Portfolio serialNumber(Integer serialNumber) {
    this.serialNumber = serialNumber;
    return this;
  }

  /**
   * Get serialNumber
   * @return serialNumber
  **/
  @ApiModelProperty(example = "1820", value = "")
  
    public Integer getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(Integer serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Portfolio type(PortfolioType type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public PortfolioType getType() {
    return type;
  }

  public void setType(PortfolioType type) {
    this.type = type;
  }

  public Portfolio ownerUserId(Long ownerUserId) {
    this.ownerUserId = ownerUserId;
    return this;
  }

  /**
   * Get ownerUserId
   * @return ownerUserId
  **/
  @ApiModelProperty(example = "12345", value = "")
  
    public Long getOwnerUserId() {
    return ownerUserId;
  }

  public void setOwnerUserId(Long ownerUserId) {
    this.ownerUserId = ownerUserId;
  }

  public Portfolio ownerFirstName(String ownerFirstName) {
    this.ownerFirstName = ownerFirstName;
    return this;
  }

  /**
   * Get ownerFirstName
   * @return ownerFirstName
  **/
  @ApiModelProperty(example = "John", value = "")

    public String getOwnerFirstName() {
    return ownerFirstName;
  }

  public void setOwnerFirstName(String ownerFirstName) {
    this.ownerFirstName = ownerFirstName;
  }

  public Portfolio ownerLastName(String ownerLastName) {
    this.ownerLastName = ownerLastName;
    return this;
  }

  /**
   * Get ownerLastName
   * @return ownerLastName
  **/
  @ApiModelProperty(example = "doe", value = "")

    public String getOwnerLastName() {
    return ownerLastName;
  }

  public void setOwnerLastName(String ownerLastName) {
    this.ownerLastName = ownerLastName;
  }

  public Portfolio ownerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
    return this;
  }

  /**
   * Get ownerEmail
   * @return ownerEmail
  **/
  @ApiModelProperty(value = "")

    public String getOwnerEmail() {
    return ownerEmail;
  }

  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Portfolio portfolio = (Portfolio) o;
    return Objects.equals(this.id, portfolio.id) &&
        Objects.equals(this.name, portfolio.name) &&
        Objects.equals(this.deleted, portfolio.deleted) &&
        Objects.equals(this.serialNumber, portfolio.serialNumber) &&
        Objects.equals(this.type, portfolio.type) &&
        Objects.equals(this.ownerUserId, portfolio.ownerUserId) &&
        Objects.equals(this.ownerFirstName, portfolio.ownerFirstName) &&
        Objects.equals(this.ownerLastName, portfolio.ownerLastName) &&
        Objects.equals(this.ownerEmail, portfolio.ownerEmail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, deleted, serialNumber, type, ownerUserId, ownerFirstName, ownerLastName, ownerEmail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Portfolio {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    deleted: ").append(toIndentedString(deleted)).append("\n");
    sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    ownerUserId: ").append(toIndentedString(ownerUserId)).append("\n");
    sb.append("    ownerFirstName: ").append(toIndentedString(ownerFirstName)).append("\n");
    sb.append("    ownerLastName: ").append(toIndentedString(ownerLastName)).append("\n");
    sb.append("    ownerEmail: ").append(toIndentedString(ownerEmail)).append("\n");
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
