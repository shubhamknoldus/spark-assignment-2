package edu.knoldus.application

import org.apache.log4j.Logger
import org.apache.spark.{SparkConf, SparkContext}

object ApplicationObject extends App {
  val logger = Logger.getLogger(this.getClass)
  val conf = new SparkConf()
    .setMaster("local")
    .setAppName("CountingSheep")
  val sc = new SparkContext(conf)
  val list = (1 to 50).toList
  val list2 = (51 to 100).toList
  logger.info(s"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n${list} ${list2}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
  val rdd = sc.parallelize(list)
  val rdd1 = sc.parallelize(list2)
  Thread.sleep(5000)
  logger.info(s"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n${rdd.map(x => x + 2).collect} ${rdd1.collect}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
  Thread.sleep(2000)
  sc.stop()
}
