package com.rf.link.configurations;

import com.rf.link.infrastructure.LinkCountRepositoryImpl;
import com.rf.link.infrastructure.LinkRepositoryImpl;
import com.rf.link.models.generated.LinkCountMapper;
import com.rf.link.models.generated.LinkEntityMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.rf.link.models")
public class MybatisConfiguration {

  @Bean
  LinkRepositoryImpl linkRepository(LinkEntityMapper mapper) {
    return new LinkRepositoryImpl(mapper);
  }

  @Bean
  LinkCountRepositoryImpl linkCountRepository(LinkCountMapper mapper) {
    return new LinkCountRepositoryImpl(mapper);
  }
}
