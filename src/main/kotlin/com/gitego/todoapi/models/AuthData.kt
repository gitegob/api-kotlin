package com.gitego.todoapi.models

import com.gitego.todoapi.entities.User

data class AuthData(var accessToken: String, var user: User)
