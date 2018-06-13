name := "Spark-POCS"

version := "0.1"

scalaVersion := "2.11.8"

val sparkVersion = "2.3.0"
val twiiter4jVersion ="4.0.4"

resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies += "org.twitter4j" % "twitter4j-core" % twiiter4jVersion
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % twiiter4jVersion

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion
)