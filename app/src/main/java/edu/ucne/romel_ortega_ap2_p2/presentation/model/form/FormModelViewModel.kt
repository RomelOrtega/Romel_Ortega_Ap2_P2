package edu.ucne.romel_ortega_ap2_p2.presentation.model.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.Resource
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.romel_ortega_ap2_p2.domain.usecase.CreateGastosUseCase
import edu.ucne.romel_ortega_ap2_p2.domain.usecase.GetGastosDetailUseCase
import edu.ucne.romel_ortega_ap2_p2.domain.usecase.UpdateGastosUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@HiltViewModel
class FormModelViewModel @Inject constructor(
    private val getGastoDetailUseCase: GetGastosDetailUseCase,
    private val createGastoUseCase: CreateGastosUseCase,
    private val updateGastoUseCase: UpdateGastosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        FormModelUiState(fecha = LocalDateTime.now().toString().substring(0, 19))
    )
    val state = _state.asStateFlow()

    fun load(id: Int) {
        _state.update { it.copy(gastoId = id) }
        viewModelScope.launch {
            getGastoDetailUseCase(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val gasto = result.data
                        _state.update {
                            it.copy(
                                isLoading = false,
                                fecha = gasto?.fecha ?: "",
                                suplidor = gasto?.suplidor ?: "",
                                ncf = gasto?.ncf ?: "",
                                itbis = gasto?.itbis?.toString() ?: "",
                                monto = gasto?.monto?.toString() ?: ""
                            )
                        }
                    }
                    is Resource.Error -> _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }

    fun onEvent(event: FormModelUiEvent) {
        when (event) {
            is FormModelUiEvent.FechaChanged ->
                _state.update { it.copy(fecha = event.value) }

            is FormModelUiEvent.SuplidorChanged ->
                _state.update { it.copy(suplidor = event.value, suplidorError = null) }

            is FormModelUiEvent.NcfChanged ->
                _state.update { it.copy(ncf = event.value) }

            is FormModelUiEvent.ItbisChanged ->
                _state.update { it.copy(itbis = event.value) }

            is FormModelUiEvent.MontoChanged ->
                _state.update { it.copy(monto = event.value, montoError = null) }

            FormModelUiEvent.Guardar -> guardar()
        }
    }

    private fun validar(): Boolean {
        val current = _state.value
        val suplidorError = if (current.suplidor.isBlank()) "El suplidor es obligatorio" else null
        val montoDouble = current.monto.toDoubleOrNull()
        val montoError = when {
            current.monto.isBlank() -> "El monto es obligatorio"
            montoDouble == null -> "El monto no es válido"
            montoDouble <= 0 -> "El monto debe ser mayor a 0"
            else -> null
        }

        _state.update { it.copy(suplidorError = suplidorError, montoError = montoError) }

        return suplidorError == null && montoError == null
    }

    private fun guardar() {
        if (!validar()) return

        val current = _state.value
        val request = GastoRequest(
            fecha = current.fecha,
            suplidor = current.suplidor.trim(),
            ncf = current.ncf.trim(),
            itbis = current.itbis.toDoubleOrNull() ?: 0.0,
            monto = current.monto.toDouble()
        )

        viewModelScope.launch {
            val flow = if (current.esEdicion) {
                updateGastoUseCase(current.gastoId!!, request)
            } else {
                createGastoUseCase(request)
            }

            flow.collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update {
                        it.copy(isLoading = false, guardadoExitoso = true)
                    }
                    is Resource.Error -> _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }
}