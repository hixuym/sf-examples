package io.sunflower.example;

import io.sunflower.Application;
import io.sunflower.db.PooledDataSourceFactory;
import io.sunflower.ebean.EbeanBundle;
import io.sunflower.ewf.template.TemplateEngineFreemarker;
import io.sunflower.ewf.undertow.EwfBundle;
import io.sunflower.example.api.Routes;
import io.sunflower.example.cli.DumpConfigCommand;
import io.sunflower.mybatis.MybatisBundle;
import io.sunflower.setup.Bootstrap;
import io.sunflower.setup.Environment;

/**
 * Created by michael on 17/9/2.
 */
public class ExampleApplication extends Application<ExampleConfiguration> {

  public static void main(String[] args) throws Exception {
    new ExampleApplication().run(args);
  }

  @Override
  public String getName() {
    return "sf-example";
  }

  @Override
  public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
    bootstrap.addCommand(new DumpConfigCommand());
    bootstrap.addBundle(new EwfBundle<>());

    bootstrap.addBundle(new EbeanBundle<ExampleConfiguration>() {
      @Override
      public PooledDataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });

    bootstrap.addBundle(new MybatisBundle<ExampleConfiguration>() {
      @Override
      public PooledDataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });

  }

  @Override
  public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
    environment.guicey().registry(Routes.class);
    environment.guicey().registry(TemplateEngineFreemarker.class);
  }

}
