// --- PARTE 1: LOGIN ---
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        // ... seu código de fetch (mantido igual)
    });
}

// --- PARTE 2: UTILITÁRIOS (UF) ---
function preencherUF() {
    const estadoCampo = document.getElementById('estado');
    const ufCampo = document.getElementById('uf');
    if (!estadoCampo || !ufCampo) return;

    // Normaliza o texto: remove acentos, espaços extras e deixa em minúsculo
    const estadoInput = estadoCampo.value
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/ç/g, "c")
        .trim()
        .toLowerCase();

    const estadosUf = {
        "acre": "AC", "alagoas": "AL", "amapa": "AP", "amazonas": "AM",
        "bahia": "BA", "ceara": "CE", "distrito federal": "DF", "espirito santo": "ES",
        "goias": "GO", "maranhao": "MA", "mato grosso": "MT", "mato grosso do sul": "MS",
        "minas gerais": "MG", "para": "PA", "paraiba": "PB", "parana": "PR",
        "pernambuco": "PE", "piaui": "PI", "rio de janeiro": "RJ", "rio grande do norte": "RN",
        "rio grande do sul": "RS", "rondonia": "RO", "roraima": "RR", "santa catarina": "SC",
        "sao paulo": "SP", "sergipe": "SE", "tocantins": "TO"
    };

    ufCampo.value = estadosUf[estadoInput] || "";
}

function toggleResponsavel() {
    const selectTerra = document.getElementById('terraTerceiros');
    const nomeInput = document.getElementById('nomeResponsavel');
    const telInput = document.getElementById('telefoneResponsavel');

    const grupoNome = document.getElementById('grupoResponsavel');
    const grupoTelefone = document.getElementById('grupoTelefone');

    // Se o valor for "false" (Não está em terras de terceiros)
    if (selectTerra.value === "false") {
        // Esconde os campos
        grupoNome.style.display = 'none';
        grupoTelefone.style.display = 'none';

        // REMOVE a obrigatoriedade para o navegador não travar
        nomeInput.removeAttribute('required');
        telInput.removeAttribute('required');

        // Limpa os valores para não enviar lixo ao banco
        nomeInput.value = "";
        telInput.value = "";
    } else {
        // Se for "true" (Sim), mostra e torna obrigatório novamente
        grupoNome.style.display = 'block';
        grupoTelefone.style.display = 'block';
        nomeInput.setAttribute('required', 'required');
        telInput.setAttribute('required', 'required');
    }
}

// Executa a função assim que a página carrega para ajustar o estado inicial
document.addEventListener('DOMContentLoaded', toggleResponsavel);

// --- PARTE 3: CÁLCULOS (Protegido com IF para não travar outras páginas) ---
function calcularTotal() {
    const inputQtd = document.getElementById('quantidade');
    const inputUnitario = document.getElementById('valorUnitario');
    const inputFrete = document.getElementById('valorFrete');
    const inputTotalVisual = document.getElementById('valorTotalVisual');
    const inputTotalReal = document.getElementById('valorTotalReal');

    // Só calcula se todos os campos existirem na página atual
    if (inputQtd && inputUnitario && inputFrete) {
        const qtd = parseFloat(inputQtd.value) || 0;
        const unit = parseFloat(inputUnitario.value) || 0;
        const frete = parseFloat(inputFrete.value) || 0;
        const total = (qtd * unit) + frete;

        if (inputTotalVisual) inputTotalVisual.value = total.toFixed(2);
        if (inputTotalReal) inputTotalReal.value = total.toFixed(2);
    }
}

// --- PARTE 4: LISTENERS (Monitoramento de digitação) ---
// Usamos um listener global para evitar erros de "null" ao trocar de página
document.addEventListener('input', (event) => {
    const idsMonitorados = ['quantidade', 'valorUnitario', 'valorFrete'];
    if (idsMonitorados.includes(event.target.id)) {
        calcularTotal();
    }
});

/// --- PARTE 5: PRODUTOS (Identifica o grupo e salva em MAIÚSCULO) ---
function atualizarNomeProduto(select) {
    const campoNome = document.getElementById('nomeProdutoOculto');
    if (!campoNome) return;

    // 1. Pega a opção que o usuário clicou
    const opcaoSelecionada = select.options[select.selectedIndex];

    // 2. Pega o label do grupo pai (optgroup)
    let nomeDoGrupo = (opcaoSelecionada.parentNode.label || "");

    if (select.value !== "") {
        // 3. Padronização total para o Banco de Dados e Dashboard
        if (nomeDoGrupo === "Mel") {
            campoNome.value = "Mel";
        } else if (nomeDoGrupo === "Pólen" || nomeDoGrupo === "Polen") {
            campoNome.value = "Polen";
        } else if (nomeDoGrupo === "Cera") {
            campoNome.value = "Cera";
        } else if (nomeDoGrupo === "Própolis" || nomeDoGrupo === "Propolis") {
            campoNome.value = "Propolis";
        } else {
            // Caso você crie um grupo novo no futuro, ele pega o label direto
            campoNome.value = nomeDoGrupo;
        }
    } else {
        campoNome.value = "";
    }

    console.log("Nome do Produto (Dashboard): " + campoNome.value);
    console.log("Unidade/Peso (Estoque): " + select.value);
}