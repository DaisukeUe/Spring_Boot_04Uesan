package com.example.demo.defaultdata

import com.example.demo.model.Order
import com.example.demo.model.User
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Defaultdata(val userRepository: UserRepository,val orderRepository: OrderRepository) : CommandLineRunner{
    override fun run(vararg args: String) {
        val newUser = User(
            name = "testUser1",
            email="sample@jp"
        )
    val saveUser= userRepository.save(newUser)

        val order1 = Order(
            name = "testOrder1",
            price = 1000,
            user =saveUser
        )
        val order2 = Order(
            name = "testOrder2",
            price = 2000,
            user = saveUser
        )
        orderRepository.save(order1)
        orderRepository.save(order2)

        println("初期値用,${orderRepository.findAll()}")
    }
}
