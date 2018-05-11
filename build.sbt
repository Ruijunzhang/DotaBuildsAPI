name := "Dota-Builds-API"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += ws
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

// https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.21.0.1"

// https://mvnrepository.com/artifact/org.apache.commons/commons-compress
libraryDependencies += "org.apache.commons" % "commons-compress" % "1.16.1"

// https://mvnrepository.com/artifact/com.skadistats/clarity
libraryDependencies += "com.skadistats" % "clarity" % "2.2"

libraryDependencies += "joda-time" % "joda-time" % "2.9.9"

// https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.20.0"

libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0" % Test
testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")
