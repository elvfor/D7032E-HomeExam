package test;

import java.io.IOException;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import main.BoomerangGame;

public class BoomerangGameTest {
    private BoomerangGame test = null;

    @Before
    public void init() throws IOException {
        test = new BoomerangGame();

    }

    @Test
    public void testValidNrOfPlayers() throws Exception {
        assertTrue(BoomerangGame.checkNrOfPlayerReq(2, 0));
        assertTrue(BoomerangGame.checkNrOfPlayerReq(3, 1));
        assertTrue(BoomerangGame.checkNrOfPlayerReq(4, 2));
    }

    @Test
    public void testInvalidNrOfPlayers() throws Exception {
        assertFalse(BoomerangGame.checkNrOfPlayerReq(1, 0));
        assertFalse(BoomerangGame.checkNrOfPlayerReq(5, 0));
        assertFalse(BoomerangGame.checkNrOfPlayerReq(2, 3));
    }
}
