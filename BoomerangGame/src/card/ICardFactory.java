package card;

import java.io.IOException;

public interface ICardFactory {
    Card[] createCards() throws IOException;

    String[] createRegions() throws IOException;

}
