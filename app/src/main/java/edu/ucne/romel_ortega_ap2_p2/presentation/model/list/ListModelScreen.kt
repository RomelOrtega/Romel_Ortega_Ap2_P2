package edu.ucne.romel_ortega_ap2_p2.presentation.model.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.romel_ortega_ap2_p2.domain.model.Model

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListModelScreen(
    viewModel: ListModelViewModel = hiltViewModel(),
    onAddClick: () -> Unit,
    onGastoClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadGastos()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Gastos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar gasto")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }

            state.error?.let { error ->
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(state.gastos) { gasto ->
                    GastoItem(
                        gasto = gasto,
                        onClick = { onGastoClick(gasto.gastoId) }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            HorizontalDivider()

            val total = state.gastos.sumOf { it.monto }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Conteo de gastos: ${state.gastos.size}",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Total: $total",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun GastoItem(
    gasto: Model,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${gasto.gastoId}", fontWeight = FontWeight.Bold)
            Text(gasto.suplidor)
            Text(gasto.fecha)
            Text("Monto: ${gasto.monto}")
        }
    }
}