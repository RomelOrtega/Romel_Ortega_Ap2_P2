package edu.ucne.romel_ortega_ap2_p2.data.repository

import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.Resource
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.romel_ortega_ap2_p2.data.remote.remotedatasource.RemoteDataSource
import edu.ucne.romel_ortega_ap2_p2.domain.model.Model
import edu.ucne.romel_ortega_ap2_p2.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override fun getGastos(): Flow<Resource<List<Model>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGastos()
        response.onSuccess { gastos ->
            emit(Resource.Success(gastos.map { it.toDomain() }))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun getGastoDetail(id: Int): Flow<Resource<Model>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGastoDetail(id)
        response.onSuccess { gasto ->
            emit(Resource.Success(gasto.toDomain()))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun createGasto(gasto: GastoRequest): Flow<Resource<Model>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.createGasto(gasto)
        response.onSuccess { creado ->
            emit(Resource.Success(creado.toDomain()))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }

    override fun updateGasto(id: Int, gasto: GastoRequest): Flow<Resource<Model>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.updateGasto(id, gasto)
        response.onSuccess { actualizado ->
            emit(Resource.Success(actualizado.toDomain()))
        }.onFailure {
            emit(Resource.Error(it.message ?: "Error desconocido"))
        }
    }
}