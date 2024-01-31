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
  cd /d "%OUTPUT_DIR%"

  rem Run the JUnit tests using JUnitCore
  ::java -cp .;%JUNIT%; org.junit.runner.JUnitCore test.ScoringTest
  java -cp .;%JUNIT%;%GSON_JAR% org.junit.runner.JUnitCore test.BoomerangGameTest
    java -cp .;%JUNIT%;%GSON_JAR% org.junit.runner.JUnitCore test.InitRoundStateTest
       : java -cp .;%JUNIT%;%GSON_JAR% org.junit.runner.JUnitCore test.DraftCardsStateTest
        java -cp .;%JUNIT%;%GSON_JAR% org.junit.runner.JUnitCore test.ScoringTest

) else (
  echo Compilation failed.
)