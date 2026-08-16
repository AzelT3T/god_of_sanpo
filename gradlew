#!/bin/sh
# Gradle wrapper bootstrap for *nix
if ! command -v java >/dev/null 2>&1; then
  echo "Java not found. Install JDK and ensure java is on PATH."
  exit 1
fi
DIR="$(cd "$(dirname "$0")" && pwd)"
GRADLE_WRAPPER="$DIR/gradle/wrapper/gradle-wrapper.jar"
if [ ! -f "$GRADLE_WRAPPER" ]; then
  echo "gradle wrapper jar not found; attempting to run anyway"
fi
java -jar "$GRADLE_WRAPPER" "$@"
