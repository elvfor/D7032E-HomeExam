@echo off

rem Compile all Java source files
javac BoomerangGame.java network\Server.java network\Client.java
javac card\Card.java player\Player.java player\HumanPlayer.java player\BotPlayer.java


rem Check if compilation was successful
if "%errorlevel%"=="0" (
  echo Compilation successful.
  echo To run the game, use: java BoomerangGame numPlayers numBots
  echo To run a client, use: java network.Client serverIPAddress
) else (
  echo Compilation failed.
)
