package com.sc4r.springcloud.msvc.usuarios;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sc4r.springcloud.msvc.usuarios.config.AppConfig;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class MsvcUsuariosApplication {

  @Autowired
  private AppConfig config;

  public static void main(String[] args) {
    SpringApplication.run(MsvcUsuariosApplication.class, args);
  }

  @Bean
  public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration conf = new CorsConfiguration();
    conf.setAllowCredentials(true);
    conf.setAllowedOriginPatterns(Collections.singletonList("*"));
    conf.setAllowedMethods(Collections.singletonList("*"));
    conf.setAllowedHeaders(Collections.singletonList("*"));
    source.registerCorsConfiguration("/**", conf);
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(new String(new byte[] { 42 }))
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods", "Accept", "Authorization", "Content-Type", "Method",
                "Origin", "X-Forwarded-For", "X-Real-IP");
      }
    };
  }

}
