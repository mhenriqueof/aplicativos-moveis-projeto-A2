package com.example.habittracker.data.model

/**
 * Data class representando uma conquista/recompensa.
 * Usada na tela de recompensas e na tela de detalhes da conquista.
 */
data class Conquista(
    val id: Int,
    val titulo: String,
    val descricao: String,
    var desbloqueada: Boolean = false,
    val icone: String = "🏅"
)