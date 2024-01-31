This is for a Home Exam in curse D7032E at LTU. 

# BoomerangGame Readme

**BoomerangGame** is a Java package that provides a framework for a card game. It supports local and remote players, customization of game modes and rules, and multiple player types, including bots. Done in the course D7032E as a Home Exam.

## Usage

**Compilation and Execution:**

   - Use `compile.bat` to compile the package with a Java compiler.
   - Use `runHost.bat` to run the main class, `BoomerangGame`, as a host.
   - Use `runClient.bat` to run the game as a client

   Update the bat files for configuration of nr of players, ip address etc.

   Or run straight from the terminal.

**Command-Line Arguments:**

   - To play as a remote player, provide the IP address as the argument.
   - To play as a local player, specify the number of players and bots as arguments.

**Game Modes and Rules:**

   - Choose the game mode and rules (currently supports "Australia" mode and "Standard" rules).

**Player Types:**

   - Support for human and bot players.
   - Local players use the console for input/output.
   - Remote players connect via a server.

**Customization:**

   - Extend the package to support additional game modes, rules, or player types.
   - Use `createCards` and `createRegions` methods to add mode-specific cards and regions.

**Multiplayer Support:**

   - Play with 2 to 4 players or bots.
   - Set up a server for remote multiplayer, allowing multiple clients to connect.

**Gameplay:**

   - Manage game logic, scoring, and player actions.
   - Transition between game states (e.g., initializing a round, player turns).

**End of Game:**

   - The game ends when specific conditions are met, and the package terminates the game.

**Unit Testing:**

- To test the game with current tests, run unittest.bat
- For adding new tests, place them in the 'test' folder and add them to the unittest.bat file for compiling and to run them

**Diagrams:**
- Diagrams that illustrates the game and design choices/patterns can be found in the "diagrams" folder. 
