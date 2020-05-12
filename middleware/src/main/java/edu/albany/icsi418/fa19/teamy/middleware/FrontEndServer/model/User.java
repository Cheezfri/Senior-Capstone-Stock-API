package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AccessLevel;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * model containing user info
 */
@ApiModel(description = "model containing user info")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class User   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("accesslevel")
  private AccessLevel accesslevel = null;

  @JsonProperty("locked")
  private Boolean locked = null;

  @JsonProperty("localCurrency")
  private String localCurrency = null;

  public User id(Long id) {
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

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * User Email Address
   * @return email
  **/
  @ApiModelProperty(example = "user@example.com", value = "User Email Address")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * User's First Name
   * @return firstName
  **/
  @ApiModelProperty(example = "John", value = "User's First Name")
  
    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public User lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * User's Last Name
   * @return lastName
  **/
  @ApiModelProperty(example = "Doe", value = "User's Last Name")
  
    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User accesslevel(AccessLevel accesslevel) {
    this.accesslevel = accesslevel;
    return this;
  }

  /**
   * Get accesslevel
   * @return accesslevel
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public AccessLevel getAccesslevel() {
    return accesslevel;
  }

  public void setAccesslevel(AccessLevel accesslevel) {
    this.accesslevel = accesslevel;
  }

  public User locked(Boolean locked) {
    this.locked = locked;
    return this;
  }

  /**
   * Get locked
   * @return locked
  **/
  @ApiModelProperty(example = "true", value = "")
  
    public Boolean isLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public User localCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
    return this;
  }

  /**
   * Get localCurrency
   * @return localCurrency
  **/
  @ApiModelProperty(example = "Yen", value = "")
  
    public String getLocalCurrency() {
    return localCurrency;
  }

  public void setLocalCurrency(String localCurrency) {
    this.localCurrency = localCurrency;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.firstName, user.firstName) &&
        Objects.equals(this.lastName, user.lastName) &&
        Objects.equals(this.accesslevel, user.accesslevel) &&
        Objects.equals(this.locked, user.locked) &&
        Objects.equals(this.localCurrency, user.localCurrency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, firstName, lastName, accesslevel, locked, localCurrency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    accesslevel: ").append(toIndentedString(accesslevel)).append("\n");
    sb.append("    locked: ").append(toIndentedString(locked)).append("\n");
    sb.append("    localCurrency: ").append(toIndentedString(localCurrency)).append("\n");
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
