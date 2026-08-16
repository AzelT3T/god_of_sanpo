@echo off
cd /d "%~dp0"
echo Building APK (debug)...
if not exist gradlew.bat (
  echo gradlew.bat not found. Please ensure this repository contains gradlew.bat.
  exit /b 1
)
gradlew.bat assembleDebug
if %errorlevel% neq 0 (
  echo Build failed. Check output above for errors.
  exit /b %errorlevel%
)
echo Build finished. APKs are in app\build\outputs\apk\debug
