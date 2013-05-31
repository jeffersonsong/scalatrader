import de.johoop.jacoco4sbt._
import de.johoop.jacoco4sbt.JacocoPlugin._
import java.io.{InputStreamReader, BufferedReader, BufferedInputStream}
import sbt._
import Keys._

object ScalaZmqBuild extends Build {

  val jeroMqVersion = "0.2.0"
  val scalaVersionNo = "2.10.0"
  val jodaTimeVersion = "2.1"
  val googleProtoBufVersion = "2.5.0"
  val typesafeConfigVersion = "1.0.1"
  val slf4jVersion = "1.7.5"

  override val settings = super.settings ++ Seq(
    scalaVersion := scalaVersionNo,
    libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
      "com.typesafe" % "config" % typesafeConfigVersion,
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "org.slf4j" % "slf4j-simple" % slf4jVersion
    ),
    organizationName := "net.sandipan"
  )

  lazy val root = Project(
    id = "scalazmq-root",
    base = file("."),
    aggregate = Seq(zmq, marketdata, algo, tradereport, messagebroker),
    settings = Project.defaultSettings ++ jacoco.settings
  )

  val zmq = Project(
    id = "zmq",
    base = file("./zmq"),
    settings = Project.defaultSettings ++ Seq(
      libraryDependencies ++= Seq("org.jeromq" % "jeromq" % jeroMqVersion),
      generateProtoTask
    ),
    dependencies = Seq(common)
  )

  val marketdata = Project(
    id = "marketdata",
    base = file("./marketdata"),
    dependencies = Seq(common, zmq)
  )

  val algo = Project(
    id = "algo",
    base = file("./algo"),
    dependencies = Seq(common, zmq)
  )

  val tradereport = Project(
    id = "tradereport",
    base = file("./tradereport"),
    dependencies = Seq(common, zmq)
  )

  val messagebroker = Project(
    id = "messagebroker",
    base = file("./messagebroker"),
    dependencies = Seq(common, zmq)
  )

  val common = Project(
    id = "common",
    base = file("./common"),
    settings = Project.defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "com.google.protobuf" % "protobuf-java" % googleProtoBufVersion,
        "org.joda" % "joda-convert" % "1.2",
        "joda-time" % "joda-time" % jodaTimeVersion
      )
    )
  )

  /* Begone! Evil hackery lies here! */
  val generateProto = TaskKey[Unit]("generate-protos", "Executes generate_protos.bat")
  val generateProtoTask = generateProto := {
    val cmd = java.lang.Runtime.getRuntime.exec("generate_protos.bat")
    cmd.waitFor()

    val stream = cmd.getInputStream
    val buf = new BufferedReader(new InputStreamReader(stream))
    var str: String = null
    do {
      str = buf.readLine()
      if (str != null) println(str)
    } while (str != null)

  }

}