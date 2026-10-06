package com.example.habittracker.data.model

/**
 * Data class representando um hábito.
 * Usada na lista principal e na tela de detalhes.
 */
data class Habito(
    val id: Int,
    val nome: String,
    val diasSemana: Set<Int> = emptySet(), // 0=Dom, 1=Seg, ... 6=Sab
    var concluidoHoje: Boolean = false,
    val descricao: String = ""
)