package Spark.Batch.Rdd

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{Matchers, WordSpec}

class SparkSpec extends WordSpec with Matchers with SparkContextSetup {
  "My analytics" should {
 //   "calculate the right thing" in withSparkContext { (sparkContext) =>
 //     val data = Seq(1,2,3,4)
 //     val rdd = sparkContext.parallelize(data)
 //     val total = rdd.map(...).filter(...).map(...).reduce(_ + _)

     //xz total shouldBe 1000
    }
}

trait SparkContextSetup {
  def withSparkContext(testMethod: (SparkContext) => Any) {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("Spark test")
    val sparkContext = new SparkContext(conf)
    try {
      testMethod(sparkContext)
    }
    finally sparkContext.stop()
  }
}