package com.rf.link.configurations;

import static com.rf.link.models.SomeErrorCode.RESOURCE_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.rf.link.models.SomeProperties;
import com.github.seanyinx.wing.core.exceptions.ErrorCode;
import com.github.seanyinx.wing.core.IpAwareUniqueIdGenerator;
import com.github.seanyinx.wing.core.UniqueIdGenerator;
import java.util.Collections;
import java.util.Map;
import java.net.UnknownHostException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
@EnableConfigurationProperties(SomeProperties.class)
public class SomeConfiguration {

  @Bean
  UniqueIdGenerator uniqueIdGenerator() throws UnknownHostException {
    return new IpAwareUniqueIdGenerator();
  }

  @Bean
  Map<ErrorCode, HttpStatus> errorCodes() {
    return Collections.singletonMap(RESOURCE_NOT_FOUND, NOT_FOUND);
  }
}
