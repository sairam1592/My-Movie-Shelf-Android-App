package com.example.emergetestapplication.emerge.domain.mapper

import com.example.emergetestapplication.emerge.data.model.firebase.FbMovieModel
import com.example.emergetestapplication.emerge.data.model.movies.Movie

object MovieMappers {
    private fun fromFbMovieModel(fbMovieModel: FbMovieModel): Movie =
        Movie(
            id = fbMovieModel.id,
            title = fbMovieModel.title,
            overview = fbMovieModel.overview,
            poster_path = fbMovieModel.posterPath,
            release_date = "",
            vote_average = 0.0,
        )

    fun toFbMovieModel(movie: Movie): FbMovieModel =
        FbMovieModel(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.poster_path,
        )

    fun FbMovieModel.toMovie(): Movie =
        Movie(
            id = this.id,
            title = this.title,
            overview = this.overview,
            poster_path = this.posterPath,
            release_date = "",
            vote_average = 0.0,
        )

    fun fromFbModelList(fbMovieModels: List<FbMovieModel>): List<Movie> = fbMovieModels.map { fromFbMovieModel(it) }

    fun toFbModelList(movies: List<Movie>): List<FbMovieModel> = movies.map { toFbMovieModel(it) }
}
