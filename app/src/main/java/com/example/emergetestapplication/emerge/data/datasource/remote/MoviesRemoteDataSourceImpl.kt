package com.example.emergetestapplication.emerge.data.datasource.remote

import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.common.AppConstants.CATEGORY_EMOJI
import com.example.emergetestapplication.emerge.common.AppConstants.CATEGORY_MOVIES
import com.example.emergetestapplication.emerge.common.AppConstants.CATEGORY_TITLE
import com.example.emergetestapplication.emerge.common.AppConstants.FIREBASE_COLLECTION_NAME
import com.example.emergetestapplication.emerge.common.AppConstants.MOVIE_ID
import com.example.emergetestapplication.emerge.common.AppConstants.MOVIE_OVERVIEW
import com.example.emergetestapplication.emerge.common.AppConstants.MOVIE_POSTER_PATH
import com.example.emergetestapplication.emerge.common.AppConstants.MOVIE_TITLE
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbMovieModel
import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import com.example.emergetestapplication.emerge.data.network.APIService
import com.google.firebase.firestore.FieldValue
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
        /**
         * This method is used to search the movies based on the Movie Title.
         */
        override fun searchMovies(query: String): Flow<Result<MovieResponse>> =
            flow {
                emit(Result.success(apiService.searchMovies(query)))
            }.flowOn(Dispatchers.IO)

        /**
         * This method is used to get the user categories from the Firebase Firestore Database Collection.
         */
        override suspend fun getUserCategories(username: String): List<FbCategoryModel>? =
            suspendCoroutine { continuation ->
                fireStore
                    .collection(AppConstants.FIREBASE_COLLECTION_NAME)
                    .document(username)
                    .get()
                    .addOnSuccessListener { document ->
                        val user =
                            document.data?.mapNotNull { entry ->
                                val categoryData =
                                    entry.value as? Map<*, *> ?: return@mapNotNull null
                                val title = categoryData[CATEGORY_TITLE] as? String ?: ""
                                val emoji = categoryData[CATEGORY_EMOJI] as? String ?: ""
                                val moviesList = categoryData[CATEGORY_MOVIES] as? List<Map<*, *>>
                                val movies =
                                    moviesList?.map { movieData ->
                                        FbMovieModel(
                                            id = (movieData[MOVIE_ID] as? Long)?.toInt() ?: 0,
                                            title = movieData[MOVIE_TITLE] as? String ?: "",
                                            overview = movieData[MOVIE_OVERVIEW] as? String ?: "",
                                            posterPath = movieData[MOVIE_POSTER_PATH] as? String ?: "",
                                        )
                                    } ?: emptyList()
                                FbCategoryModel(title = title, emoji = emoji, movies = movies)
                            } ?: emptyList()

                        continuation.resume(user)
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }

        /**
         * This method is used to add the a new Category with 5 movies to the Firebase Firestore Database Collection.
         */
        override suspend fun addCategoryToFirebaseDB(
            username: String,
            categoryName: String,
            category: FbCategoryModel,
        ) = suspendCoroutine { continuation ->
            val categoryMap =
                mapOf(
                    categoryName to
                        mapOf(
                            CATEGORY_TITLE to categoryName,
                            CATEGORY_EMOJI to category.emoji,
                            CATEGORY_MOVIES to
                                category.movies.map {
                                    mapOf(
                                        MOVIE_ID to it.id,
                                        MOVIE_TITLE to it.title,
                                        MOVIE_OVERVIEW to it.overview,
                                        MOVIE_POSTER_PATH to it.posterPath,
                                    )
                                },
                        ),
                )
            fireStore
                .collection(AppConstants.FIREBASE_COLLECTION_NAME)
                .document(username)
                .set(categoryMap, SetOptions.merge())
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        /**
         * This method is used to delete the category from the Firebase Firestore.
         */
        override suspend fun deleteCategory(
            username: String,
            categoryName: String,
        ) = suspendCoroutine { continuation ->
            fireStore
                .collection(AppConstants.FIREBASE_COLLECTION_NAME)
                .document(username)
                .update(categoryName, FieldValue.delete())
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        override suspend fun removeMoviesFromCategory(
            username: String,
            categoryName: String,
            movieIds: List<Int>,
        ): Unit =
            suspendCoroutine { continuation ->
                fireStore
                    .collection(FIREBASE_COLLECTION_NAME)
                    .document(username)
                    .get()
                    .addOnSuccessListener { document ->
                        val categoryData = document.get(categoryName) as? Map<*, *>
                        val moviesList = categoryData?.get(CATEGORY_MOVIES) as? List<Map<*, *>>
                        val updatedMoviesList =
                            moviesList?.filterNot { movieData ->
                                movieIds.contains((movieData[MOVIE_ID] as? Long)?.toInt())
                            }

                        val updatedCategory = categoryData?.toMutableMap()
                        updatedCategory?.set(CATEGORY_MOVIES, updatedMoviesList)

                        fireStore
                            .collection(FIREBASE_COLLECTION_NAME)
                            .document(username)
                            .update(categoryName, updatedCategory)
                            .addOnSuccessListener {
                                continuation.resume(Unit)
                            }.addOnFailureListener { exception ->
                                continuation.resumeWithException(exception)
                            }
                    }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        override suspend fun deleteAccountFromFirebase(username: String): Result<Unit> =
            suspendCoroutine { continuation ->
                fireStore
                    .collection(FIREBASE_COLLECTION_NAME)
                    .document(username)
                    .delete()
                    .addOnSuccessListener {
                        continuation.resume(Result.success(Unit))
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                }
        }
    }
