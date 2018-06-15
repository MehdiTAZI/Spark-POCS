import Spark.Batch.RDD.TweetsCleaner
import Spark.Streaming.DStream.Twitter.TwitterStreamRunner
import Spark.Utils.StringProcessing

object Main {

  def main(args: Array[String]): Unit = {

    //collectTweetsFromTwitter()
    cleanAndStoreTweets()

  }


  private def collectTweetsFromTwitter(): Unit ={

    val appName = "spark-streaming-twitter-ingest"

    val keywordList = List("big data", "bigdata", "big_data", "hadoop", "spark", "hive", "impala", "hdfs", "yarn", "sentry", "hbase",
      "sqoop", "kafka", "flume", "solr", "ambari", "elastic search", "elastic_search", "hue", "cloudera", "hortonworks", "mapreduce",
      "nifi", "zookeeper", "sqoop2", "map reduce", "mapreduce", "oozie", "cassandra", "mongodb", "neo4j", "redis", "couchbase", "nosql",
      "datascience", "machinelearning", "machine learning", "data science", "confluent.io", "streamsets", "redis", "spark2",
      "cloudera_manager", "cloudera manager", "cloudera navigator", "cloudera_navigator", "clouderanavigator", "ranger")

    val frequency = 60

    TwitterStreamRunner.run(appName, StringProcessing.stringsToWholeWordHashTags(keywordList), frequency)

  }

  private  def cleanAndStoreTweets(): Unit ={

    val appName = "spark-cleaner-filter"

    TweetsCleaner.run(appName)

  }

}
