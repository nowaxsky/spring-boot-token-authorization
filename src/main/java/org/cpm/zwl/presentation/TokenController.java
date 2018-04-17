package org.cpm.zwl.presentation;

import javax.servlet.http.HttpServletRequest;
import org.cpm.zwl.authorization.manager.TokenManager;
import org.cpm.zwl.authorization.model.TokenModel;
import org.cpm.zwl.dao.entity.User;
import org.cpm.zwl.dao.entity.UserDetail;
import org.cpm.zwl.dao.persistence.UserRepository;
import org.cpm.zwl.presentation.vos.ResponseModel;
import org.cpm.zwl.util.ResultStatus;
import org.cpm.zwl.util.TokenConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * token操作範例
 * 
 * @author CPM
 *
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenManager tokenManager;

  /**
   * 登入
   * 
   * @param userId
   * @param password
   * @return
   */
  @ApiOperation(value = "登入")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "userId", value = "帳號", required = true, dataType = "string",
          paramType = "query"),
      @ApiImplicitParam(name = "password", value = "密碼", required = true, dataType = "string",
          paramType = "query")})
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<ResponseModel> login(@RequestParam String userId,
      @RequestParam String password) {

    System.out.println("userId: " + userId);
    System.out.println("password: " + password);

    User user = userRepository.findByUserId(userId);

    // 未註冊或密碼錯誤則失敗
    if (user == null || !user.getPassword().equals(password)) {
      return new ResponseEntity<>(ResponseModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR),
          HttpStatus.NOT_FOUND);
    }

    // 產生一組token
    TokenModel model = tokenManager.createToken(user.getUserId());
    System.out.println("login");
    return new ResponseEntity<>(ResponseModel.ok(model), HttpStatus.OK);
  }

  /**
   * 登入後印出hello
   * 
   * @param name
   * @return
   */
  @ApiOperation(value = "登入後印出hello")
  @ApiImplicitParam(name = "authorization", value = "authorization", required = false,
      dataType = "string", paramType = "header")
  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public ResponseEntity<ResponseModel> hello(HttpServletRequest request) {
    UserDetail userDetail =
        tokenManager.checkToken(request.getHeader(TokenConstants.AUTHORIZATION));
    String hello = "hello: " + userDetail.getUsername() + "(" + userDetail.getUserId() + ")";
    return new ResponseEntity<>(ResponseModel.ok(hello), HttpStatus.OK);
  }

  /**
   * 登出
   * 
   * @param request
   * @return
   */
  @ApiOperation(value = "登出")
  @ApiImplicitParam(name = "authorization", value = "authorization", required = false,
      dataType = "string", paramType = "header")
  @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
  public ResponseEntity<ResponseModel> logout(HttpServletRequest request) {
    tokenManager.deleteToken(request.getHeader(TokenConstants.AUTHORIZATION));
    return new ResponseEntity<>(ResponseModel.ok(), HttpStatus.OK);
  }

}
