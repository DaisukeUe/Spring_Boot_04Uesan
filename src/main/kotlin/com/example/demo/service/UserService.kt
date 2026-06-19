package com.example.demo.service

import com.example.demo.controler.RequestUser
import com.example.demo.model.User

interface UserService {
    fun getUsers(): List<User>
    fun getUser(id:Long):User
    fun updateUser(id: Long,request: RequestUser): User
    fun deleteUser(id:Long):User
    fun createUser(request: RequestUser): User
}
