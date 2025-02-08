package com.api.library.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração da classe ModelMapper para ser usada em todo o projeto
 *
 */
@Configuration
public class ModelMapperConfig {
    /**
     * Cria e configura uma instância de ModelMapper
     *
     * @return uma instância de ModelMapper configurada
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}