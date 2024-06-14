package com.example.emergetestapplication.emerge.domain.mapper

import com.example.emergetestapplication.emerge.data.model.user.User
import com.example.emergetestapplication.emerge.data.model.db.UserEntity

object UserEntityToModelMapper {
    fun UserEntity.toModel(): User =
        User(
            username = this.username,
            password = this.password,
        )

    fun User.toEntity(): UserEntity =
        UserEntity(
            username = this.username,
            password = this.password,
        )
}
