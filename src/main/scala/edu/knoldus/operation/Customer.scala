package edu.knoldus.operation

case class Customer(id: String,
                    name: String,
                    address: String,
                    zip: String) {
  val city = address.split(' ').last

}
