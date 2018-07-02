name := "Spark-POCS"

version := "0.1"

scalaVersion := "2.11.8"


val scalaTestVersion = "3.0.5"
val sparkVersion = "2.3.0"
val twitter4jVersion ="4.0.4"
//val mockitoVersion = "1.8.5"

resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/"
)

libraryDependencies += "org.twitter4j" % "twitter4j-core" % twitter4jVersion
libraryDependencies += "org.twitter4j" % "twitter4j-stream" % twitter4jVersion

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion
)


libraryDependencies += "org.scalactic" %% "scalactic" % scalaTestVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
//libraryDependencies += "org.mockito" % "mockito-core" % mockitoVersion  % Test