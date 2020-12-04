package andreasgroup.medicineproductiongateway.Configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutesConfiguration {

    @Bean
    public RouteLocator loadBalandedRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r-> r.path("/api/v1/medicine*", "/api/v1/medicine/*", "/api/v1/medicineUpc/*")
                            .uri("lb://medicine-service")
                            .id("medicine-service"))
                .route(r->r.path("/api/v1/customers/**")
                            .uri("lb://medicine-order-service")
                            .id("medicine-order-service"))
                .route(r -> r.path("/api/v1/medicine/*/inventory")
                            .filters(f->f.circuitBreaker(c->c.setName("inventoryCB")
                                        .setFallbackUri("forward:/medicine-inventory-failover")
                                        .setRouteId("medicine-inventory-failover")))
                            .uri("lb://medicine-inventory-service")
                            .id("medicine-inventory-service"))
                .route(r->r.path("/medicine-inventory-failover/**")
                            .uri("lb://medicine-inventory-failover")
                            .id("medicine-inventory-failover"))
                .build();
    }
}
