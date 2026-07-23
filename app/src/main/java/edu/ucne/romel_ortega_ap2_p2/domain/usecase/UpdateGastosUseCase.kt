package edu.ucne.romel_ortega_ap2_p2.domain.usecase

import edu.ucne.romel_ortega_ap2_p2.domain.repository.Repository
import edu.ucne.romel_ortega_ap2_p2.data.remote.dto.GastoRequest
import javax.inject.Inject

class UpdateGastosUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id: Int, gasto: GastoRequest) = repository.updateGasto(id, gasto)
}