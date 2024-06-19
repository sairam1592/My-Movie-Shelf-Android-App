package com.example.emergetestapplication.emerge.data.datasource.remote

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbMovieModel
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
        override fun searchMovies(query: String): Flow<Result<MovieResponse>> =
            flow {
                emit(Result.success(apiService.searchMovies(query)))
            }.flowOn(Dispatchers.IO)

        override suspend fun getUserCategories(username: String): List<FbCategoryModel>? =
            suspendCoroutine { continuation ->
                fireStore
                    .collection("users")
                    .document(username)
                    .get()
                    .addOnSuccessListener { document ->
                        val user =
                            document.data?.mapNotNull { entry ->
                                val categoryData =
                                    entry.value as? Map<*, *> ?: return@mapNotNull null
                                val title = categoryData["title"] as? String ?: ""
                                val emoji = categoryData["emoji"] as? String ?: ""
                                val moviesList = categoryData["movies"] as? List<Map<*, *>>
                                val movies =
                                    moviesList?.map { movieData ->
                                        FbMovieModel(
                                            id = (movieData["id"] as? Long)?.toInt() ?: 0,
                                            title = movieData["title"] as? String ?: "",
                                            overview = movieData["overview"] as? String ?: "",
                                            posterPath = movieData["posterPath"] as? String ?: "",
                                        )
                                    } ?: emptyList()
                                FbCategoryModel(title = title, emoji = emoji, movies = movies)
                            } ?: emptyList()

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
            val categoryMap =
                mapOf(
                    categoryName to
                        mapOf(
                            "title" to categoryName,
                            "emoji" to category.emoji,
                            "movies" to
                                category.movies.map {
                                    mapOf(
                                        "id" to it.id,
                                        "title" to it.title,
                                        "overview" to it.overview,
                                        "posterPath" to it.posterPath,
                                    )
                                },
                        ),
                )
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
