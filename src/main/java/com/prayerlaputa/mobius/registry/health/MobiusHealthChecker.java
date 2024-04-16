package com.prayerlaputa.mobius.registry.health;

import com.prayerlaputa.mobius.registry.model.InstanceMeta;
import com.prayerlaputa.mobius.registry.service.MobiusRegistryService;
import com.prayerlaputa.mobius.registry.service.RegistryService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Slf4j
public class MobiusHealthChecker implements HealthChecker {

    RegistryService registryService;

    public MobiusHealthChecker(RegistryService registryService) {
        this.registryService = registryService;
    }

    final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    long timeout = 20_000;

    @Override
    public void start() {
        executor.scheduleWithFixedDelay(
                () -> {
                    log.info(" ===> Health checker running...");
                    long now = System.currentTimeMillis();
                    MobiusRegistryService.TIMESTAMPS.keySet().stream().forEach(serviceAndInst -> {
                        long timestamp = MobiusRegistryService.TIMESTAMPS.get(serviceAndInst);
                        if (now - timestamp > timeout) {
                            log.info(" ===> Health checker: {} is down", serviceAndInst);
                            int index = serviceAndInst.indexOf("@");
                            String service = serviceAndInst.substring(0, index);
                            String url = serviceAndInst.substring(index + 1);
                            InstanceMeta instance = InstanceMeta.from(url);
                            registryService.unregister(service, instance);
                            MobiusRegistryService.TIMESTAMPS.remove(serviceAndInst);
                        }
                    });

                },
                10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        executor.shutdown();
    }
}
