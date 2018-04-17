package org.cpm.zwl.authorization.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    // 讓swagger不需驗證
    String path = request.getRequestURI().substring(request.getContextPath().length())
        .replaceAll("[/]+$", "");
    System.out.println("path: " + path);
    if (this.checkPath(path))
      return true;

    // 從header中取得token
    String token = request.getHeader(TokenConstants.AUTHORIZATION);
    System.out.println("token: " + token);

    if (manager.checkToken(token) != null) {
      System.out.println("pass");
      return true;
    }
    System.out.println("cannot pass");
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_OK);
    ObjectMapper mapper = new ObjectMapper();
    String errorMessage =
        mapper.writeValueAsString(ResponseModel.error(ResultStatus.USER_NOT_LOGIN));
    response.getOutputStream().println(errorMessage);
    return false;

  }

  private boolean checkPath(String path) {
    List<String> list = Arrays.asList("/tokens/login", "/swagger-ui.html", "/v2/api-docs");
    Set<String> whiteList = new HashSet<>(list);
    return whiteList.contains(path) || path.contains("/webjars/springfox-swagger-ui/")
        || path.contains("/swagger-resources");
  }
}
