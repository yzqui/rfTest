//package com.rf.link.interfaces;
//
//import static java.util.Collections.singletonList;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.rf.link.application.service.SomeService;
//import com.rf.link.configurations.SomeConfiguration;
//import com.rf.link.models.generated.LinkEntity;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = SomeController.class, excludeAutoConfiguration = SomeConfiguration.class)
//public class SomeControllerTest {
//
//  @MockBean
//  private SomeService someService;
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  private final LinkEntity entity = new LinkEntity();
//
//  @Before
//  public void setUp() {
//    entity.se(1L);
//    entity.setFoo("bar");
//  }
//
//  @Test
//  public void addEntity() throws Exception {
//    mockMvc.perform(post("/inner/entities")
//        .contentType(APPLICATION_JSON)
//        .content("{\n"
//            + "  \"foo\": \"bar\"\n"
//            + "}"))
//        .andExpect(status().isCreated());
//
//    verify(someService).add("bar");
//  }
//
//  @Test
//  public void deleteId() throws Exception {
//    mockMvc.perform(delete("/inner/entities/{id}", 1))
//        .andExpect(status().isNoContent());
//
//    verify(someService).deleteById(1);
//  }
//
//  @Test
//  public void getIdFromGenerator() throws Exception {
//    when(someService.getById(1L)).thenReturn(entity);
//
//    mockMvc.perform(get("/inner/entities/{id}", 1))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.code", is(200)))
//        .andExpect(jsonPath("$.data.someId", is(1)))
//        .andExpect(jsonPath("$.data.foo", is("bar")));
//  }
//}
