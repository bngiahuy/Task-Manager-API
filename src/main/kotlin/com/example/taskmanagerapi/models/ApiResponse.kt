package com.example.taskmanagerapi.models

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null
) {
    constructor(success: Boolean, message: String) : this(success, message, null)
    constructor(message: String, payload: T) : this(true, message, payload)
    constructor(payload: T) : this(true, "Success", payload)
}
