package ltu.card;

import java.io.IOException;

/**
 * This interface is used when creating new cards, holds the functions that are
 * needed in order to set up the cards and regions
 */
public interface ICardFactory {
    Card[] createCards() throws IOException;

    String[] createRegions() throws IOException;

}
