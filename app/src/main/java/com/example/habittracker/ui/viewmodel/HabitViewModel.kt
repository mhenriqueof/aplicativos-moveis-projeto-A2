package com.example.habittracker.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.habittracker.data.model.Conquista
import com.example.habittracker.data.model.Habito

/**
 * ViewModel compartilhado entre todas as telas.
 * Mantém as listas de hábitos e conquistas em memória.
 * Sobrevive à navegação entre telas (mas não ao fechar o app - isso é Trabalho 3).
 */
class HabitViewModel : ViewModel() {

    // Lista reativa de hábitos - usar mutableStateListOf para Compose recompor automaticamente
    val habitos = mutableStateListOf(
        Habito(1, "Beber 2L de água", setOf(1, 2, 3, 4, 5), descricao = "Manter hidratação durante o dia"),
        Habito(2, "Ler 10 páginas", setOf(1, 2, 3, 4, 5, 6, 0), descricao = "Leitura diária antes de dormir"),
        Habito(3, "Exercitar-se 20min", setOf(1, 3, 5), descricao = "Treino leve em casa"),
        Habito(4, "Meditar 5min", setOf(1, 2, 3, 4, 5, 6, 0), descricao = "Meditação guiada pela manhã")
    )

    // Lista reativa de conquistas
    val conquistas = mutableStateListOf(
        Conquista(1, "3 dias seguidos", "Complete hábitos por 3 dias consecutivos", true),
        Conquista(2, "7 dias seguidos", "Complete hábitos por 7 dias consecutivos", true),
        Conquista(3, "Madrugador", "Complete um hábito antes das 8h", true),
        Conquista(4, "14 dias seguidos", "Complete hábitos por 14 dias consecutivos", false),
        Conquista(5, "30 dias seguidos", "Complete hábitos por 30 dias consecutivos", false),
        Conquista(6, "Perfeição semanal", "Complete todos os hábitos por 7 dias", false)
    )

    // Próximo ID disponível para novos hábitos
    private var proximoHabitoId = 5

    // Próximo ID disponível para novas conquistas
    private var proximoConquistaId = 7

    // --- Funções para Hábitos ---

    fun adicionarHabito(nome: String, diasSemana: Set<Int>, descricao: String) {
        if (nome.isBlank()) return
        habitos.add(
            Habito(
                id = proximoHabitoId++,
                nome = nome,
                diasSemana = diasSemana,
                descricao = descricao
            )
        )
    }

    fun removerHabito(habito: Habito) {
        habitos.remove(habito)
    }

    fun toggleHabitoConcluido(habitoId: Int) {
        val index = habitos.indexOfFirst { it.id == habitoId }
        if (index != -1) {
            val habitoAtual = habitos[index]
            habitos[index] = habitoAtual.copy(concluidoHoje = !habitoAtual.concluidoHoje)
        }
    }

    fun buscarHabitoPorId(id: Int): Habito? {
        return habitos.find { it.id == id }
    }

    // --- Funções para Conquistas ---

    fun toggleConquistaDesbloqueada(conquistaId: Int) {
        val index = conquistas.indexOfFirst { it.id == conquistaId }
        if (index != -1) {
            val conquistaAtual = conquistas[index]
            conquistas[index] = conquistaAtual.copy(desbloqueada = !conquistaAtual.desbloqueada)
        }
    }

    fun buscarConquistaPorId(id: Int): Conquista? {
        return conquistas.find { it.id == id }
    }

    // --- Estatísticas calculadas (complexidade extra para tela de detalhes) ---

    fun calcularTaxaConclusao(): Float {
        if (habitos.isEmpty()) return 0f
        val concluidos = habitos.count { it.concluidoHoje }
        return concluidos.toFloat() / habitos.size.toFloat()
    }

    fun totalConquistasDesbloqueadas(): Int {
        return conquistas.count { it.desbloqueada }
    }
}