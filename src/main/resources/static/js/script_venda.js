document.addEventListener('DOMContentLoaded', function() {

    // --- Seletores Comuns e Específicos ---
    
    // Campo Comum: Quantidade
    const quantidadeInput = document.getElementById('quantidade');

    // Campos de Venda (Saída)
    const valorUnitarioVendaInput = document.getElementById('valorVendaUnitario');
    const valorFreteVendaInput = document.getElementById('valorFreteUnitario'); // ID usado no seu HTML de Venda
    const valorTotalVendaInput = document.getElementById('valorTotalVenda');

    // Campos de Compra (Entrada) - IDs presumidos
    const valorUnitarioCustoInput = document.getElementById('valorCustoUnitario'); 
    const valorFreteCompraInput = document.getElementById('valorFrete'); // ID sugerido para o HTML de Compra
    const valorTotalCustoInput = document.getElementById('valorTotalCusto'); 

    // --- Função de Cálculo Genérica ---
    function calcularValorTotal(quantidade, valorUnitario, valorFrete) {
        const Q = parseFloat(quantidade) || 0;
        const VU = parseFloat(valorUnitario) || 0;
        const F = parseFloat(valorFrete) || 0;
        
        const subtotal = Q * VU;
        return (subtotal + F).toFixed(2);
    }
    
    // --- Lógica para Venda (Se os campos de Venda existirem) ---
    if (valorUnitarioVendaInput && valorFreteVendaInput && valorTotalVendaInput) {
        
        function calcularVenda() {
            const quantidade = quantidadeInput.value;
            const valorUnitario = valorUnitarioVendaInput.value;
            const valorFrete = valorFreteVendaInput.value;
            
            valorTotalVendaInput.value = calcularValorTotal(quantidade, valorUnitario, valorFrete);
        }

        // Adiciona listeners para os campos de Venda
        quantidadeInput.addEventListener('input', calcularVenda);
        valorUnitarioVendaInput.addEventListener('input', calcularVenda);
        valorFreteVendaInput.addEventListener('input', calcularVenda);
        
        // Executa o cálculo inicial
        calcularVenda();
    }

    // --- Lógica para Compra (Se os campos de Compra existirem) ---
    if (valorUnitarioCustoInput && valorFreteCompraInput && valorTotalCustoInput) {
        
        function calcularCompra() {
            const quantidade = quantidadeInput.value;
            const valorUnitario = valorUnitarioCustoInput.value;
            const valorFrete = valorFreteCompraInput.value;
            
            valorTotalCustoInput.value = calcularValorTotal(quantidade, valorUnitario, valorFrete);
        }

        // Adiciona listeners para os campos de Compra
        quantidadeInput.addEventListener('input', calcularCompra);
        valorUnitarioCustoInput.addEventListener('input', calcularCompra);
        valorFreteCompraInput.addEventListener('input', calcularCompra);
        
        // Executa o cálculo inicial
        calcularCompra();
    }
});