function mostrarOpcoesInimigos() {
    var selectControle = document.getElementById("controleInimigos");
    var blocoEscolha = document.getElementById("blocoEscolhaInimigos");
    var selectReal = document.getElementById("inimigosNaturais");

    if (selectControle.value === "Sim") {
        blocoEscolha.style.display = "block";
    } else {
        blocoEscolha.style.display = "none";
        selectReal.value = "Nenhum"; // Reseta o valor caso o usuário mude para 'Não'
    }
}