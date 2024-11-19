package com.digiex.utility.exception;

import com.digiex.utility.utility.web.model.res.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {
  INTERNAL_ERROR_SERVER("E_100_500_001", 500),
  INVALID_REQUEST_PARAMETER("E_100_400_001", 400),
  INVALID_HTTP_REQUEST_METHOD("E_100_400_002", 400),
  INVALID_HTTP_REQUEST_RESOURCE("E_100_400_003", 400),
  NOT_FOUND_HTTP_REQUEST_RESOURCE("E_100_404_001", 404),
  CONFLICT_RESOURCE_ERROR("E_100_409_001", 409);

  private String systemCode;

  private Integer httpStatusCode;
}
