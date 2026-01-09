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
        // Proteção contra nulos ou vazios
        if (source == null || source.trim().isEmpty()) {
            return null;
        }

        try {
            // CORREÇÃO: Mudado de Long para Integer
            Integer id = Integer.valueOf(source);

            // Agora o tipo casa com o JpaRepository<Apiario, Integer>
            return apiarioRepository.findById(id).orElse(null);

        } catch (NumberFormatException e) {
            System.err.println("Erro de conversão: O ID do Apiário não é um número válido. Valor recebido: " + source);
            return null;
        }
    }
}