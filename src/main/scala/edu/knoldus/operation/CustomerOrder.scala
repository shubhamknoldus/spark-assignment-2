package edu.knoldus.operation
import java.util.Date

case class CustomerOrder(private val timeStamp: Long, customerId: String, sales: Double) {
  val date = new Date(timeStamp * 1000)
}
