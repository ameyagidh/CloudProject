package com.mywebapp.application;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for managing metrics.
 */
@Configuration
public class MetricRegistry {

    private MeterRegistry meterRegistry;

    /**
     * Constructor to initialize MetricRegistry with MeterRegistry.
     *
     * @param meterRegistry MeterRegistry instance
     */
    public MetricRegistry(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * Method to get the singleton instance of MeterRegistry with common tags configured.
     *
     * @return MeterRegistry instance
     */
    public MeterRegistry getInstance(){
        this.meterRegistry.config().commonTags("application","webservice");
        return this.meterRegistry;
    }
}
