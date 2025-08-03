package com.karan.prometheus_grafana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PrometheusGrafanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrometheusGrafanaApplication.class, args);
	}

}
