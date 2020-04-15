package com.hyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class GateWayApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
    
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
       return builder.routes()
        .route(p -> p
            .path("/get")
            .filters(f -> f.addRequestHeader("Hello", "World"))
            .uri("http://httpbin.org:80"))
        .build();
    }
    
    //使用hystrix的处理  http://localhost:8080/get
    //@Bean
    public RouteLocator routesHystrix(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
            .route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri(httpUri))
            .route(p -> p
                .host("*.hystrix.com")
                .filters(f -> f
                    .hystrix(config -> config
                        .setName("mycmd")
                        .setFallbackUri("forward:/fallback")))
                .uri(httpUri))
            .build();
    }
    
    // curl --dump-header - --header 'Host: www.hystrix.com' http://localhost:8080/delay/3
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

    
    //@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/baidu")
                        .uri("http://baidu.com:80/")
                )
               .route("websocket_route", r -> r.path("/apitopic1/**")
                .uri("ws://127.0.0.1:6605"))
                .route(r -> r.path("/userapi3/**")
                        .filters(f -> f.addResponseHeader("X-AnotherHeader", "testapi3"))

                        .uri("lb://user-service/")
                )
                .build();
    }
//https://blog.csdn.net/russle/java/article/details/80962066

}
