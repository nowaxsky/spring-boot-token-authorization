package org.cpm.zwl.authorization.manager.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.cpm.zwl.authorization.manager.TokenManager;
import org.cpm.zwl.authorization.model.TokenModel;
import org.cpm.zwl.dao.entity.UserDetail;
import org.cpm.zwl.dao.persistence.UserDetailRepository;
import org.cpm.zwl.util.TokenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 以redis儲存或驗證token
 * 
 * @author CPM
 *
 */
@Component
public class RedisTokenManager implements TokenManager {

  @Autowired
  private RedisTemplate<String, UserDetail> redis;

  @Autowired
  private UserDetailRepository userDetailRepository;

  public TokenModel createToken(String userId) {

    // 以UUID作為token
    String token = UUID.randomUUID().toString().replace("-", "");
    TokenModel model = new TokenModel(userId, token);

    UserDetail userDetail = userDetailRepository.findByUserId(userId);

    // 儲存token資訊到redis並設置時間
    redis.boundValueOps(token).set(userDetail, TokenConstants.TOKEN_EXPIRES_MIN, TimeUnit.MINUTES);
    return model;
  }

  public boolean checkToken(String token) {
    if (token == null || token.length() == 0) {
      return false;
    }
    UserDetail userDetail = redis.boundValueOps(token).get();
    if (userDetail == null) {
      return false;
    }
    // 若驗證成功則延長token的到期時間
    redis.boundValueOps(token).expire(TokenConstants.TOKEN_EXPIRES_MIN, TimeUnit.MINUTES);
    System.out.println("userId: " + userDetail.getUserId());
    System.out.println("username: " + userDetail.getUsername());
    return true;
  }

  public void deleteToken(String userId) {
    redis.delete(userId);
  }
}
