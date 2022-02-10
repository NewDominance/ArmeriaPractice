package blog;

import com.linecorp.armeria.common.metric.MeterIdPrefixFunction;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.metric.MetricCollectingService;
import com.linecorp.armeria.server.metric.PrometheusExpositionService;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ArmeriaPrometheusApplication {

    public static void main(String[] args) {
        PrometheusMeterRegistry meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        Server server = Server
                .builder()
                .http(8083)
                .meterRegistry(meterRegistry)
                .annotatedService(new MyAnnotatedService())
                .service("/metrics", PrometheusExpositionService.of(meterRegistry.getPrometheusRegistry()))
                .decorator(MetricCollectingService.builder(MeterIdPrefixFunction.ofDefault("my.http.service"))
                        .newDecorator())
                .tls(new File("certificate.crt"),new File("private.key"),"myPassphrase")
                .build();


        CompletableFuture<Void> future = server.start();
        future.join();
    }
}