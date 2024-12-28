ThisBuild / scalaVersion := "3.6.2"
ThisBuild / organization := "com.example"

lazy val root = (project in file("."))
  .settings(
    name := "typeclass-exercises",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.12.0",
      "org.typelevel" %% "discipline-core" % "1.7.0" % Test,
      "org.typelevel" %% "discipline-scalatest" % "2.3.0" % Test,
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )
