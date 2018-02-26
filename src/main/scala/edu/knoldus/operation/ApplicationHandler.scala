package edu.knoldus.operation

import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}

object ApplicationHandler {
  val logger = Logger.getLogger(this.getClass)
  val conf = new SparkConf()
    .setMaster("local")
    .setAppName("spark-assignment")
  val sc = new SparkContext(conf)
  val customerFileRdd = sc.textFile("/home/knoldus/Desktop/Assignments/spark-assignment/customerFile.txt")
  val salesFileRdd = sc.textFile("/home/knoldus/Desktop/Assignments/spark-assignment/salesFile.txt")
  val customerTable = customerFileRdd.flatMap(x => x.split('\n')).map(y => y.split('#')).map(z => (z(0), Customer(z(0), z(1), z(2), z(3))))
  val salesTable = salesFileRdd.map(x => {
    val z = x.split('#')
    (z(1), CustomerOrder(z(0).toLong, z(1), z(2).toDouble))
  })

  def runApp: Unit = {
    val joinedRdd = customerTable join salesTable
    val yearlyReport = joinedRdd
      .map(joinedTuple => ((joinedTuple._2._1.city, joinedTuple._2._2.date.getYear + 1900, joinedTuple._1),joinedTuple._2._2.sales))
      .reduceByKey(_ + _)
      .map(deducedTuple => s"${deducedTuple._1._1}#${deducedTuple._1._2}###${deducedTuple._2}")

    val monthlyReport = joinedRdd.
      map(joinedTuple => ((joinedTuple._2._1.city, joinedTuple._2._2.date.getYear + 1900, joinedTuple._2._2.date.getMonth + 1, joinedTuple._1),joinedTuple._2._2.sales))
      .reduceByKey(_ + _)
      .map(y => s"${y._1._1}#${y._1._2}#${y._1._3}##${y._2}")

    val dailyReport = joinedRdd
      .map(joinedTuple => ((joinedTuple._2._1.city, joinedTuple._2._2.date.getYear + 1900, joinedTuple._2._2.date.getMonth + 1, joinedTuple._2._2.date.getDate, joinedTuple._1),joinedTuple._2._2.sales))
      .reduceByKey(_ + _)
      .map(deducedTuple => s"${deducedTuple._1._1}#${deducedTuple._1._2}#${deducedTuple._1._3}#${deducedTuple._1._3}#${deducedTuple._2}")

    yearlyReport union monthlyReport union dailyReport map(x => {
      val city = x.split('#')(0)
      (city, x)
    })

    logger.info(s"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n${yearlyReport.collect.toList}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
    logger.info(s"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n${monthlyReport.collect.toList}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
    logger.info(s"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n${dailyReport.collect.toList}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
  }


}
