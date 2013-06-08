import de.johoop.jacoco4sbt.JacocoPlugin._
import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object ScalaTraderBuild extends Build {

  val jeroMqVersion = "0.2.0"
  val scalaVersionNo = "2.10.0"
  val jodaTimeVersion = "2.1"
  val googleProtoBufVersion = "2.5.0"
  val typesafeConfigVersion = "1.0.1"
  val slf4jVersion = "1.7.5"

  val distribution = TaskKey[File]("distribution", "Creates a distributable zip file in the target directory.")

  override val settings = super.settings ++ Seq(
    scalaVersion := scalaVersionNo,
    libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
      "com.typesafe" % "config" % typesafeConfigVersion,
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "org.slf4j" % "slf4j-simple" % slf4jVersion
    ),
    organization := "net.sandipan.scalatrader"
  )

  artifact in (Compile, assembly) ~= { art =>
    art.copy(`classifier` = Some("assembly"))
  }

  val customAssemblySettings = assemblySettings ++ addArtifact(artifact in (Compile, assembly), assembly)

  lazy val root = Project(
    id = "scalatrader",
    base = file("."),
    aggregate = Seq(zmq, marketdata, algo, tradecapture, messagebroker, dist),
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
    settings = Project.defaultSettings ++ Seq(
      version := "0.1"
    )
  )

  val algo = Project(
    id = "algo",
    base = file("./algo"),
    dependencies = Seq(common, zmq),
    settings = Project.defaultSettings ++ jacoco.settings ++ Seq(
      version := "0.1"
    )
  )

  val tradecapture = Project(
    id = "tradecapture",
    base = file("./tradecapture"),
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
    settings = Project.defaultSettings ++ Seq(
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

  val dist: Project = Project(
    id = "scalatrader-dist",
    base = file("./scalatrader-dist"),
    settings = Project.defaultSettings ++ customAssemblySettings ++ Seq(
      version := "0.1",

      distribution <<= (AssemblyKeys.assembly, Keys.target, Keys.name, Keys.version, streams) map {
        (a: File, t: File, n: String, v: String, s: TaskStreams) =>

          // Credit/inspiration: https://eknet.org/main/dev/sbt-create-distribution-zip.html

          val distDir = t / (n + "-" + v)
          val zipFile = t / (n + "-" + v + ".zip")
          s.log.info("Creating distribution file: " + zipFile.getName)
          IO.delete(distDir)
          IO.delete(zipFile)

          val bin = distDir / "bin"
          val conf = distDir / "conf"
          IO.createDirectories(Seq(bin, conf))

          def copyFiles(targetDir: File, destDir: File) {
            for (f <- targetDir.listFiles) {
              s.log.info("Including: " + f.getName)
              IO.copyFile(f, destDir / f.getName)
            }
          }

          // Copy scripts and config
          val scripts: File = dist.base / "src" / "main" / "scripts"
          val config: File = dist.base / "src" / "main" / "conf"
          copyFiles(scripts, bin)
          copyFiles(config, conf)

          // Copy assembly
          s.log.info("Including: " + a.getName)
          IO.copyFile(a, bin / a.getName)

          def entries(f: File): List[File] = f :: (if (f.isDirectory) IO.listFiles(f).toList.flatMap(entries(_)) else Nil)

          // Create the zip file
          IO.zip(entries(distDir).map(d => (d, d.getAbsolutePath.substring(distDir.getParent.length + 1))), zipFile)

          s.log.info("Done creating distribution file! Located here: " + zipFile.getAbsoluteFile)

          zipFile
      }
    ),
    dependencies = Seq(algo, marketdata, tradecapture, messagebroker)
  )

}