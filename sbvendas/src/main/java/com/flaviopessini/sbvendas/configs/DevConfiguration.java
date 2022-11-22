package com.flaviopessini.sbvendas.configs;

import com.flaviopessini.sbvendas.annotations.Development;
import org.springframework.context.annotation.Bean;

/**
 * Classe de configurações customizadas para o perfil de ambiente de desenvolvimento.
 */
@Development
public class DevConfiguration {

    /**
     * Perfil de desenvolvimento
     *
     * @return "dev"
     */
    @Bean(name = "applicationProfile")
    public String applicationProfile() {
        return "dev";
    }

    /**
     * Versão da aplicação no perfil de desenvolvimento.
     */
    @Bean(name = "applicationVersion")
    public String applicationVersion() {
        return "0.0.1";
    }
}
