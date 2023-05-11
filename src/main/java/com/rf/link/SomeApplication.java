package com.rf.link;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApolloConfig
public class SomeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SomeApplication.class, args);
  }
}
