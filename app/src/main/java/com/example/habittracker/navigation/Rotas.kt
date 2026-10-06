package com.example.habittracker.navigation

/**
 * Objeto que centraliza todas as rotas do app.
 * Usar constantes evita erros de digitação nas navegações.
 */
object Rotas {
    const val HOME = "home"
    const val NOVO_HABITO = "novo_habito"
    const val RECOMPENSAS = "recompensas"
    const val DETALHE_HABITO = "detalhe_habito/{habitoId}"
    const val DETALHE_CONQUISTA = "detalhe_conquista/{conquistaId}"
    const val PERFIL = "perfil"
    const val CONFIGURACOES = "configuracoes"

    // Função auxiliar para montar a rota com o ID dinâmico
    fun detalheHabito(habitoId: Int) = "detalhe_habito/$habitoId"
    fun detalheConquista(conquistaId: Int) = "detalhe_conquista/$conquistaId"
}
