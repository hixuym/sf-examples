package io.sunflower.example.controllers;

import io.ebean.EbeanServer;
import io.ebean.annotation.Transactional;
import io.sunflower.ewf.Result;
import io.sunflower.ewf.Results;
import io.sunflower.ewf.jaxy.GET;
import io.sunflower.ewf.jaxy.Path;
import io.sunflower.example.core.GreetingService;
import io.sunflower.example.core.User;
import io.sunflower.example.core.UserMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author michael
 */
@Singleton
@Path("")
public class ApplicationController {

    @Inject
    private EbeanServer ebeanServer;

    @Inject
    private UserMapper userMapper;

    @Path("/")
    public Result index() {
        return Results.html();
    }

    @Inject
    private GreetingService greetingService;

    @Path("/hello")
    @GET
    public Result hello() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Results.json().render(greetingService.greeting("michael"));
    }

    @io.sunflower.mybatis.Transactional
    @Path("/user_count")
    @GET
    public Result userCount() {
        return Results.json().render(userMapper.getUserCount());
    }

    @Transactional(rollbackFor = Exception.class)
    @Path("/hello_world.json")
    public Result helloWorldJson() {
        User u = new User();

        u.setName("michael");
        u.setAge(30);
        ebeanServer.save(u);

        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }

    public static class SimplePojo {

        public String content;

    }
}
