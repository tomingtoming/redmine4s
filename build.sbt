name := "redmine4s"

version := "0.1"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

libraryDependencies := Seq(
  // Scala
  "com.typesafe.play" %% "play-json" % "2.5.8",
  // Java
  "org.slf4j" % "slf4j-api" % "1.7.21",
  "org.apache.httpcomponents" % "httpclient" % "4.5.2",
  // Java Test
  "ch.qos.logback" % "logback-classic" % "1.1.7" % Test,
  // Scala Test
  "org.scalatest" %% "scalatest" % "3.0.0" % Test
)