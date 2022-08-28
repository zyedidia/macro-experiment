
lazy val settings = Seq(
  scalaVersion := "2.12.4",
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

lazy val macros = (project in file("macros")).settings(settings)
lazy val top = (project in file(".")).settings(settings).dependsOn(macros)
