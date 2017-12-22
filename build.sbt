name := """up-checker"""
organization := "com.example"

version := "0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  evolutions,
  jdbc,
  guice,
  ws,
  "org.postgresql" % "postgresql" % "42.1.4",
  "com.typesafe.play" %% "anorm" % "2.5.3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)
