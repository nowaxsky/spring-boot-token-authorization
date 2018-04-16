package org.cpm.zwl.util;

/**
 * response狀態定義
 * 
 * @author CPM
 *
 */
public enum ResultStatus {
  SUCCESS("success"), USERNAME_OR_PASSWORD_ERROR("username or password error"), USER_NOT_LOGIN("user not login");

  /*
   * 狀態說明
   */
  private String message;

  ResultStatus(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
