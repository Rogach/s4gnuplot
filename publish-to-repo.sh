#!/bin/bash

name=`sed -n 's_\s*name\s*:=\s*"\(.*\)"_\1_p' build.sbt`
version=`sed -n 's_\s*version\s*:=\s*"\(.*\)"_\1_p' build.sbt`
scalaVersion=`sed -n 's_\s*scalaVersion\s*:=\s*"\(.*\)"_\1_p' build.sbt`
sbt publish-local
mkdir maven/org/rogach/${name}/${version}/
cp ~/.ivy2/local/default/${name}_${scalaVersion}/${version}/jars/${name}_${scalaVersion}.pom maven/org/rogach/${name}/${version}/${name}-${version}.jar
cat ~/.ivy2/local/default/${name}_${scalaVersion}/${version}/poms/${name}_${scalaVersion}.jar \
  | sed 's/<groupId>default<\/groupId>/<groupId>org.rogach<\/groupId>/' \
  | sed "s/<artifactId>${name}_${scalaVersion}<\/artifactId>/<arifactId>${name}<\/artifactId>/" \
  > maven/org/rogach/${name}/${version}/${name}-${version}.pom
