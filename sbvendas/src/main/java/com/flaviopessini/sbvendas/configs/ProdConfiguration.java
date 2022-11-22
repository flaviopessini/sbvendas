package com.flaviopessini.sbvendas.configs;

import com.flaviopessini.sbvendas.annotations.Production;
import org.springframework.context.annotation.Bean;

/**
 * Classe de configurações customizadas para o perfil de ambiente de produção.
 */
@Production
public class ProdConfiguration {

    /**
     * Perfil de produção
     *
     * @return "prod"
     */
    @Bean(name = "applicationProfile")
    public String applicationProfile() {
        return "prod";
    }

    /**
     * Versão da aplicação no perfil de produção.
     */
    @Bean(name = "applicationVersion")
    public String applicationVersion() {
        return "0.1.1";
    }
}
