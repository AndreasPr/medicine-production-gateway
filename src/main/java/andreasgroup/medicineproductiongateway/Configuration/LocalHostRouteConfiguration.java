package andreasgroup.medicineproductiongateway.Configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created on 26/Nov/2020 to microservices-medicine-production
 */
@Profile("!local-discovery")
@Configuration
public class LocalHostRouteConfiguration {

    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/api/v1/medicine*", "/api/v1/medicine/*", "/api/v1/medicineUpc/*")
                            .uri("http://localhost:8080"))
                .route(r->r.path("/api/v1/customers/**")
                        .uri("http://localhost:8081"))
                .route(r->r.path("/api/v1/medicine/*/inventory")
                        .uri("http://localhost:8082"))
                .build();
    }
}
