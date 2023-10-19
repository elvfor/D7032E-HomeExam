@echo off
cd /d "%~dp0\src"
set GSON_JAR=..\lib\gson-2.8.6.jar
set OUTPUT_DIR=..\bin
rem Change the current directory to the "bin" folder
  cd /d "%OUTPUT_DIR%"
rem Run the BoomerangGame with arguments (e.g., 2 and 0)
java -cp .;%GSON_JAR%; ltu.main.BoomerangGame 127.0.0.1