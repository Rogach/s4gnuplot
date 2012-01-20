//name := "default"

//version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  //"org.apache.mina" % "mina-core" % "2.0.4",
  //"org.eclipse.jetty" % "jetty-server" % "8.1.0.RC1"
)

seq(Revolver.settings: _*)

seq(jotSettings: _*)