package com.example.demo.repository
import com.example.demo.model.Order
import com.example.demo.service.OrderService
import org.springframework.stereotype.Repository
import com.example.demo.repository.OrderRepository
import org.springframework.data.jpa.repository.JpaRepository

val listOrders = mutableListOf<Order>(
    Order(id =0, name = "Order 0"),
)
@Repository
//class SqlOrderRepository: OrderRepository {
    interface OrderRepository : JpaRepository<Order, Long> {}
//    override fun getAll(): List<Order> {
//        return listOrders
//    }
//    override fun getById(id: Long): Order? {
//        return listOrders.find{it.id == id}
//    }
//    override fun save(order: Order): Order {
//        listOrders.add(order)
//        return order
//    }
//    override  fun delete(id:Long):Order?{
//        val deleteItem = listOrders.find{it.id == id}
//        listOrders.removeIf{it.id==id}
//        return deleteItem;
//    }
//    override fun upDateOrder(order: Order): Order {
//        listOrders.first{it.id == order.id}.name =order.name
//        return order.id?.let { this.getById(it) }!!
//    }
//}
