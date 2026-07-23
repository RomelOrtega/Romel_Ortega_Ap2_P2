package edu.ucne.romel_ortega_ap2_p2.presentation.model.list

sealed interface ListModelUiEvent {
    data object Recargar : ListModelUiEvent
}