package com.example.emergetestapplication.emerge.data.datasource.remote

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbUserModel
import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import com.example.emergetestapplication.emerge.data.network.APIService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MoviesRemoteDataSourceImpl
    @Inject
    constructor(
        private val apiService: APIService,
        private val fireStore: FirebaseFirestore,
    ) : MoviesRemoteDataSource {
        override fun getPopularMovies(): Flow<Result<MovieResponse>> =
            flow {
                emit(Result.success(apiService.getPopularMovies()))
            }.flowOn(Dispatchers.IO)

        override fun searchMovies(query: String): Flow<Result<MovieResponse>> =
            flow {
                emit(Result.success(apiService.searchMovies(query)))
            }.flowOn(Dispatchers.IO)

        override suspend fun getUserCategories(username: String): FbUserModel? =
            suspendCoroutine { continuation ->
                fireStore
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnSuccessListener { document ->
                        val user = document.toObject(FbUserModel::class.java)
                        continuation.resume(user)
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }

        override suspend fun addCategoryToFirebaseDB(
            username: String,
            categoryName: String,
            category: FbCategoryModel,
        ) = suspendCoroutine { continuation ->
            val categoryMap = mapOf("categories.$categoryName" to category)
            fireStore
                .collection("users")
                .document(username)
                .set(categoryMap, SetOptions.merge())
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
    }
