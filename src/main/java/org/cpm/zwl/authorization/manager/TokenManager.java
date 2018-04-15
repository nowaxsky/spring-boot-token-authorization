package org.cpm.zwl.authorization.manager;

import org.cpm.zwl.authorization.model.TokenModel;

/**
 * token管理
 * 
 * @author CPM
 *
 */
public interface TokenManager {

  /**
   * 建立token
   * 
   * @param userId
   * @return
   */
  public TokenModel createToken(String userId);

  /**
   * 檢查token是否有效
   * 
   * @param token
   * @return
   */
  public boolean checkToken(String token);

  /**
   * 清除token
   * 
   * @param userId
   */
  public void deleteToken(String userId);

}
