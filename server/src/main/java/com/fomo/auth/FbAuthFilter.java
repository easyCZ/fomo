package com.fomo.auth;

import com.fomo.db.Person;
import com.fomo.db.dao.PersonDao;
import com.fomo.resources.EventResourse;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FbAuthFilter implements ContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(FbAuthFilter.class);
    private final Client client;
    private final PersonDao personDao;
    private final SessionFactory sessionFactory;
    private static final String FB_USER_CTX_KEY = "user";
    public final LoadingCache<String, FbUser> userCache = CacheBuilder.newBuilder()
                                                                .expireAfterAccess(100, TimeUnit.MINUTES)
                                                                .build(new CacheLoader<String, FbUser>() {
                                                                    @Override
                                                                    public FbUser load(String key) throws Exception {
                                                                        FbUser user = client.target("https://graph.facebook.com/me?access_token=" + key)
                                                                                            .request()
                                                                                            .get(FbUser.class);
                                                                        return user;
                                                                    }
                                                                });

    public FbAuthFilter(SessionFactory sessionFactory) {
        this.client = ClientBuilder.newClient();
        this.personDao = new PersonDao(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Handle nulls and
        String fbAuth = requestContext.getHeaders().getFirst("fbAuth");
        log.info("FbAugh header: " + fbAuth);
        if (fbAuth != null) {
            try {
                FbUser fbUser = userCache.get(fbAuth);
                log.info("Fb user: " + fbUser);
                Person person = getUser(fbUser);
                requestContext.setProperty(FB_USER_CTX_KEY, person);
                return; // They've successfully authenticated
            } catch (Exception e) {
                log.error("User failed to auth - try again...", e);
            }
        }
        log.info("User failed to login for some reason");
        unauthorized(requestContext);
    }

    private Person getUser(FbUser fbUser) {
        Session session = sessionFactory.openSession();
        Person person = personDao.get(session, fbUser.getId());
        person = person == null ? personDao.create(session, fbUser.toPerson()) : person;
        Hibernate.initialize(person.getResponses());
        session.flush();
        session.close();
        return person;
    }

    private void unauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(ImmutableMap.of("error","Access denied. Login with facebook."))
                        .build());
    }

    public static class FbUserFactory implements Factory<Person> {

        private final ContainerRequestContext context;

        @Inject
        public FbUserFactory(ContainerRequestContext context) {
            this.context = context;
        }

        @Override
        public Person provide() {
            return (Person)context.getProperty(FB_USER_CTX_KEY);
        }

        @Override
        public void dispose(Person t) {}
    }

    public static AbstractBinder USER_BINDER = new AbstractBinder() {
        @Override
        protected void configure() {
            bindFactory(FbUserFactory.class).to(Person.class);
        }
    };


}
