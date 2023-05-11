//package com.rf.link.interfaces;
//
//import static org.springframework.http.HttpStatus.CREATED;
//import static org.springframework.http.HttpStatus.NO_CONTENT;
//import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
//
//import com.rf.link.application.service.SomeService;
//import com.rf.link.models.SomeRequest;
//import com.rf.link.models.generated.LinkEntity;
//import com.github.seanyinx.wing.spring.common.Response;
//
//import java.util.List;
//import javax.annotation.Resource;
//import javax.validation.constraints.Positive;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/inner/entities")
//public class SomeController {
//
//    @Autowired
//    private SomeService someService;
//    @Autowired
//    private Config config;
//    @Resource
//    private RedisTemplate<String, String> redisTemplate;
//
//    @PostMapping(consumes = APPLICATION_JSON_VALUE)
//    @ResponseStatus(CREATED)
//    public void addLinkEntity(@RequestBody SomeRequest request) {
//        someService.add(request.getFoo());
//    }
//
//    @GetMapping("/{id}")
//    public Response<LinkEntity> getLinkEntity(@PathVariable long id) {
//        System.out.println(config.getTimeout());
////        redisTemplate.opsForValue().set("a-y", "b-z", 100, TimeUnit.MILLISECONDS);
//        return Response.ok(someService.getById(id));
//    }
//
//    @GetMapping("")
//    public Response<List<LinkEntity>> getAll(
//            @Positive @RequestParam(value = "cursor", required = false, defaultValue = "0") long cursor,
//            @Positive @RequestParam(value = "limit", required = false, defaultValue = "50") Long limit) {
//        List<LinkEntity> entities = someService.getAll(cursor, limit);
//        if (entities.isEmpty() || entities.size() < limit) {
//            return Response.ok(entities);
//        }
//        return Response.ok(entities, entities.get(entities.size() - 1).getId());
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(NO_CONTENT)
//    public void deleteLinkEntity(@PathVariable long id) {
//        someService.deleteById(id);
//    }
//}
