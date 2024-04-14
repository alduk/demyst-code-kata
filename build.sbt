ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

val zioVersion = "2.1-RC1"

lazy val root = (project in file("."))
  .settings(
    name := "test-zio2",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-json" % "0.6.2",
      "dev.zio" %% "zio-http" % "3.0.0-RC6",
      "dev.zio" %% "zio-config" % "4.0.1",
      "dev.zio" %% "zio-config-typesafe" % "4.0.1",
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
      "dev.zio" %% "zio-test-magnolia" % zioVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.scalatestplus" %% "mockito-5-10" % "3.2.18.0" % Test
    )
  )
