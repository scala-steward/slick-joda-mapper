import sbt._
import sbt.Keys._
import scalariform.formatter.preferences._

def testContainerVersion = "1.20.4"

lazy val `slick-joda-mapper` = project.in(file("."))
  .settings(scalariformSettings)
  .settings(publishingSettings)
  .settings(
    name := "slick-joda-mapper",
    organization := "com.github.tototoshi",
    version := "2.9.1",
    crossScalaVersions := Seq("2.12.20", "2.13.16", "3.3.5"),
    scalaVersion := "2.13.16",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-language:reflectiveCalls",
      "-language:implicitConversions",
    ),
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) =>
          Seq("-Xsource:3")
        case _ =>
          Nil
      }
    },
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.12.7" % "provided",
      "org.joda" % "joda-convert" % "2.2.4" % "provided",
      "com.h2database" % "h2" % "2.2.224" % "test",
      "com.dimafeng" %% "testcontainers-scala" % "0.41.8" % "test",
      "com.mysql" % "mysql-connector-j" % "9.2.0" % "test",
      "org.postgresql" % "postgresql" % "42.7.5" % "test",
      "org.testcontainers" % "mysql" % testContainerVersion % "test",
      "org.testcontainers" % "postgresql" % testContainerVersion % "test",
      "org.slf4j" % "slf4j-simple" % "2.0.16" % "test",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test",
      "com.typesafe.slick" %% "slick" % "3.5.2" % "provided",
    ),
    initialCommands += """
      import org.joda.time._
      import java.sql._
    """
  )

lazy val scalariformSettings = Seq(
  scalariformPreferences := scalariformPreferences.value
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(DoubleIndentConstructorArguments, true)
    .setPreference(DanglingCloseParenthesis, Preserve)
)

lazy val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := _publishTo(version.value),
  Test / publishArtifact := false,
  pomExtra := _pomExtra
)

def _publishTo(v: String) = {
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

lazy val _pomExtra =
  <url>https://github.com/tototoshi/slick-joda-mapper</url>
    <licenses>
      <license>
        <name>Two-clause BSD-style license</name>
        <url>https://github.com/tototoshi/slick-joda-mapper/blob/master/LICENSE.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:tototoshi/slick-joda-mapper.git</url>
      <connection>scm:git:git@github.com:tototoshi/slick-joda-mapper.git</connection>
    </scm>
    <developers>
      <developer>
        <id>tototoshi</id>
        <name>Toshiyuki Takahashi</name>
        <url>https://tototoshi.github.com</url>
      </developer>
    </developers>
