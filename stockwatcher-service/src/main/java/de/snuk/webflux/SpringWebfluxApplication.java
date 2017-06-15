package de.snuk.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebfluxApplication {

    public static void main(String[] args) {
	SpringApplication.run(SpringWebfluxApplication.class, args);
    }

    // @Bean
    // RouterFunction<?> routes(StockService service) {
    // return RouterFunctions
    // .route(RequestPredicates.GET("/stocks"),
    // request -> ServerResponse.ok().body(service.all(), Stock.class))
    // .andRoute(RequestPredicates.GET("/stocks/{stockId}"),
    // request ->
    // ServerResponse.ok().body(service.byId(request.pathVariable("stockId")),
    // Stock.class))
    // .andRoute(RequestPredicates.GET("/stocks/{stockId}/events"),
    // request -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
    // .body(service.stream(request.pathVariable("stockId")),
    // StockEvent.class));
    // }
}
