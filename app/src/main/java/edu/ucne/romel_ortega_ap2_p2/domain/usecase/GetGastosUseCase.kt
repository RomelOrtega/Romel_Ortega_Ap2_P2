package edu.ucne.romel_ortega_ap2_p2.domain.usecase

import edu.ucne.romel_ortega_ap2_p2.domain.repository.Repository
import javax.inject.Inject

class GetGastosUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getGastos()
}