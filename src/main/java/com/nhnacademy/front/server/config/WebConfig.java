package com.nhnacademy.front.server.config;

import com.nhnacademy.front.server.interceptor.AuthorizationInterceptor;
import com.nhnacademy.front.server.interceptor.ViewInterceptor;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "/login");
  }

  @Bean
  public ResponseErrorHandler responseErrorHandler() {
    return new ResponseErrorHandler() {
      @Override
      public boolean hasError(ClientHttpResponse response) throws IOException {
        return HttpStatus.OK.is2xxSuccessful();
      }

      @Override
      public void handleError(ClientHttpResponse response) throws IOException {
      }
    };
  }

  @Bean
  @LoadBalanced
  @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "test")
  public RestTemplate restTemplate(RestTemplateBuilder builder) {

    RestTemplate restTemplate = builder
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(5))
        .errorHandler(responseErrorHandler())
        .build();

    restTemplate.setInterceptors(List.of(new AuthorizationInterceptor()));

    return restTemplate;
  }

  @Bean
  @ConditionalOnProperty(value = "spring.profiles.active", havingValue = "dev")
  public RestTemplate restTemplateMocky(RestTemplateBuilder builder) {
    return builder
        .setConnectTimeout(Duration.ofSeconds(5))
        .setReadTimeout(Duration.ofSeconds(5))
        .build();
  }

  @Bean
  public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
    return new DeviceResolverHandlerInterceptor();
  }

  public ViewInterceptor viewInterceptor() {
    return new ViewInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(deviceResolverHandlerInterceptor());
    registry.addInterceptor(viewInterceptor());
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("https://www.aiotone.live", "https://aiotone.live")
        .allowedMethods("*")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
