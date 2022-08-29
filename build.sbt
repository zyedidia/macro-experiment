val chiselVersion = "3.5.4"

lazy val settings = Seq(
  scalaVersion := "2.12.4",
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

lazy val macros = (project in file("macros")).settings(settings)
lazy val top = (project in file(".")).settings(settings).dependsOn(macros)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % chiselVersion cross CrossVersion.full)
libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % chiselVersion,
  "edu.berkeley.cs" %% "chiseltest" % "0.5.1" % "test"
)
