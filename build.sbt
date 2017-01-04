name := "kafka-examples"

version := "1.0"

scalaVersion := "2.12.1"

resolvers += Resolver.jcenterRepo
resolvers += Resolver.mavenLocal

libraryDependencies += "org.apache.kafka" % "kafka_2.10" % "0.10.1.1"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.1.1"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "0.10.1.1"

libraryDependencies += "com.google.firebase" % "firebase-server-sdk" % "3.0.1"
libraryDependencies += "org.apache.avro" % "avro" % "1.8.1"
libraryDependencies += "com.twitter" % "bijection-avro_2.11" % "0.9.4"
