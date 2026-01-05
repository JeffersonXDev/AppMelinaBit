package com.appmelinabit.converter;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.repository.ApiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApiarioConverter implements Converter<String, Apiario> {


    @Autowired
    private ApiarioRepository apiarioRepository;

    @Override
    public Apiario convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        
        try {
            Long id = Long.valueOf(source);

            return apiarioRepository.findById(id).orElse(null);

        } catch (NumberFormatException e) {

            System.err.println("Erro de conversão: O ID do Apiário não é um número válido. Valor recebido: " + source);
            return null;
        }
    }
}