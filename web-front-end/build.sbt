name := """web-front-end"""
organization := "com.nappin.homemedia.web"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.nappin.homemedia.web.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.nappin.homemedia.web.binders._"
