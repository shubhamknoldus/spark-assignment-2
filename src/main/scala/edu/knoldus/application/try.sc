case class Person(private val name: String, id: Int) {
  val newName = name.toUpperCase

  override def toString = s"$newName \t $id"
}

object Person {
  def unapply(arg: (String, Int)): Option[Person] = {
    Some(Person(arg._1, arg._2))
  }
}
("shubham", 23) match{
case Person(Person(x, y)) => println("person")
case _ => println("none")}

Person("shubham", 34)
import java.util.Date

val x: Long = 1470045600
val date = new Date(x * 1000)


val y = 1470045600 * 1000
val date2 = new Date(y)

import edu.knoldus.operation.CustomerOrder
val obj = CustomerOrder(1470045600, 36, 10292)