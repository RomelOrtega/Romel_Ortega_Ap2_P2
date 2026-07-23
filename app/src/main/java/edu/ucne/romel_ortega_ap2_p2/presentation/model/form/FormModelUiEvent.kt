package edu.ucne.romel_ortega_ap2_p2.presentation.model.form

sealed interface FormModelUiEvent {
    data class FechaChanged(val value: String) : FormModelUiEvent
    data class SuplidorChanged(val value: String) : FormModelUiEvent
    data class NcfChanged(val value: String) : FormModelUiEvent
    data class ItbisChanged(val value: String) : FormModelUiEvent
    data class MontoChanged(val value: String) : FormModelUiEvent
    data object Guardar : FormModelUiEvent
}