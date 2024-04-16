package com.prayerlaputa.mobius.registry;

import com.prayerlaputa.mobius.registry.health.HealthChecker;
import com.prayerlaputa.mobius.registry.health.MobiusHealthChecker;
import com.prayerlaputa.mobius.registry.service.MobiusRegistryService;
import com.prayerlaputa.mobius.registry.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MobiusRegistryConfig {

    @Bean
    public RegistryService registryService() {
        return new MobiusRegistryService();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public HealthChecker healthChecker(@Autowired RegistryService registryService) {
        return new MobiusHealthChecker(registryService);
    }

}
