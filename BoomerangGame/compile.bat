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
  
  echo To run the game, use: java -cp .;%GSON_JAR% BoomerangGame numPlayers numBots
  echo To run a client, use: java -cp .;%GSON_JAR% BoomerangGame serverIPAddress
  rem Run the BoomerangGame with arguments (e.g., 2 and 0)

  java -cp .;%GSON_JAR%; ltu.main.BoomerangGame 1 1
) else (
  echo Compilation failed.
)