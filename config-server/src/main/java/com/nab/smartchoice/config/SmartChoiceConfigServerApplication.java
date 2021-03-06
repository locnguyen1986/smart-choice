package com.nab.smartchoice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class SmartChoiceConfigServerApplication {

	private final Environment env;

	private static final Logger log = LoggerFactory.getLogger(SmartChoiceConfigServerApplication.class);

	public SmartChoiceConfigServerApplication(Environment env) {
		this.env = env;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SmartChoiceConfigServerApplication.class);
		Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\t{}://localhost:{}\n\t" +
						"External: \t{}://{}:{}\n\t" +
						"Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				protocol,
				env.getProperty("server.port"),
				protocol,
				hostAddress,
				env.getProperty("server.port"),
				env.getActiveProfiles());

		String applicationDesc = env.getProperty("application.description");
		log.info("\n----------------------------------------------------------\n\t" +
						"Application Description: \t{}\n----------------------------------------------------------",
				applicationDesc);
	}

}
