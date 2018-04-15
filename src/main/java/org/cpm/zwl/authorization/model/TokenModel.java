package org.cpm.zwl.authorization.model;

/**
 * 
 * @author CPM
 *
 */
public class TokenModel {

  /*
   * 用戶名
   */
  private String userId;

  /*
   * UUID
   */
  private String token;

  public TokenModel() {
    super();
  }

  public TokenModel(String userId, String token) {
    super();
    this.userId = userId;
    this.token = token;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
