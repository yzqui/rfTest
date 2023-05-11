package com.rf.link.interfaces;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rf.link.application.service.LinkService;
import com.rf.link.configurations.SomeConfiguration;
import com.rf.link.models.generated.LinkEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LinkController.class, excludeAutoConfiguration = SomeConfiguration.class)
public class LinkControllerTest {

  @MockBean
  private LinkService linkService;

  @Autowired
  private MockMvc mockMvc;

  private final LinkEntity entity = new LinkEntity();

  @Before
  public void setUp() {
    entity.setId(1L);
  }

  @Test
  public void addEntity() throws Exception {
    mockMvc.perform(post("/link/save")
        .contentType(APPLICATION_JSON)
        .content("{" +
                "    \"originalUrl\":\"https://www.npr.org/2020/06/01/865510819/little-progress-made-in-trumps-plan-for-private-retailers-to-test-for-coronaviru\"" +
                "}"))
        .andExpect(status().isCreated());

    verify(linkService).add("https://www.npr.org/2020/06/01/865510819/little-progress-made-in-trumps-plan-for-private-retailers-to-test-for-coronaviru");
  }

  @Test
  public void deleteId() throws Exception {
    mockMvc.perform(delete("/link/{id}", 1))
        .andExpect(status().isNoContent());

    verify(linkService).deleteById(1);
  }

  @Test
  public void getIdFromGenerator() throws Exception {
    when(linkService.getById(1L)).thenReturn(entity);

    mockMvc.perform(get("/link/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)))
        .andExpect(jsonPath("$.data.id", is(1)));
  }

  @Test
  public void getOriginalUrlFromShortUrl() throws Exception {

    mockMvc.perform(get("/link/getByShortUrl/{shortUrl}",""))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", is(200)))
            .andExpect(jsonPath("$.data.id", is(1)));
  }
}
