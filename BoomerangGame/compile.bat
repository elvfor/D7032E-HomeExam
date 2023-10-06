@echo off

rem Change the current directory to the "src" folder
cd /d "%~dp0\src"

rem Compile all Java source files
set GSON_JAR=..\lib\gson-2.8.6.jar
@mkdir ..\bin
set OUTPUT_DIR=..\bin
javac -d %OUTPUT_DIR% -cp .;%GSON_JAR% *.java network\*.java card\*.java player\*.java game\*.java

rem Check if compilation was successful
if "%errorlevel%"=="0" (
  echo Compilation successful.
  
  rem Change the current directory to the "bin" folder
  cd /d "%OUTPUT_DIR%"
  
  echo To run the game, use: java -cp .;%GSON_JAR% BoomerangGame numPlayers numBots
  echo To run a client, use: java -cp .;%GSON_JAR% BoomerangGame serverIPAddress
  rem Run the BoomerangGame with arguments (e.g., 2 and 0)
  java -cp .;%GSON_JAR% BoomerangGame 2 0
) else (
  echo Compilation failed.
)
