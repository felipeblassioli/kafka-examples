package kafka.playground

import java.util.Properties

import org.apache.kafka.common.serialization._
import org.apache.kafka.streams._
import org.apache.kafka.streams.kstream.{KStream, KStreamBuilder, KTable}
import org.apache.kafka.streams.KeyValue
//import org.apache.kafka.connect.json.JsonDeserializer;
//import org.apache.kafka.connect.json.JsonSerializer;

//object KeyValueImplicits {
//
//  implicit def Tuple2ToKeyValue[K, V](tuple: (K, V)): KeyValue[K, V] = new KeyValue(tuple._1, tuple._2)
//
//}

object Main extends App {
  println("Running 3-way join example. Topics: attrA, attrB, attrC, joinResult")

  val streamingConfig = {
    val settings = new Properties
    settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "map-function-scala-example")
    settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    settings.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181")
    // Specify default (de)serializers for record keys and for record values.
    settings.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray.getClass.getName)
    settings.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    settings
  }

  val builder: KStreamBuilder = new KStreamBuilder()

  val stringSerde: Serde[String] = Serdes.String()
  val rowsA: KTable[String, String] = builder.table(stringSerde, stringSerde, "attrA", "attrAStore")
  val rowsB: KTable[String, String] = builder.table(stringSerde, stringSerde, "attrB", "attrBStore")
  val rowsC: KTable[String, String] = builder.table(stringSerde, stringSerde, "attrC", "attrCStore")

  import KeyValueImplicits._
  val join: KTable[String, String] = rowsA.leftJoin(rowsB, (attrA: String, attrB: String) => attrA + "," + attrB)
  join.to(stringSerde, stringSerde, "joinAB")

  val streams: KafkaStreams = new KafkaStreams(builder, streamingConfig)
  streams.start()
}
