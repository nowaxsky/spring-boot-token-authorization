package org.cpm.zwl.authorization.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cpm.zwl.authorization.manager.TokenManager;
import org.cpm.zwl.presentation.vos.ResponseModel;
import org.cpm.zwl.util.ResultStatus;
import org.cpm.zwl.util.TokenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 判斷此次請求是否有權限
 * 
 * @author CPM
 *
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  private TokenManager manager;

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // 從header中取得token
    String token = request.getHeader(TokenConstants.AUTHORIZATION);
    System.out.println("token: " + token);

    if (manager.checkToken(token)) {
      System.out.println("pass");
      return true;
    }
    System.out.println("cannot pass");
    ObjectMapper mapper = new ObjectMapper();
    String result = mapper.writeValueAsString(ResponseModel.error(ResultStatus.USER_NOT_LOGIN));
    response.getOutputStream().println(result);
    return false;

  }
}
