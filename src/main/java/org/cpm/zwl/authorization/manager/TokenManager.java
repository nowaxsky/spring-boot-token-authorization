package org.cpm.zwl.authorization.manager;

import org.cpm.zwl.authorization.model.TokenModel;
import org.cpm.zwl.dao.entity.UserDetail;

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
  public UserDetail checkToken(String token);

  /**
   * 清除token
   * 
   * @param userId
   */
  public void deleteToken(String userId);

}
