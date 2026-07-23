package edu.ucne.romel_ortega_ap2_p2.data.remote.dto

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String) : Resource<T>(null, message)
    class Loading<T> : Resource<T>()
}