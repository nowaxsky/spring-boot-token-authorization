package org.cpm.zwl.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.cpm.zwl.authorization.manager.TokenManager;
import org.cpm.zwl.util.TokenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@Order(1)
@WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {

  @Autowired
  private TokenManager manager;

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }
  
  final String savePath = "/tokens/login";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    System.out.println("doFilter");
    
    String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
    System.out.println("path: " + path);
    boolean allowedPath = savePath.contains(path);
    if(allowedPath) {
      filterChain.doFilter(request, response);
      return;
    }

    // 從header中取得token
    String token = req.getHeader(TokenConstants.AUTHORIZATION);
    System.out.println("token: " + token);

    if (manager.checkToken(token)) {
      filterChain.doFilter(request, response);
      return;
    }
    // 驗證失敗則拋出錯誤
    System.out.println("cannot pass");
    throw new ServletException();


  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // TODO Auto-generated method stub

  }

}
