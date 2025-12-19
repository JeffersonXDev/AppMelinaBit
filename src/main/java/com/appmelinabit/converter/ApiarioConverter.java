package com.appmelinabit.converter;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.repository.ApiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converte o ID (String) de um Apiario (vindo do formulário HTML) para o objeto Apiario.
 * Essencial para o Spring Data JPA realizar o binding correto em campos ManyToOne (th:field="*{apiario}").
 */
@Component
public class ApiarioConverter implements Converter<String, Apiario> {

    // O repositório é necessário para buscar o objeto Apiario pelo ID
    @Autowired
    private ApiarioRepository apiarioRepository;

    @Override
    public Apiario convert(String source) {
        if (source.isEmpty()) {
            return null; // Retorna nulo se o campo de seleção for vazio (opção "Selecione o Apiário")
        }
        
        try {
            Long id = Long.valueOf(source);
            // Busca o Apiario no banco de dados e o retorna.
            return apiarioRepository.findById(id).orElse(null);

        } catch (NumberFormatException e) {
            // Log de erro, mas pode lançar uma exceção de validação
            System.err.println("Erro de conversão: O ID do Apiário não é um número válido. Valor recebido: " + source);
            return null;
        }
    }
}