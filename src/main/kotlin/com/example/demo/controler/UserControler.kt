package com.example.demo.controler

import com.example.demo.model.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class RequestUser(val name:String,val email:String);

@RestController
class UserController(
    val userService: UserService,
    service: UserService,
    userService1: UserService,
    service1: UserService
){
    @GetMapping("/users")
    fun getUsers():List<User>{
        return userService.getUsers();
    }
    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id:Long,@RequestBody request: RequestUser):User{
        return userService.updateUser(id,request)
    }
    @PostMapping("/users")
    fun createUser(@RequestBody request:RequestUser):User{
        return userService.createUser(request);
    }
    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id:Long):User{
        return userService.getUser(id)
    }
    @DeleteMapping("/users/{id}")
    fun deleteUserById(@PathVariable id:Long):User{
        return userService.deleteUser(id)
    }
}