@echo off
rem Gradle wrapper bootstrap for Windows
set DIR=%~dp0
java -version >nul 2>&1
if %errorlevel% neq 0 (
  echo Java not found. Install JDK and ensure java is on PATH.
  exit /b 1
)
set GRADLE_WRAPPER=%DIR%gradle\wrapper\gradle-wrapper.jar
if not exist "%GRADLE_WRAPPER%" (
  echo gradle wrapper jar not found; attempting to download via built-in bootstrap
)
java -jar "%GRADLE_WRAPPER%" %*
