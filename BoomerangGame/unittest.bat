@echo off
rem Change the current directory to the "src" folder
cd /d "%~dp0\src"

set JDK_HOME=c:\Program\Java\jdk-11.0.13
set JUNIT=..\lib\org.junit4-4.3.1.jar
set GSON_JAR=..\lib\gson-2.8.6.jar
@mkdir ..\bin
set OUTPUT_DIR=..\bin
@echo "Compiling + runittests ..."
javac -d %OUTPUT_DIR% -cp .;%JUNIT%;%GSON_JAR%  test\*.java

rem Check if compilation was successful
if "%errorlevel%"=="0" (
  echo Compilation successful.
  
  rem Run the JUnit tests using JUnitCore
  java -cp .;%JUNIT%;%OUTPUT_DIR%;%JUNIT% org.junit.runner.JUnitCore test.initGameTest
) else (
  echo Compilation failed.
)