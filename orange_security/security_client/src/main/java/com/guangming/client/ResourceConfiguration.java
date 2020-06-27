package com.guangming.client;

import com.guangming.exception.MyTokenExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${source.id}")
    private String SOURCE_ID; /*资源id*/
    @Resource
    private MyTokenExceptionEntryPoint tokenExceptionEntryPoint; /*自定义权限异常*/
    @Resource
    private RedisConnectionFactory redisConnectionFactory; /*redis连接*/

    //资源服务配置
    @Override
    @CrossOrigin
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(SOURCE_ID).stateless(true);
        resources.tokenServices(defaultTokenServices());
        resources.authenticationEntryPoint(tokenExceptionEntryPoint);

    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        // 放开请求不用认证
        http.csrf().disable()
                .authorizeRequests().antMatchers("/actuator/**", "/error", "/user/login", "/", "/webjars/**", "/resources/**",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/search/cx/**", "/code", "/user/register", "/user/logout").permitAll().and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated();
        http.headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable();  /*允许同源开iframe*/
        // @formatter:on
    }

    // 自定义的Token存储器，存到Redis中
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    // Token转换器
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
        };
        //实际生产中使用RSA加密
        accessTokenConverter.setSigningKey("SigningKey");
        return accessTokenConverter;
    }


    // 创建一个默认的资源服务token
    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        // 使用自定义的Token转换器
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        // 使用自定义的tokenStore
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}
