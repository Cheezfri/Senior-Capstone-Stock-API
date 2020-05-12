package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * The role of a user
 */
public enum AccessRole {
  USER("USER"),
    SUPPORT("SUPPORT"),
    ADMIN("ADMIN");

  private String value;

  AccessRole(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static AccessRole fromValue(String text) {
    for (AccessRole b : AccessRole.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
