package com.patlejch.se1challenge.uc

interface UseCase<In, Out> {
    operator fun invoke(input: In, completion: (Result<Out>) -> Unit)
    fun <T> withResult(
        result: Result<T>,
        completion: (Result<Out>) -> Unit,
        continuation: (T) -> Unit
    ) {
        result.onFailure {
            completion(Result.failure(it))
        }.onSuccess {
            continuation(it)
        }
    }
}

operator fun <T> UseCase<Unit, T>.invoke(completion: (Result<T>) -> Unit) {
    invoke(Unit, completion)
}
