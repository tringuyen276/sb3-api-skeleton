package com.digiex.utility.utility.web.model.res;

import com.fasterxml.jackson.annotation.JsonValue;

public interface ErrorCode {
  @JsonValue
  Integer getHttpStatusCode();

  String getSystemCode();
}
