package edu.ucne.romel_ortega_ap2_p2.domain.repository

import kotlinx.coroutines.flow.Flow
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.Resource
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.romel_ortega_ap2_p2.domain.model.Model

interface Repository {
    fun getGastos(): Flow<Resource<List<Model>>>
    fun getGastoDetail(id: Int): Flow<Resource<Model>>
    fun createGasto(gasto: GastoRequest): Flow<Resource<Model>>
    fun updateGasto(id: Int, gasto: GastoRequest): Flow<Resource<Model>>
}