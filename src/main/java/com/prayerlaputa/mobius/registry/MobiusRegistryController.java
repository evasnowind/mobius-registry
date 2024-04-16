package com.prayerlaputa.mobius.registry;

import com.prayerlaputa.mobius.registry.model.InstanceMeta;
import com.prayerlaputa.mobius.registry.service.RegistryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MobiusRegistryController {

    @Resource
    RegistryService registryService;

    @RequestMapping("/reg")
    public InstanceMeta register(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info(" ===> register {} @ {}", service, instance);
        return registryService.register(service, instance);
    }

    @RequestMapping("/unreg")
    public InstanceMeta unregister(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info(" ===> unregister {} @ {}", service, instance);
        return registryService.unregister(service, instance);
    }


    @RequestMapping("/findAll")
    public List<InstanceMeta> findAllInstances(@RequestParam String service) {
        log.info(" ===> findAllInstances {}", service);
        return registryService.getAllInstances(service);
    }

    @RequestMapping("/renew")
    public long renew(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info(" ===> renew {} @ {}", service, instance);
        return registryService.renew(instance, service);
    }

    @RequestMapping("/renews")
    public long renews(@RequestParam String services, @RequestBody InstanceMeta instance) {
        log.info(" ===> renew {} @ {}", services, instance);
        return registryService.renew(instance, services.split(","));
    }

    @RequestMapping("/version")
    public long version(@RequestParam String service) {
        log.info(" ===> version {}", service);
        return registryService.version(service);
    }

    @RequestMapping("/versions")
    public Map<String, Long> versions(@RequestParam String services) {
        log.info(" ===> versions {}", services);
        return registryService.versions(services.split(","));
    }

//    public static void main(String[] args) {
//        InstanceMeta meta = InstanceMeta.http("127.0.0.1", 8081)
//                .addParams(Map.of("env", "dev", "tag", "RED"));
//        System.out.println(JSON.toJSONString(meta));
//    }

}
