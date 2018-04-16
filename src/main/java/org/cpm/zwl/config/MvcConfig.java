package org.cpm.zwl.config;

import org.cpm.zwl.authorization.interceptor.AuthorizationInterceptor;
import org.cpm.zwl.authorization.manager.impl.JsonRedisSerializer;
import org.cpm.zwl.dao.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置攔截器
 * 
 * @author CPM
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private AuthorizationInterceptor authorizationInterceptor;


  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(authorizationInterceptor)
        // 攔截所有路徑除了excludePathPatterns之外
        .addPathPatterns("/**")
        // 配置不攔截路徑
        .excludePathPatterns("/tokens/login/**");
  }

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  RedisTemplate<String, UserDetail> redisTemplate() {
    final RedisTemplate<String, UserDetail> template = new RedisTemplate<String, UserDetail>();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setKeySerializer(new JdkSerializationRedisSerializer());
    // 可存入Json
    template.setValueSerializer(new JsonRedisSerializer());
    return template;
  }
}
