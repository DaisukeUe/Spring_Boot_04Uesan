package com.example.demo.controler

import com.example.demo.service.OrderService
import org.apache.coyote.Request
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import com.example.demo.model.Order
//data class Order(val id: Long, var name: String)
data class RequestOrder(var name: String, var price: Int)
@RestController
class OrderControler(val orderService: OrderService) {
    @GetMapping("/orders/{id}")
    fun getOrder(@PathVariable("id") id: Long): Order {
     return orderService.getOrder(id)
    }
    @GetMapping("/orders")
    fun getAll(): List<Order> {
        return orderService.getAll()
    }
    @PostMapping("/orders")
    fun postOrder(@RequestBody request: RequestOrder): Order {
        return orderService.createdOrder(request)
    }
    @DeleteMapping("/orders/{id}")
    fun deleteOrder(@PathVariable("id") id:Long):Order{
        return orderService.deleteOrder(id)
    }
    @PutMapping("/orders/{id}")
     fun updateOrder(@PathVariable("id") id:Long, @RequestBody request: RequestOrder): Order {
         return orderService.updateOrder(id,request)
     }
}
