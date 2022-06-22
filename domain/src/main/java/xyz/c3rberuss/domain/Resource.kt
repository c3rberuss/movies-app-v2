package xyz.c3rberuss.domain

sealed class Resource<out T> {
    class Loading<T> : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Failed<T>(val exception: Exception) : Resource<T>()
}