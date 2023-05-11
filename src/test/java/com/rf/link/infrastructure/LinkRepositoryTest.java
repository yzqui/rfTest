package com.rf.link.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.rf.link.configurations.MybatisConfiguration;
import com.rf.link.models.LinkRepository;
import com.rf.link.models.generated.LinkEntity;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@MybatisTest
@ActiveProfiles("test")
@Import(MybatisConfiguration.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LinkRepositoryTest {

    @Autowired
    private LinkRepository linkRepository;

    @Test
    public void shouldGetOneFromDatabase() {
        Optional<LinkEntity> linkEntity = linkRepository.selectByPrimaryKey(2L);

        LinkEntity entity = linkEntity.orElse(null);
        assertThat(entity).isNotNull();
        System.out.println("------------------------");
        System.out.println(new Gson().toJson(entity));
        System.out.println("------------------------");
        assertThat(entity.getId()).isEqualTo(2);
        assertThat(entity.getShortUrlId()).isEqualTo("7061681686932764610");
    }

    @Test
    public void shouldGetAllFromDB() {
        List<LinkEntity> entities = linkRepository.selectAll(0, 50);

        LinkEntity entity = entities.get(0);
        assertThat(entity.getId()).isEqualTo(1);
        assertThat(entity.getShortUrlId()).isEqualTo("7061680734230165441");
    }

    @Test
    public void shouldSaveFromDB() {
        Optional<LinkEntity> linkEntity = linkRepository.selectByPrimaryKey(1L);

        LinkEntity entity = linkEntity.orElse(null);
        assertThat(entity).isNotNull();
        String originalUrl = entity.getOriginalUrl();
        entity.setOriginalUrl(originalUrl + 1);
        int i = linkRepository.updateByPrimaryKey(entity);
        assertThat(i).isEqualTo(1);
    }

    @Test
    public void insert(){
        LinkEntity entity = new LinkEntity();
        entity.setShortUrlId("123");
        entity.setShortUrl("http://short.url/123");
        entity.setOriginalUrl("http://hsjdhsjhdjahsjhdajsda/dshjdhjshajd/1");
        int insert = linkRepository.insert(entity);
        assertThat(insert).isEqualTo(1);
    }

    @Test
    public void delete(){
        int i = linkRepository.deleteByPrimaryKey(1L);
        assertThat(i).isEqualTo(1);
    }

    @Test
    public void getByShortUrl(){
        Optional<LinkEntity> entityOptional = linkRepository.getByShortUrl("http://short.url/7061680734230165441");
        assertThat(entityOptional.isPresent()).isEqualTo(true);
        assertThat(entityOptional.get().getOriginalUrl()).isEqualTo("https://www.baidu.com/?tn=020033901_19_hao_pg");
    }
}
