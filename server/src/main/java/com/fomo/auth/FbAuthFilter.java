package com.fomo.auth;

import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FbAuthFilter implements ContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(FbAuthFilter.class);
    private final Client client;
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

    public FbAuthFilter() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Handle nulls and
        String fbAuth = requestContext.getHeaders().getFirst("fbAuth");
        if (fbAuth != null) {
            try {
                FbUser fbUser = userCache.get(fbAuth);
                requestContext.setProperty(FB_USER_CTX_KEY, fbUser);
                return; // They've successfully authenticated
            } catch (Exception e) {
                log.error("User failed to auth - try again...");
            }
        }
        log.info("User failed to login for some reason");
        unauthorized(requestContext);
    }

    private void unauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity(ImmutableMap.of("error","Access denied. Login with facebook."))
                        .build());
    }

    public static class FbUserFactory implements Factory<FbUser> {

        private final ContainerRequestContext context;

        @Inject
        public FbUserFactory(ContainerRequestContext context) {
            this.context = context;
        }

        @Override
        public FbUser provide() {
            return (FbUser)context.getProperty(FB_USER_CTX_KEY);
        }

        @Override
        public void dispose(FbUser t) {}
    }

    public static AbstractBinder USER_BINDER = new AbstractBinder() {
        @Override
        protected void configure() {
            bindFactory(FbUserFactory.class).to(FbUser.class);
        }
    };


}
