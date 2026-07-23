package edu.ucne.romel_ortega_ap2_p2.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import java.util.UUID

sealed class Screen : NavKey {
    @Serializable
    data object ModelList : Screen()

    @Serializable
    data class ModelForm(
        val gastoId: Int? = null,
        val instanceId: String = UUID.randomUUID().toString()
    ) : Screen()
}
