package edu.ucne.romel_ortega_ap2_p2.presentation.model.list

import edu.ucne.romel_ortega_ap2_p2.domain.model.Model

data class ListModelUiState(
    val isLoading: Boolean = false,
    val gastos: List<Model> = emptyList(),
    val error: String? = null
)