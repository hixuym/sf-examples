package io.sunflower.example;

import com.google.inject.AbstractModule;
import io.sunflower.Application;
import io.sunflower.db.PooledDataSourceFactory;
import io.sunflower.ebean.EbeanBundle;
import io.sunflower.ewf.assets.AssetsRoutes;
import io.sunflower.ewf.auth.AuthRoutes;
import io.sunflower.ewf.auth.UsernamePasswordValidator;
import io.sunflower.ewf.internal.template.TemplateEngineFreemarker;
import io.sunflower.ewf.undertow.EwfBundle;
import io.sunflower.example.cli.DumpConfigCommand;
import io.sunflower.example.core.SimpleUsernamePasswordValidator;
import io.sunflower.setup.Bootstrap;
import io.sunflower.setup.Environment;

/**
 * @author michael created on 17/9/2
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

//        bootstrap.addBundle(new MybatisBundle<ExampleConfiguration>() {
//            @Override
//            public PooledDataSourceFactory getDataSourceFactory(ExampleConfiguration configuration) {
//                return configuration.getDataSourceFactory();
//            }
//        });

    }

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) throws Exception {

        environment.guice().register(new AbstractModule() {
            @Override
            protected void configure() {
                bind(AssetsRoutes.class);
                bind(AuthRoutes.class);
                bind(TemplateEngineFreemarker.class);
                bind(UsernamePasswordValidator.class).to(SimpleUsernamePasswordValidator.class);
            }
        });
    }

}
