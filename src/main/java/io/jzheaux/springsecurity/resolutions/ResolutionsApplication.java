package io.jzheaux.springsecurity.resolutions;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(
        authz -> authz.mvcMatchers(HttpMethod.GET, "/resolutions", "/resolution/**")
            .hasAuthority("resolution:read").anyRequest().hasAuthority("resolution:write"))
        .httpBasic(httpSecurityHttpBasicConfigurer -> {
        });
  }

  public static void main(String[] args) {
    SpringApplication.run(ResolutionsApplication.class, args);
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository users) {
    return new UserRepositoryUserDetailsService(users);
  }
}
