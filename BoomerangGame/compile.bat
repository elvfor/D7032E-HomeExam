@echo off

rem Change the current directory to the "src" folder
cd /d "%~dp0\src"

rem Compile all Java source files
set GSON_JAR=..\lib\gson-2.8.6.jar
@mkdir ..\bin
set OUTPUT_DIR=..\bin
javac -d %OUTPUT_DIR% -cp .;%GSON_JAR%  ltu\card\*.java ltu\exception\*.java ltu\game\logic\*.java ltu\game\scoring\*.java ltu\game\state\*.java ltu\main\*.java ltu\network\*.java  ltu\player\*.java ltu\player\actions\*.java ltu\player\communication\*.java
rem Check if compilation was successful
if "%errorlevel%"=="0" (
  echo Compilation successful.
  
  rem Change the current directory to the "bin" folder
  cd /d "%OUTPUT_DIR%"
  
  echo To run the game,  go to bin directory and use: java -cp .;%GSON_JAR%; ltu.main.BoomerangGame numPlayers numBots
  echo To run a client, go to bin directory and use: java ltu.main.BoomerangGame serverIPAddress
  echo Or even easier, run as host using .\runHost.bat
  echo Run as Client using .\runClient.bat
) else (
  echo Compilation failed.
)