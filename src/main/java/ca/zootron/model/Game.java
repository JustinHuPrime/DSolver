package ca.zootron.model;

import ca.zootron.model.Turn.Phase;
import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import ca.zootron.model.map.Province.SupplyCenter;
import ca.zootron.model.map.Unit;
import ca.zootron.util.BadMapException;
import ca.zootron.util.NoSuchMapException;
import ca.zootron.util.Pair;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Game {

    @NotNull
    public final List<@NotNull Country> countries;
    @NotNull
    public final List<@NotNull Province> board;
    @NotNull
    public Turn turn;

    private Game(@NotNull List<@NotNull Country> countries, @NotNull List<@NotNull Province> provinces) {
        this.countries = countries;
        this.board = provinces;
        this.turn = new Turn(1, Phase.SPRING_MOVE);
    }

    public static Game fromMap(@NotNull String name) throws NoSuchMapException, BadMapException {
        InputStream mapFile = Game.class.getResourceAsStream("/maps/" + name + ".json");
        if (mapFile == null) {
            throw new NoSuchMapException("couldn't open map " + name);
        }

        try {
            JsonMap parsed = new Gson().fromJson(new InputStreamReader(mapFile), JsonMap.class);

            AtomicBoolean badFlag = new AtomicBoolean(false);

            // countries
            List<Country> countries = parsed.countries.stream().map(Country::new).toList();

            // provinces + units + supply centers
            List<Province> provinces = parsed.provinces.stream().map(jsonProvince -> {
                Province p = new Province(
                      jsonProvince.name,
                      new HashMap<>(),
                      parsed.units.stream().filter(unit -> unit.province.equals(jsonProvince.name)).findFirst().map(unit -> {
                          Optional<Country> owner = countries.stream().filter(c -> c.name.equals(unit.owner)).findFirst();
                          if (owner.isEmpty()) {
                              badFlag.set(true);
                              return null;
                          } else {
                              return new Pair<>(new Unit(owner.get()), unit.location);
                          }
                        }).orElse(null),
                      parsed.supplyCenters.stream().filter(sc -> sc.province.equals(jsonProvince.name)).findFirst().map(sc -> {
                          if (sc.owner == null) {
                              return new SupplyCenter(null);
                          } else {
                              Optional<Country> owner = countries.stream().filter(c -> c.name.equals(sc.owner)).findFirst();
                              if (owner.isEmpty()) {
                                  badFlag.set(true);
                                  return null;
                              } else {
                                  return new SupplyCenter(owner.get());
                              }
                          }
                      }).orElse(null));

                jsonProvince.locations.forEach(location -> p.adjacencies.put(location, new ArrayList<>()));

                return p;
            }).toList();
            if (badFlag.get()) {
                throw new BadMapException("couldn't parse map " + name);
            }

            // check: all units must be on the map
            parsed.units.forEach(unit -> {
                if (provinces.stream().filter(province -> province.name.equals(unit.province)).findAny().isEmpty()) {
                    badFlag.set(true);
                }
            });
            if (badFlag.get()) {
                throw new BadMapException("couldn't parse map " + name);
            }

            // check: all supply centers must be on the map
            parsed.supplyCenters.forEach(supplyCenter -> {
                if (provinces.stream().filter(province -> province.name.equals(supplyCenter.province)).findAny().isEmpty()) {
                    badFlag.set(true);
                }
            });
            if (badFlag.get()) {
                throw new BadMapException("couldn't parse map " + name);
            }

            // province adjacencies (requires provinces to all exist)
            parsed.adjacencies.forEach(adjacency -> provinces.stream().filter(p -> p.name.equals(adjacency.from)).findFirst().ifPresentOrElse(
                  p -> {
                      List<Pair<Province, Location>> adjacencyList = p.adjacencies.get(adjacency.fromLocation);
                      if (adjacencyList == null) {
                          badFlag.set(true);
                          return;
                      }

                      provinces.stream().filter(q -> q.name.equals(adjacency.to)).findFirst().ifPresentOrElse(
                            q -> {
                                List<Pair<Province, Location>> destAdjacencyList = q.adjacencies.get(adjacency.toLocation);
                                if (destAdjacencyList == null) {
                                    badFlag.set(true);
                                    return;
                                }

                                adjacencyList.add(new Pair<>(q, adjacency.toLocation));
                                destAdjacencyList.add(new Pair<>(p, adjacency.fromLocation));
                            },
                            () -> badFlag.set(true)
                      );
                  },
                  () -> badFlag.set(true)
            ));
            if (badFlag.get()) {
                throw new BadMapException("couldn't parse map " + name);
            }

            return new Game(countries, provinces);
        } catch (JsonParseException e) {
            throw new BadMapException("couldn't parse map " + name, e);
        }
    }

    private static class JsonMap {

        public List<String> countries;
        public List<JsonProvince> provinces;
        public List<JsonAdjacency> adjacencies;
        public List<JsonUnit> units;
        public List<JsonSupplyCenter> supplyCenters;
    }

    private static class JsonProvince {

        public String name;
        public List<Location> locations;
    }

    private static class JsonAdjacency {

        public String from;
        public Location fromLocation;
        public String to;
        public Location toLocation;
    }

    private static class JsonUnit {

        public String owner;
        public String province;
        public Location location;
    }

    private static class JsonSupplyCenter {

        @Nullable
        public String owner;
        public String province;
    }
}
