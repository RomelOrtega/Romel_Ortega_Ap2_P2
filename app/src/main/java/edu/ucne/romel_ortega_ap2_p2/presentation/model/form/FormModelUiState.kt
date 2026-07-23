package edu.ucne.romel_ortega_ap2_p2.presentation.model.form

data class FormModelUiState(
    val gastoId: Int? = null,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: String = "",
    val monto: String = "",
    val suplidorError: String? = null,
    val montoError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val guardadoExitoso: Boolean = false
) {
    val esEdicion: Boolean get() = gastoId != null
}