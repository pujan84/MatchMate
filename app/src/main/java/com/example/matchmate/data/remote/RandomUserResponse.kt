package com.example.matchmate.data.remote

data class RandomUserResponse(
    val results: List<UserDto>
)

data class UserDto(
    val gender: String,
    val email: String,
    val phone: String,
    val name: NameDto,
    val picture: PictureDto,
    val location: LocationDto,
    val login: LoginDto,
    val dob: DobDto
)

data class NameDto(
    val title: String,
    val first: String,
    val last: String
)

data class PictureDto(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class LocationDto(
    val city: String,
    val state: String,
    val country: String
)

data class LoginDto(
    val uuid: String
)

data class DobDto(
    val age: Int
)