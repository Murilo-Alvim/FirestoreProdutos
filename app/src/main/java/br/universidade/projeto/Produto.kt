package br.universidade.projeto

// Valores padrão são obrigatórios para o toObject() do Firestore funcionar
data class Produto(
    val nome: String = "",
    val categoria: String = "",
    val preco: Double = 0.0
)
