// URL do seu endpoint REST do Spring Boot
const API_URL = '/api/usuarios/admin';

document.addEventListener('DOMContentLoaded', () => {
    const usersTableBody = document.querySelector('#usersTable tbody');

    // Função para buscar os usuários do backend e preencher a tabela
    const fetchUsers = async () => {
        try {
            const response = await fetch(API_URL);
            if (!response.ok) {
                // Lida com erros de resposta HTTP, como 403 (Forbidden)
                throw new Error('Falha ao carregar usuários. Verifique as permissões.');
            }
            const usuarios = await response.json();
            renderUsers(usuarios); // Chama a função para renderizar a tabela
        } catch (error) {
            console.error('Erro:', error);
            // Mostra uma mensagem de erro na página
            usersTableBody.innerHTML = `<tr><td colspan="5">Erro ao carregar os dados: ${error.message}</td></tr>`;
        }
    };

    // Função para renderizar os dados na tabela
    const renderUsers = (usuarios) => {
        // Limpa o corpo da tabela antes de adicionar novas linhas
        usersTableBody.innerHTML = ''; 

        if (usuarios.length === 0) {
            usersTableBody.innerHTML = '<tr><td colspan="5">Nenhum usuário encontrado.</td></tr>';
            return;
        }

        usuarios.forEach(usuario => {
            const row = document.createElement('tr');
            
            // Usa o campo 'statusConta' para determinar a classe e o texto do status
            const statusClass = usuario.statusConta === 'BLOQUEADA' ? 'status-bloqueada' : 'status-ativa';
            const statusText = usuario.statusConta === 'BLOQUEADA' ? 'BLOQUEADA' : 'ATIVA';
            const blockButtonText = usuario.statusConta === 'BLOQUEADA' ? 'Ativar' : 'Bloquear';

            row.innerHTML = `
                <td>${usuario.id}</td>
                <td>${usuario.nome}</td>
                <td>${usuario.email}</td>
                <td><span class="${statusClass}">${statusText}</span></td>
                <td class="action-buttons">
                    <button class="btn-block" data-id="${usuario.id}" data-status="${usuario.statusConta}">${blockButtonText}</button>
                    <button class="btn-delete" data-id="${usuario.id}">Excluir</button>
                </td>
            `;

            usersTableBody.appendChild(row);
        });

        // Adiciona os event listeners aos botões depois que a tabela é renderizada
        setupButtonListeners();
    };

    // Função para configurar os eventos de clique nos botões
    const setupButtonListeners = () => {
        // Evento para o botão de bloqueio
        document.querySelectorAll('.btn-block').forEach(button => {
            button.addEventListener('click', (event) => {
                const userId = event.target.dataset.id;
                const currentStatus = event.target.dataset.status;
                
                alterarStatusUsuario(userId, currentStatus);
            });
        });

        // Evento para o botão de exclusão
        document.querySelectorAll('.btn-delete').forEach(button => {
            button.addEventListener('click', (event) => {
                const userId = event.target.dataset.id;
                
                excluirUsuario(userId);
            });
        });
    };
    
    // --- Funções de Requisição para o Backend ---

    // Função para alterar o status do usuário
    const alterarStatusUsuario = async (id, statusAtual) => {
        const novoStatus = statusAtual === 'ATIVA' ? 'BLOQUEADA' : 'ATIVA';
        
        try {
            const response = await fetch(`/api/usuarios/admin/${id}/status`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ status: novoStatus })
            });

            if (!response.ok) {
                throw new Error('Falha ao alterar o status do usuário.');
            }

            await fetchUsers(); // Recarrega a tabela para ver a mudança

        } catch (error) {
            console.error('Erro:', error);
            alert('Não foi possível alterar o status do usuário.');
        }
    };

    // Função para excluir um usuário
    const excluirUsuario = async (id) => {
        const confirmacao = confirm('Tem certeza de que deseja excluir este usuário? Esta ação não pode ser desfeita.');

        if (confirmacao) {
            try {
                const response = await fetch(`/api/usuarios/admin/${id}`, {
                    method: 'DELETE'
                });

                if (!response.ok) {
                    throw new Error('Falha ao excluir o usuário.');
                }

                await fetchUsers(); // Recarrega a tabela para ver a mudança

            } catch (error) {
                console.error('Erro:', error);
                alert('Não foi possível excluir o usuário.');
            }
        }
    };

    // Chama a função para carregar os usuários quando a página é carregada
    fetchUsers();
});