package com.example.demo

import com.example.demo.model.Order
import com.example.demo.controler.OrderControler
import com.example.demo.controler.RequestOrder
import com.example.demo.controler.RequestUser
import com.example.demo.controler.UserController
import com.example.demo.model.User
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.OrderService
import com.example.demo.service.SqlOrderService
import com.example.demo.service.UserService
import org.mockito.Mockito.`when`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MediaType
import org.springframework.http.MediaType as Media
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
//import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import java.util.Optional
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.web.servlet.post
import com.fasterxml.jackson.databind.ObjectMapper


@WebMvcTest(OrderControler::class)
@AutoConfigureRestTestClient
class DemoApplicationTests(@Autowired val restTestClient: RestTestClient) {

	@MockitoBean
	lateinit var orderService: OrderService

	@Autowired
	lateinit var mockMvc: MockMvc

	val testOrder = listOf(Order(id=1,name="test1", price=10000))
	@Test
	fun controllerGetTest() {
		`when`(orderService.getAll()).thenReturn(testOrder)
		mockMvc.get("/orders") {}
			.andExpect {
				status { isOk() }
			content{
				jsonPath("$[0].id" ){value(1)}
				jsonPath("$[0].name" ){value("test1")
				jsonPath("$[0].price"){value(10000)}}
			}}
	}
	@Test
	fun controllerGet() {
		`when`(orderService.getOrder(1))
			.thenReturn(testOrder[0])
		mockMvc.get("/orders/1") {}
			.andExpect {
				status { isOk() }
				content {
					jsonPath("$.id") { value(1) }
					jsonPath("$.name") { value("test1")
					jsonPath("$.price") { value(10000)}}
				}
			}
	}
}
@ExtendWith(MockitoExtension::class)
class OderServiceTest{
	@Mock
	lateinit var orderRepository: OrderRepository;

	@InjectMocks
	lateinit var orderService: SqlOrderService;



	@Test
	fun serviceGet() {
		val testOrder = Order(id=1,name="test1", price=10000)
		`when`(orderRepository.findById(1)).thenReturn(Optional.of(testOrder))
		val actual =orderService.getOrder(1)
		assertThat(actual.name).isEqualTo(testOrder.name)
		assertThat(actual.id).isEqualTo(testOrder.id);
		assertThat(actual.price).isEqualTo(testOrder.price);
		println(orderService)
	}
	@Test
	fun serviceCreate(){
		val request = RequestOrder(
            "新テスト",
            price = 1000
        )
		`when`(orderRepository.save(any()))
			.thenAnswer { invocation -> invocation.getArgument<Order>(0) }

		val createOrder = orderService.createdOrder(request)
		assertThat(createOrder).isNotNull
		assertThat(createOrder.name).isEqualTo("新テスト")
		assertThat(createOrder.price).isEqualTo(1000)
		verify(orderRepository).save(any())
		println(createOrder)
	}
}

@DataJpaTest()
class OrderRepositoryTest{

	@Autowired
	lateinit var orderRepository : OrderRepository

	@Autowired
	lateinit var userRepository: UserRepository

	@BeforeEach
	fun setUp(){
		orderRepository.deleteAll()
		userRepository.deleteAll()
		val argUser = userRepository.save(User(name = "testUser", email = "example@jp"))
		orderRepository.save(Order(name = "test1", price = 1000,user=argUser))
	}
	@Test
	fun repositoryGetAll(){
		val orders = orderRepository.findAll()
		assertThat(orders).hasSize(1)
		assertThat(orders[0].name).isEqualTo("test1")
	}
	@Test
	fun repositorySave(){
		val testUser = userRepository.findByIdOrNull(1)
		val newOrder = Order(name="test2", price = 2000,user=testUser)
		val saveOrder = orderRepository.save(newOrder)

		assertThat(saveOrder.id).isGreaterThan(1.toLong())
		assertThat(saveOrder.name).isEqualTo("test2")
		assertThat(saveOrder.price).isEqualTo(2000)
		val allOrder = orderRepository.findAll()
		assertThat(allOrder).hasSize(2)
		println(allOrder)
	}
}

@WebMvcTest(UserController::class)
@AutoConfigureRestTestClient
class UserControllerTest(@Autowired val restTestClient: RestTestClient){

	@MockitoBean
	lateinit var userService: UserService

	@Autowired
	lateinit var mockMvc: MockMvc


	val objectMapper = com.fasterxml.jackson.databind.ObjectMapper()

	val listUsers = listOf(User(id = 1,name = "testUser", email = "example@jp"), User(id=2,name = "test2", email = "example2"))

	@Test
	fun userControllerGetAll(){
		`when`(userService.getUsers()).thenReturn(listUsers)
			mockMvc.get("/users"){}
				.andExpect {
					status{isOk()}
					content{
						jsonPath("$[0].id") { value(1) }
						jsonPath("$[0].name") { value("testUser") }
						jsonPath("$[0].email"){value("example@jp")}
						jsonPath("$[1].id"){value(2)}
						jsonPath("$[1].name"){value("test2")}
						jsonPath("$[1].email"){value("example2")}
					}
				}
	}

	val request= RequestUser(name = "createUser", email = "create@jp")
	val saveUser = User(name = "createUser", email = "create@jp")
	@Test
	fun userControllerCreate() {
		`when`(userService.createUser(request)).thenReturn(saveUser)
		mockMvc.post("/users"){
			contentType=Media.APPLICATION_JSON
			content =objectMapper.writeValueAsString(request)
		}
			.andExpect {
				status { isOk() }
				content {
					jsonPath("$.id") { value(0) }
					jsonPath("$.name") { value("createUser") }
					jsonPath("$.email") { value("create@jp") }
				}
			}
	}
}

