package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import junit.framework.Assert;
import main.BoomerangGame;

public class BoomerangGameTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private BoomerangGame BoomerangGameTest = null;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /*
     * @Test
     * public void testCheckNrOfPlayerReq() throws Exception {
     * // Call the method that prints output (BoomerangGame constructor with args)
     * String[] args = { "2", "1" }; // Adjust the args as needed
     * new BoomerangGame(args);
     * 
     * // Get the captured console output
     * String output = outputStreamCaptor.toString().trim(); // Remove
     * // leading/trailing whitespace
     * 
     * // Assert that the output contains the expected message
     * assertEquals(output, ("Initializing game with"));
     * 
     * }
     */
    /*
     * @Test
     * public void testCheckNrOfPlayerReq() throws Exception {
     * // Call the method that prints output (BoomerangGame constructor with args)
     * String[] args = { "4", "1" }; // Adjust the args as needed
     * new BoomerangGame(args);
     * assertEquals(
     * "This game is for a total of 2-4 players/bots Server syntax: java BoomerangAustralia numPlayers numBots Client syntax: IP"
     * ,
     * outContent.toString());
     * }
     * 
     * @Test
     * public void err() {
     * System.err.print("hello again");
     * assertEquals("hello again", errContent.toString());
     * }
     */
    // Requirement 1
    @Test
    public void testCheckNrOfPlayerReqOK() throws Exception {
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(2, 0));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(4, 0));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(2, 2));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(1, 3));
        assertEquals(true, BoomerangGame.checkNrOfPlayerReq(1, 1));
    }

    @Test
    public void testCheckNrOfPlayerReqNotOK() throws Exception {
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(2, 3));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(5, 0));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(1, 0));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(0, 5));
        assertEquals(false, BoomerangGame.checkNrOfPlayerReq(3, 3));
    }

}