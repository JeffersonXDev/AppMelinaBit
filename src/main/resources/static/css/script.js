document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault(); // Impede o envio padrão do formulário

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');

    errorMessage.textContent = ''; // Limpa mensagens de erro anteriores

    if (!username || !password) {
        errorMessage.textContent = 'Por favor, preencha todos os campos.';
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            // Armazena o token e o papel do usuário
            localStorage.setItem('authToken', data.token);
            localStorage.setItem('userRole', data.userRole); // Armazena o papel do usuário!
            
            // Redireciona para o dashboard
            window.location.href = 'dashboard.html'; 
        } else {
            const errorData = await response.json();
            errorMessage.textContent = errorData.message || 'Erro ao fazer login. Verifique suas credenciais.';
        }
    } catch (error) {
        console.error('Erro de rede ou servidor:', error);
        errorMessage.textContent = 'Não foi possível conectar ao servidor. Tente novamente mais tarde.';
    }
});