package com.example.demo.service
import  com.example.demo.model.Order
import com.example.demo.controler.RequestOrder
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.listOrders
import  com.example.demo.service.OrderService
import org.springframework.stereotype.Service

@Service
class SqlOrderService ( val orderRepository: OrderRepository) : OrderService {
    override  fun getOrder(id: Long): Order  {
        return orderRepository.findById(id).orElseThrow { IllegalArgumentException("Order with id $id does not exist") }
    }
    override  fun getAll(): List<Order> {
        return orderRepository.findAll()
    }
    override  fun createdOrder(request: RequestOrder): Order{
        val newOrder = Order(name =request.name, price = request.price)
        return orderRepository.save(newOrder)
    }
    override  fun deleteOrder(id: Long): Order{
        val deleteOrder = orderRepository.findById(id).orElseThrow { IllegalArgumentException("Order with id $id does not exist") }
        orderRepository.deleteById(deleteOrder.id)
        return deleteOrder
    }
    override  fun updateOrder(id: Long, request: RequestOrder): Order {
        if(!orderRepository.existsById(id)){
            throw IllegalArgumentException("Order with id $id does not exist")
        }
        val newOrder = Order(id=id,name=request.name, price = request.price)
        return orderRepository.save(newOrder)
    }
}
