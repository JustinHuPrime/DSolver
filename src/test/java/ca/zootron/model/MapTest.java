package ca.zootron.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import ca.zootron.model.Turn.Phase;
import ca.zootron.model.map.Province.Location;
import ca.zootron.util.BadMapException;
import ca.zootron.util.NoSuchMapException;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class MapTest {

    @Test
    void fromMapNonExistent() {
        try {
            Game.fromMap("doesntexist");
            fail("Exception expected");
        } catch (NoSuchMapException e) {
            // all good
        } catch (Exception e) {
            fail("Wrong exception");
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
            game.board.forEach(province -> assertTrue(((province.supplyCenter == null || province.supplyCenter.controller == null) && province.unit == null) || (province.supplyCenter != null && province.unit != null && Objects.equals(province.supplyCenter.controller, province.unit.owner))));
            game.countries.forEach(country -> assertEquals(game.board.stream().filter(province -> province.unit != null && province.unit.owner.equals(country)).count(), game.board.stream().filter(province -> province.supplyCenter != null && province.supplyCenter.controller != null && province.supplyCenter.controller.equals(country)).count()));

            assertEquals(new Turn(1, Phase.SPRING_MOVE), game.turn);

            assertTrue(game.onlyHomeSCBuilds);
        } catch (NoSuchMapException | BadMapException e) {
            fail("No exception expected");
        }
    }

    @Test
    void modern2Map() {
        try {
            Game game = Game.fromMap("modern2");

            assertEquals(10, game.countries.size());
            assertEquals(10, game.countries.stream().distinct().count());
            game.countries.forEach(country -> assertTrue(game.countries.stream().filter(other -> other.name.equals(country.name) && other != country).findAny().isEmpty()));

            assertEquals(141, game.board.size());
            game.board.forEach(province -> assertTrue(game.board.stream().filter(other -> other.name.equals(province.name) && other != province).findAny().isEmpty()));
            // Iceland and Ireland are actual islands in modern2
            game.board.forEach(province -> province.adjacencies.forEach((location, connections) -> assertTrue(((province.name.equals("Iceland") || province.name.equals("Ireland")) && location == Location.LAND) || !connections.isEmpty())));

            assertEquals(64, game.board.stream().filter(province -> province.supplyCenter != null).count());
            assertEquals(38, game.board.stream().filter(province -> province.supplyCenter != null && province.supplyCenter.controller != null).count());
            assertEquals(38, game.board.stream().filter(province -> province.unit != null).count());
            game.board.forEach(province -> assertTrue(((province.supplyCenter == null || province.supplyCenter.controller == null) && province.unit == null) || (province.supplyCenter != null && province.unit != null && Objects.equals(province.supplyCenter.controller, province.unit.owner))));
            game.countries.forEach(country -> assertEquals(game.board.stream().filter(province -> province.unit != null && province.unit.owner.equals(country)).count(), game.board.stream().filter(province -> province.supplyCenter != null && province.supplyCenter.controller != null && province.supplyCenter.controller.equals(country)).count()));

            assertEquals(new Turn(1, Phase.SPRING_MOVE), game.turn);

            assertFalse(game.onlyHomeSCBuilds);
        } catch (NoSuchMapException | BadMapException e) {
            fail("No exception expected");
        }
    }
}