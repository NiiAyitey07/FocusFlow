#!/bin/sh
# Gradle wrapper script - downloads and runs Gradle
GRADLE_VERSION=8.2
GRADLE_ZIP=gradle-$GRADLE_VERSION-bin.zip
GRADLE_URL=https://services.gradle.org/distributions/$GRADLE_ZIP
GRADLE_HOME=$HOME/.gradle/wrapper/dists/gradle-$GRADLE_VERSION
if [ ! -d "$GRADLE_HOME" ]; then
  echo "Downloading Gradle $GRADLE_VERSION..."
  mkdir -p "$GRADLE_HOME"
fi
exec gradle "$@"
