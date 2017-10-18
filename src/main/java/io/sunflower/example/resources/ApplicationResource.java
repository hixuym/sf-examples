package io.sunflower.example.resources;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.ebean.EbeanServer;
import io.ebean.annotation.Transactional;
import io.sunflower.ewf.Result;
import io.sunflower.ewf.Results;
import io.sunflower.example.core.GreetingService;
import io.sunflower.example.core.User;
import io.sunflower.example.core.UserMapper;

@Singleton
public class ApplicationResource {

  @Inject
  private EbeanServer ebeanServer;

  @Inject
  private UserMapper userMapper;

  public Result index() {
    return Results.html();
  }

  @Inject
  private GreetingService greetingService;

  public Result hello() {
    return Results.json().render(greetingService.greeting("michael"));
  }

  @io.sunflower.mybatis.Transactional
  public Result userCount() {
    return Results.json().render(userMapper.getUserCount());
  }

  @Transactional
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
