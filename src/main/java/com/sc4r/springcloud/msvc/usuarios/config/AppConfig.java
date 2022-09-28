package com.sc4r.springcloud.msvc.usuarios.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AppConfig
 */
@Component
@ConfigurationProperties(prefix = "app")

public class AppConfig {

  private String authUri;
  private boolean ignoreSession;
  private String allowedOrigins = "**";
  private String allowedMethods = "GET, POST, PUT, DELETE, OPTIONS";
  private String allowedHeaders = "Access-Control-Allow-Origin, Access-Control-Allow-Headers, Access-Control-Allow-Methods, Accept, "
      + "Authorization, Content-Type, Method, Origin, X-Forwarded-For, X-Real-IP";

  // public AppConfig(String authUri, boolean ignoreSession, String
  // allowedOrigins, String allowedMethods,
  // String allowedHeaders) {
  // this.authUri = authUri;
  // this.ignoreSession = ignoreSession;
  // this.allowedOrigins = allowedOrigins;
  // this.allowedMethods = allowedMethods;
  // this.allowedHeaders = allowedHeaders;
  // }

  public String getAuthUri() {
    return authUri;
  }

  public void setAuthUri(String authUri) {
    this.authUri = authUri;
  }

  public boolean isIgnoreSession() {
    return ignoreSession;
  }

  public void setIgnoreSession(boolean ignoreSession) {
    this.ignoreSession = ignoreSession;
  }

  public String getAllowedOrigins() {
    return allowedOrigins;
  }

  public void setAllowedOrigins(String allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }

  public String getAllowedMethods() {
    return allowedMethods;
  }

  public void setAllowedMethods(String allowedMethods) {
    this.allowedMethods = allowedMethods;
  }

  public String getAllowedHeaders() {
    return allowedHeaders;
  }

  public void setAllowedHeaders(String allowedHeaders) {
    this.allowedHeaders = allowedHeaders;
  }
}
