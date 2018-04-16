package org.cpm.zwl.presentation.vos;

import org.cpm.zwl.util.ResultStatus;

/**
 * json回傳格式
 * 
 * @author CPM
 *
 */
public class ResponseModel {

  private boolean isSuccess;
  private String errorCode;
  private String message;
  private Object values;


  public boolean isSuccess() {
    return isSuccess;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

  public Object getValues() {
    return values;
  }

  public ResponseModel(boolean isSuccess, String errorCode, String message, Object values) {
    super();
    this.isSuccess = isSuccess;
    this.errorCode = errorCode;
    this.message = message;
    this.values = values;
  }

  public ResponseModel(ResultStatus status) {
    super();
    this.isSuccess = true;
    this.message = status.getMessage();
    this.values = "";
  }

  public ResponseModel(ResultStatus status, Object values) {
    this.isSuccess = true;
    this.message = status.getMessage();
    this.values = values;
  }

  public static ResponseModel ok(Object content) {
    return new ResponseModel(ResultStatus.SUCCESS, content);
  }

  public static ResponseModel ok() {
    return new ResponseModel(ResultStatus.SUCCESS);
  }

  public static ResponseModel error(ResultStatus error) {
    return new ResponseModel(error);
  }
}
