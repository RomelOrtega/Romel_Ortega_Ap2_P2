package edu.ucne.romel_ortega_ap2_p2.data.remote.dto

import edu.ucne.romel_ortega_ap2_p2.domain.model.Model

data class GastoResponse(
    val gastoId: Int,
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
) {
    fun toDomain() = Model(
        gastoId = gastoId,
        fecha = fecha,
        suplidor = suplidor,
        ncf = ncf,
        itbis = itbis,
        monto = monto
    )
}