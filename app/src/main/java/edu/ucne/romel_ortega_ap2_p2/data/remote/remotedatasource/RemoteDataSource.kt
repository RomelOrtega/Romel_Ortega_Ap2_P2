package edu.ucne.romel_ortega_ap2_p2.data.remote.remotedatasource

import retrofit2.HttpException
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.Api
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.GastoResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: Api
) {

    suspend fun getGastos(): Result<List<GastoResponse>> {
        try {
            val response = api.getGastos()
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            return Result.success(response.body()!!)
        } catch (e: HttpException) {
            return Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            return Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun getGastoDetail(id: Int): Result<GastoResponse> {
        try {
            val response = api.getGastoDetail(id)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            return Result.success(response.body()!!)
        } catch (e: HttpException) {
            return Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            return Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun createGasto(gasto: GastoRequest): Result<GastoResponse> {
        try {
            val response = api.createGasto(gasto)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            return Result.success(response.body()!!)
        } catch (e: HttpException) {
            return Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            return Result.failure(Exception("Error desconocido", e))
        }
    }

    suspend fun updateGasto(id: Int, gasto: GastoRequest): Result<GastoResponse> {
        try {
            val response = api.updateGasto(id, gasto)
            if (!response.isSuccessful) {
                return Result.failure(Exception("Error de red ${response.code()}"))
            }
            return Result.success(response.body()!!)
        } catch (e: HttpException) {
            return Result.failure(Exception("Error de servidor", e))
        } catch (e: Exception) {
            return Result.failure(Exception("Error desconocido", e))
        }
    }
}