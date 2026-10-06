package com.example.habittracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.habittracker.ui.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheConquistaScreen(navController: NavController, viewModel: HabitViewModel, conquistaId: Int) {
    val conquista = viewModel.buscarConquistaPorId(conquistaId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Conquista", fontWeight = FontWeight.Bold) },
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
        if (conquista == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Conquista não encontrada", color = Color.Red)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Ícone grande
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(if (conquista.desbloqueada) Color(0xFFFFD43B) else Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (conquista.desbloqueada) "🏅" else "🔒",
                        fontSize = 48.sp
                    )
                }

                Text(
                    text = conquista.titulo,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = conquista.descricao,
                    fontSize = 16.sp,
                    color = Color(0xFF6B6B6B),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (conquista.desbloqueada) Color(0xFFE8F5E9) else Color(0xFFF5F5F5)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (conquista.desbloqueada) "✅ DESBLOQUEADA" else "🔒 BLOQUEADA",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (conquista.desbloqueada) Color(0xFF2E7D32) else Color(0xFF6B6B6B)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { viewModel.toggleConquistaDesbloqueada(conquista.id) },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text(
                        if (conquista.desbloqueada) "Marcar como Bloqueada" else "Desbloquear Conquista",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}