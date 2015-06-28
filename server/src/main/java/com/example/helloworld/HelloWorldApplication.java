package com.example.helloworld;

import com.example.helloworld.db.*;
import com.example.helloworld.resources.EventResourse;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.ImmutableList;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.text.SimpleDateFormat;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    private static final ImmutableList<Class<?>> hibernateClasses = ImmutableList.of(
            Person.class,
            Event.class,
            Location.class,
            Group.class,
            Response.class);


    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    private final HibernateBundle<HelloWorldConfiguration> hibernateBundle =
            new HibernateBundle<HelloWorldConfiguration>(hibernateClasses, new SessionFactoryFactory()) {
                @Override
                public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "FOMO";
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
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        EventDao eventDao = new EventDao(hibernateBundle.getSessionFactory());
        environment.jersey().register(new EventResourse(eventDao));
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        environment.getObjectMapper().setDateFormat(SimpleDateFormat.getDateInstance());
    }
}
