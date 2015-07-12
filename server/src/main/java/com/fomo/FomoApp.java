package com.fomo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fomo.auth.FbAuthFilter;
import com.fomo.db.*;
import com.fomo.db.dao.EventDao;
import com.fomo.db.dao.GroupDao;
import com.fomo.db.dao.PersonDao;
import com.fomo.resources.EventResourse;
import com.fomo.resources.GroupResource;
import com.fomo.resources.PersonResource;
import com.google.common.collect.ImmutableList;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.text.SimpleDateFormat;
import java.util.EnumSet;

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

    final HibernateBundle<Config> hibernateBundle =
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
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html", "assets"));
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
        environment.jersey().register(new FbAuthFilter());
        environment.jersey().register(FbAuthFilter.USER_BINDER);
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        environment.getObjectMapper().setDateFormat(SimpleDateFormat.getDateInstance());

        if (configuration.getEnvironment().equals(com.fomo.Environment.DEV)) {
            // TODO: Only allow CORS on local dev - we should have a better way of doing this. Move it into a class somewhere
            final FilterRegistration.Dynamic cors =
                    environment.servlets().addFilter("CORS", CrossOriginFilter.class);

            // Configure CORS parameters
            cors.setInitParameter("allowedOrigins", "*");
            cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,Cookie");
            cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
            cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        }
    }
}
