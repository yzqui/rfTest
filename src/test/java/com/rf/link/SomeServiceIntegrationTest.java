//package com.rf.link;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.http.HttpMethod.GET;
//import static org.springframework.http.HttpStatus.OK;
//
//import com.rf.link.models.generated.LinkEntity;
//import com.rf.link.models.SomeRepository;
//import com.github.seanyinx.wing.spring.common.Response;
//import com.github.seanyinx.wing.test.containers.Containers;
//import com.github.seanyinx.wing.test.containers.RedisContainer;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@DirtiesContext
//public class SomeServiceIntegrationTest {
//
//  @ClassRule
//  public static final RedisContainer redis = Containers.REDIS;
//
//  @Autowired
//  private TestRestTemplate restTemplate;
//
//  @Autowired
//  private SomeRepository repository;
//
//  @Test
//  public void shouldGetData() {
//    ParameterizedTypeReference<Response<LinkEntity>> typeReference = new ParameterizedTypeReference<Response<LinkEntity>>() {
//    };
//    ResponseEntity<Response<LinkEntity>> responseEntity = restTemplate.exchange(
//        "/inner/entities/{id}",
//        GET,
//        null,
//        typeReference,
//        1);
//
//    assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
//    assertThat(responseEntity.getBody().getData().getFoo()).isEqualTo("bar");
//  }
//}
