package com.fomo.auth;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class FbAuthFilter implements ContainerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(FbAuthFilter.class);
    private final Client client;
    private static final String FB_USER_CTX_KEY = "user";

    public FbAuthFilter() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authCookie = requestContext.getCookies().get("fbAuth").getValue();
        Response response = client.target("https://graph.facebook.com/me?access_token=" + authCookie)
                                .request().get();
        if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            log.info("User failed to login for some reason");
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Access denied. Login with facebook.")
                            .build());
            return;
        }
        requestContext.setProperty(FB_USER_CTX_KEY, response.readEntity(FbUser.class));
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
