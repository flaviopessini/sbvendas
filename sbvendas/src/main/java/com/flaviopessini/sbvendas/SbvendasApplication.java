package com.flaviopessini.sbvendas;

import com.flaviopessini.sbvendas.tests.Animal;
import com.flaviopessini.sbvendas.tests.annotations.Gato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SbvendasApplication extends SpringBootServletInitializer {

    /**
     * Obtém a configuração externalizada através do arquivo 'application.properties'.
     */
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Obtém a configuração através de classes.
     */
    @Autowired
    @Qualifier(value = "applicationVersion")
    private String version;

    /**
     * Injeção de dependência com annotations.
     */
    @Gato
    private Animal animal;

    @Bean(name = "runner")
    public CommandLineRunner runner() {
        return args -> {
            System.out.println("### INICIALIZADO NO PERFIL [" + this.profile + "] - VERSÃO [" + this.version + "] ###");
            System.out.print("Teste de injeção de dependência: ");
            this.animal.interagir();
            System.out.println();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SbvendasApplication.class, args);
    }

}
