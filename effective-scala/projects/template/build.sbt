ThisBuild / scalaVersion := "3.2.1"

libraryDependencies ++= List(
  "com.lihaoyi" %% "fansi" % "0.4.0",
  "org.scalameta" %% "munit-scalacheck" % "0.7.29" % Test,
)

testFrameworks += TestFramework("munit.Framework")
