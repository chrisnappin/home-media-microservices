name := """web-front-end"""
organization := "com.nappin.homemedia.web"

version := "0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  filters,
  ws,
  specs2 % Test)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.nappin.homemedia.web.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.nappin.homemedia.web.binders._"
