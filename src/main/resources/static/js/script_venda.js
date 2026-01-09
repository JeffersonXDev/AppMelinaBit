document.addEventListener('DOMContentLoaded', function() {

    // --- FUNÇÃO DE CÁLCULO CORE ---
    function calcularTotal(qtdId, unitarioId, freteId, totalId) {
        const qtdEl = document.getElementById(qtdId);
        const unitEl = document.getElementById(unitarioId);
        const freteEl = document.getElementById(freteId);
        const totalEl = document.getElementById(totalId);

        // Só executa se todos os elementos daquela página existirem
        if (qtdEl && unitEl && freteEl && totalEl) {
            const atualizar = () => {
                const Q = parseFloat(qtdEl.value) || 0;
                const VU = parseFloat(unitEl.value) || 0;
                const F = parseFloat(freteEl.value) || 0;
                const resultado = (Q * VU) + F;
                totalEl.value = resultado.toFixed(2);
            };

            // Escuta mudanças em qualquer um dos 3 campos
            [qtdEl, unitEl, freteEl].forEach(el => el.addEventListener('input', atualizar));

            // Cálculo inicial
            atualizar();
        }
    }

    // --- EXECUÇÃO PARA PÁGINA DE VENDA ---
    // IDs baseados no seu HTML de Venda
    calcularTotal('quantidade', 'valorVendaUnitario', 'valorFreteUnitario', 'valorVenda');

    // --- EXECUÇÃO PARA PÁGINA DE COMPRA ---
    // IDs baseados no seu HTML de Compra (vinculado ao HistoricoCompraFornecedor)
    calcularTotal('quantidadeComprado', 'valorCustoUnitario', 'valorFreteUnitario', 'precoPago');

});