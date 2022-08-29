package com.dh.gatewayservice;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Component
public class CountFilter extends AbstractGatewayFilterFactory<CountFilter.Config> {

    public static Logger log = Logger.getLogger(CountFilter.class.getName());
    private static AtomicInteger count = new AtomicInteger();

    public CountFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            log.info("la cantidad de peticiones al gateway es: " + count.incrementAndGet());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {}));

        };

    }

    public static class Config {}

}
