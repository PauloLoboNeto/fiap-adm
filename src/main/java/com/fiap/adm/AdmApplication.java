package com.fiap.adm;

import com.fiap.adm.application.web.configuration.ListenerOfSecrets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdmApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AdmApplication.class);
		app.addListeners(new ListenerOfSecrets());
        app.run(args);
    }
}
