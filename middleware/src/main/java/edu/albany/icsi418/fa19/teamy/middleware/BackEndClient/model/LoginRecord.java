/*
 * BackEndApi
 * This api is connects the middleware and backend of a portfolio management web application
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model;

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
 * login log
 */
@Schema(description = "login log")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-11-16T01:51:45.397Z[GMT]")
public class LoginRecord {
  @SerializedName("id")
  private Long id = null;

  @SerializedName("email")
  private String email = null;

  @SerializedName("success")
  private Boolean success = null;

  @SerializedName("ipAddress")
  private String ipAddress = null;

  @SerializedName("date")
  private OffsetDateTime date = null;

  public LoginRecord id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @Schema(description = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LoginRecord email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  @Schema(description = "")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LoginRecord success(Boolean success) {
    this.success = success;
    return this;
  }

   /**
   * Get success
   * @return success
  **/
  @Schema(description = "")
  public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public LoginRecord ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

   /**
   * Get ipAddress
   * @return ipAddress
  **/
  @Schema(description = "")
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public LoginRecord date(OffsetDateTime date) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginRecord loginRecord = (LoginRecord) o;
    return Objects.equals(this.id, loginRecord.id) &&
        Objects.equals(this.email, loginRecord.email) &&
        Objects.equals(this.success, loginRecord.success) &&
        Objects.equals(this.ipAddress, loginRecord.ipAddress) &&
        Objects.equals(this.date, loginRecord.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, success, ipAddress, date);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginRecord {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
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
