name := "akka-microservice"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")
resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= {
  val akkaVersion = "2.4.12"
  val akkaHttpVersion = "2.4.11"
  val scalaTestVersion = "2.2.6"
  val scalacticVersion = "2.2.6"
  val logbackVersion = "1.1.7"
  val scalaLoggingVersion = "3.5.0"
  val circeVersion = "0.5.4"
  val akkaHttpCirceVersion = "1.10.1"
  val twitter4jVersion = "4.0.5"
  val typesafeConfigVersion = "1.3.1"
  val mongoScalaDriverVersion = "1.1.1"
  val scalaMockVersion = "3.2.2"

  List(
    // Akka
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,

    // Logging
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,

    // Circe
    "io.circe" %% "circe-core" % circeVersion,
    "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirceVersion,

    // Twitter
    "org.twitter4j" % "twitter4j-core" % twitter4jVersion,
    "org.twitter4j" % "twitter4j-stream" % twitter4jVersion,

    // Mongo
    "org.mongodb.scala" %% "mongo-scala-driver" % mongoScalaDriverVersion,

    // Config
    "com.typesafe" % "config" % typesafeConfigVersion,

    // Test
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % Test
  )
}

logBuffered in Test := false

crossPaths := false

scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-unchecked",
  "-deprecation",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-unused-import"
  //"-Ywarn-value-discard"
)
