package com.guangming.configbeans;

import com.alibaba.fastjson.JSON;
import com.guangming.exception.MyOauthWebResponseExceptionTranslator;
import com.guangming.pojo.User;
import com.guangming.service.IOauthService;
import com.guangming.service.impl.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    AuthenticationManager authenticationManager;
    @Resource
    RedisConnectionFactory redisConnectionFactory;

    @Resource
    private MyUserDetailsService userDetailsService;

    @Resource
    private IOauthService oauthService;
    @Resource
    private DataSource dataSource;
    @Resource
    private MyOauthWebResponseExceptionTranslator webResponseExceptionTranslator;    //自定义异常处理

    @Bean
    public JdbcClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService()); //客户端信息
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.accessTokenConverter(accessTokenConverter());
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
        endpoints.userDetailsService(userDetailsService);
        endpoints.exceptionTranslator(webResponseExceptionTranslator);//认证异常处理器
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 允许表单认证
        oauthServer.allowFormAuthenticationForClients();
    }

    // JWT
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /***
             * 重写增强token方法,用于自定义一些token总需要封装的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String authorityId = authentication.getUserAuthentication().getName();
                // 得到用户名，去处理数据库可以拿到当前用户的信息和角色信息（需要传递到服务中用到的信息）
                final Map<String, Object> additionalInformation = new HashMap<>();
                User user = oauthService.getUserInfo(authorityId);
                additionalInformation.put("userInfo", JSON.toJSONString(user));
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                return super.enhance(accessToken, authentication);
            }
        };
        // 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        accessTokenConverter.setSigningKey("SigningKey");
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

}
