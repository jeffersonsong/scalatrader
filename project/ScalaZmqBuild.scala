import de.johoop.jacoco4sbt.JacocoPlugin._
import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

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
    publishTo := Some(Resolver.file("file",  new File( "/home/srazzaque/software/sonatype-work/nexus/storage/sandipan" )) ),
    organization := "net.sandipan.scalazmq",
    publishMavenStyle := true
  )

  artifact in (Compile, assembly) ~= { art =>
    art.copy(`classifier` = Some("assembly"))
  }

  val customAssemblySettings = assemblySettings ++ addArtifact(artifact in (Compile, assembly), assembly)

  lazy val root = Project(
    id = "scalazmq",
    base = file("."),
    aggregate = Seq(zmq, marketdata, algo, tradereport, messagebroker),
    settings = Project.defaultSettings ++ Seq(
      version := "0.1"
    )
  )

  val zmq = Project(
    id = "zmq",
    base = file("./zmq"),
    settings = Project.defaultSettings ++ Seq(
      libraryDependencies ++= Seq("org.jeromq" % "jeromq" % jeroMqVersion),
      version := "0.1"
    ),
    dependencies = Seq(common)
  )

  val marketdata = Project(
    id = "marketdata",
    base = file("./marketdata"),
    dependencies = Seq(common, zmq),
    settings = Project.defaultSettings ++ customAssemblySettings ++ Seq(
      version := "0.1"
    )
  )

  val algo = Project(
    id = "algo",
    base = file("./algo"),
    dependencies = Seq(common, zmq),
    settings = Project.defaultSettings ++ jacoco.settings ++ customAssemblySettings ++ Seq(
      version := "0.1"
    )
  )

  val tradereport = Project(
    id = "tradereport",
    base = file("./tradereport"),
    dependencies = Seq(common, zmq),
    settings = Project.defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "org.mongodb" %% "casbah" % "2.6.1"
      ),
      version := "0.1"
    ) ++ assemblySettings
  )

  val messagebroker = Project(
    id = "messagebroker",
    base = file("./messagebroker"),
    dependencies = Seq(common, zmq),
    settings = Project.defaultSettings ++ customAssemblySettings ++ Seq(
      version := "0.1"
    )
  )

  val common = Project(
    id = "common",
    base = file("./common"),
    settings = Project.defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "com.google.protobuf" % "protobuf-java" % googleProtoBufVersion,
        "org.joda" % "joda-convert" % "1.2",
        "joda-time" % "joda-time" % jodaTimeVersion
      ),
      version := "0.1"
    )
  )

}