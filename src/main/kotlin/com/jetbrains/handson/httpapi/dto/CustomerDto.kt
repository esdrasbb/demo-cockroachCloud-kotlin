package com.jetbrains.handson.httpapi.dto

import kotlinx.serialization.Serializable

@Serializable
data class CustomerDto (
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String
)