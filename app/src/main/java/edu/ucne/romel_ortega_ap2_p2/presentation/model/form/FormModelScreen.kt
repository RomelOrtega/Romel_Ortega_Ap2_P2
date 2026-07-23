package edu.ucne.romel_ortega_ap2_p2.presentation.model.form

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormModelScreen(
    gastoId: Int?,
    onBack: () -> Unit,
    onSaved: () -> Unit,
    viewModel: FormModelViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(gastoId) {
        if (gastoId != null) {
            viewModel.load(gastoId)
        }
    }

    LaunchedEffect(state.guardadoExitoso) {
        if (state.guardadoExitoso) {
            onSaved()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.esEdicion) "Editar Gasto" else "Registrar Gasto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.fecha,
                onValueChange = { viewModel.onEvent(FormModelUiEvent.FechaChanged(it)) },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.suplidor,
                onValueChange = { viewModel.onEvent(FormModelUiEvent.SuplidorChanged(it)) },
                label = { Text("Suplidor") },
                isError = state.suplidorError != null,
                supportingText = {
                    state.suplidorError?.let { Text(it, color = Color.Red) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.ncf,
                onValueChange = { viewModel.onEvent(FormModelUiEvent.NcfChanged(it)) },
                label = { Text("NCF") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.itbis,
                onValueChange = { viewModel.onEvent(FormModelUiEvent.ItbisChanged(it)) },
                label = { Text("ITBIS") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.monto,
                onValueChange = { viewModel.onEvent(FormModelUiEvent.MontoChanged(it)) },
                label = { Text("Monto") },
                isError = state.montoError != null,
                supportingText = {
                    state.montoError?.let { Text(it, color = Color.Red) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            state.error?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text("Error: $error", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onEvent(FormModelUiEvent.Guardar) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                } else {
                    Text("Guardar")
                }
            }
        }
    }
}