package com.fomo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

public class Config extends Configuration {
    @JsonProperty @NotNull
    private String environment = Environment.DEV.name();

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
    
    @NotNull
    private Map<String, Map<String, String>> viewRendererConfiguration = Collections.emptyMap();

    public boolean isDev() {
        return Environment.valueOf(environment).equals(Environment.DEV);
    }

    public boolean isProd() {
        return Environment.valueOf(environment).equals(Environment.DEV);
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
