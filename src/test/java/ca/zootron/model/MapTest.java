package ca.zootron.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import ca.zootron.model.Turn.Phase;
import org.junit.jupiter.api.Test;

class MapTest {

    @Test
    void fromMapDecimalYear() {
        try {
            Game.fromMap("decimalYear");
            fail("Exception expected");
        } catch (Exception e) {
            // swallow
        }
    }

    @Test
    void classicMap() {
        try {
            Game game = Game.fromMap("classic");

            assertEquals(7, game.countries.size());
            assertEquals(7, game.countries.stream().distinct().count());
            game.countries.forEach(country -> assertTrue(game.countries.stream().filter(other -> other.name.equals(country.name) && other != country).findAny().isEmpty()));

            assertEquals(75, game.board.size());
            game.board.forEach(province -> assertTrue(game.board.stream().filter(other -> other.name.equals(province.name) && other != province).findAny().isEmpty()));
            game.board.forEach(province -> province.adjacencies.forEach((location, connections) -> assertFalse(connections.isEmpty())));

            assertEquals(34, game.board.stream().filter(province -> province.supplyCenter != null).count());
            assertEquals(22, game.board.stream().filter(province -> province.supplyCenter != null && province.supplyCenter.controller != null).count());
            assertEquals(22, game.board.stream().filter(province -> province.unit != null).count());

            assertEquals(new Turn(1, Phase.SPRING_MOVE), game.turn);
        } catch (Exception e) {
            fail("No exception expected");
        }
    }
}