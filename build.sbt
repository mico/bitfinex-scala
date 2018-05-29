name := "bitfinex-websocket-client"

organization := "com.github.mico"

version := "0.1"

scalaVersion := "2.12.4"

mainClass in (Compile, run) := Some("com.mico.bitfinex.Main")

val akkaVersion      = "2.5.11"
val akkaHTTPVersion  = "10.1.0-RC2"
val scalaTestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "ch.qos.logback"             % "logback-classic"      % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging"       % "3.8.0",
  "com.typesafe.akka"          %% "akka-actor"          % akkaVersion,
  "com.typesafe.akka"          %% "akka-stream"         % akkaVersion,
  "com.typesafe.akka"          %% "akka-http"           % akkaHTTPVersion,
  "io.argonaut"                %% "argonaut"            % "6.2.1",
  "org.bouncycastle"           % "bcpkix-jdk15on"       % "1.59",
  "com.github.pureconfig"      %% "pureconfig"          % "0.9.0",
  "org.scalactic"              %% "scalactic"           % scalaTestVersion % Test,
  "com.typesafe.akka"          %% "akka-testkit"        % akkaVersion % Test,
  "com.typesafe.akka"          %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka"          %% "akka-http-testkit"   % akkaHTTPVersion % Test,
  "org.scalatest"              %% "scalatest"           % scalaTestVersion % Test,
  "io.spray"                   %% "spray-json"          % "1.3.3"
)
