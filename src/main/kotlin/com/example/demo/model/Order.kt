package com.example.demo.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name="orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String = "",
    val price:Int =0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    val user:User?=null
)
@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String = "",
    val email: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)