val scala3Version = "3.1.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Poker",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic"  %% "scalactic" % "3.2.11",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    libraryDependencies += "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0",
    libraryDependencies += ( "net.codingwell" %% "scala-guice" % "5.0.2" ).cross( CrossVersion.for3Use2_13 ),
    libraryDependencies += ("com.typesafe.play" %% "play-json" % "2.8.2").cross( CrossVersion.for3Use2_13 ),
    libraryDependencies += ("org.scala-lang.modules" %% "scala-xml" % "2.0.1")
    )
  
jacocoReportSettings := JacocoReportSettings(
  "Jacoco Coverage Report",
  None,
  JacocoThresholds(),
  Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML), // note XML formatter
  "utf-8")