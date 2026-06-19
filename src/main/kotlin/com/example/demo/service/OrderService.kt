package com.example.demo.service
import com.example.demo.model.Order
import com.example.demo.controler.RequestOrder

interface OrderService {
    fun getOrder(id: Long): Order
    fun getAll(): List<Order>
    fun createdOrder(request: RequestOrder): Order
    fun deleteOrder(id: Long):Order
    fun updateOrder(id:Long,request: RequestOrder): Order
}
