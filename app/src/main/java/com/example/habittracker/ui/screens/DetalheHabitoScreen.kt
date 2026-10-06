package com.example.habittracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.habittracker.ui.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheHabitoScreen(navController: NavController, viewModel: HabitViewModel, habitoId: Int) {
    val habito = viewModel.buscarHabitoPorId(habitoId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Hábito", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        if (habito == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Hábito não encontrado", color = Color.Red)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Nome e status
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (habito.concluidoHoje) Color(0xFFE8F5E9) else Color(0xFFF5F5F5)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = habito.nome,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (habito.concluidoHoje) "✅ Concluído hoje" else "⏳ Pendente hoje",
                            fontSize = 14.sp,
                            color = if (habito.concluidoHoje) Color(0xFF2E7D32) else Color(0xFF6B6B6B),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Descrição
                if (habito.descricao.isNotBlank()) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Descrição", fontSize = 12.sp, color = Color(0xFF6B6B6B), fontWeight = FontWeight.Medium)
                            Text(habito.descricao, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }

                // --- COMPLEXIDADE EXTRA: Informações calculadas e combinadas ---

                // 1. Dias da semana formatados
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Dias de repetição", fontSize = 12.sp, color = Color(0xFF6B6B6B), fontWeight = FontWeight.Medium)
                        Text(
                            text = formatarDiasSemana(habito.diasSemana),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // 2. Taxa de conclusão global (combina dados de todos os hábitos)
                val taxaConclusao = viewModel.calcularTaxaConclusao()
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📊 Estatística Global", fontSize = 12.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.Medium)
                        Text(
                            text = "Taxa de conclusão hoje: ${(taxaConclusao * 100).toInt()}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "${viewModel.habitos.count { it.concluidoHoje }} de ${viewModel.habitos.size} hábitos concluídos",
                            fontSize = 12.sp,
                            color = Color(0xFF6B6B6B),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // 3. Progresso visual (complexidade extra visual)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Progresso do dia", fontSize = 12.sp, color = Color(0xFF6B6B6B), fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { taxaConclusao },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            color = Color(0xFF4C6EF5),
                            trackColor = Color(0xFFE0E0E0)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botão para toggle direto da tela de detalhes
                Button(
                    onClick = { viewModel.toggleHabitoConcluido(habito.id) },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text(
                        if (habito.concluidoHoje) "Desmarcar Conclusão" else "Marcar como Concluído",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Função auxiliar para formatar dias da semana
private fun formatarDiasSemana(dias: Set<Int>): String {
    if (dias.isEmpty()) return "Não definido"
    val nomes = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
    return dias.sorted().mapNotNull { nomes.getOrNull(it) }.joinToString(", ")
}