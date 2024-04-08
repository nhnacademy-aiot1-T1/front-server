package com.nhnacademy.front.server.config;

import com.nhnacademy.front.server.filter.LoginCheckFilter;
import com.nhnacademy.front.server.service.AuthService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * 스프링 부트를 활용하기 위한 wevConfig 파일입니다!
 * @author AoiTuNa
 * @version 1.0
 * @see #restTemplate(RestTemplateBuilder)
 */
@Configuration
public class WebConfig {

    /**
     * bean으로 등록된 restTemplate 객체입니다!
     * @param builder bean을 설정하기위한 빌더 입니다!
     * @return 설정된 빌더를 리턴합니다!
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean<LoginCheckFilter> loginCheckFilterRegistrationBean(AuthService authService){
        FilterRegistrationBean<LoginCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginCheckFilter(authService));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
