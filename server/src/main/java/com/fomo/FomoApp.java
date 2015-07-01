package com.fomo;

import com.fomo.resources.EventResourse;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fomo.db.*;
import com.fomo.resources.GroupResource;
import com.fomo.resources.PersonResource;
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

public class FomoApp extends Application<Config> {
    private static final ImmutableList<Class<?>> hibernateClasses = ImmutableList.of(
            Person.class,
            Event.class,
            Location.class,
            Group.class,
            Response.class);


    public static void main(String[] args) throws Exception {
        new FomoApp().run(args);
    }

    private final HibernateBundle<Config> hibernateBundle =
            new HibernateBundle<Config>(hibernateClasses, new SessionFactoryFactory()) {
                @Override
                public DataSourceFactory getDataSourceFactory(Config configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "FOMO";
    }

    @Override
    public void initialize(Bootstrap<Config> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
//
//        bootstrap.addBundle(new AssetsBundle());
//        bootstrap.addBundle(new MigrationsBundle<Config>() {
//            @Override
//            public DataSourceFactory getDataSourceFactory(Config configuration) {
//                return configuration.getDataSourceFactory();
//            }
//        });
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(Config configuration, Environment environment) {
        environment.jersey().register(new EventResourse(new EventDao(hibernateBundle.getSessionFactory())));
        environment.jersey().register(new GroupResource(new GroupDao(hibernateBundle.getSessionFactory())));
        environment.jersey().register(new PersonResource(new PersonDao(hibernateBundle.getSessionFactory())));
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        environment.getObjectMapper().setDateFormat(SimpleDateFormat.getDateInstance());
    }
}
