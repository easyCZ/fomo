package com.fomo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.Arrays;

public class FbAuthFilter implements ContainerResponseFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FbAuthFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        LOGGER.info(Arrays.toString(requestContext.getHeaders().entrySet().toArray()));
        responseContext.getHeaders().add("X-Powered-By", "Jersey :-)");
    }
}
