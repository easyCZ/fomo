package com.example.helloworld;

import com.example.helloworld.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

//    private final HibernateBundle<HelloWorldConfiguration> hibernateBundle =
//            new HibernateBundle<HelloWorldConfiguration>(Person.class) {
//                @Override
//                public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
//                    return configuration.getDataSourceFactory();
//                }
//            };

    @Override
    public String getName() {
        return "fomo";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
//
//        bootstrap.addBundle(new AssetsBundle());
//        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
//            @Override
//            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
//                return configuration.getDataSourceFactory();
//            }
//        });
//        bootstrap.addBundle(hibernateBundle);
//        bootstrap.addBundle(new ViewBundle<HelloWorldConfiguration>() {
//            @Override
//            public Map<String, Map<String, String>> getViewConfiguration(HelloWorldConfiguration configuration) {
//                return configuration.getViewRendererConfiguration();
//            }
//        });
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        environment.jersey().register(new HelloWorldResource());
    }
}
