package ca.zootron.model;

import ca.zootron.model.Turn.Phase;
import ca.zootron.model.map.Country;
import ca.zootron.model.map.Province;
import ca.zootron.model.map.Province.Location;
import ca.zootron.model.map.Province.ProvinceLocation;
import ca.zootron.model.map.Province.SupplyCenter;
import ca.zootron.model.map.Unit;
import ca.zootron.model.order.BuildOrder;
import ca.zootron.model.order.DisbandOrder;
import ca.zootron.model.order.Order;
import ca.zootron.util.BadMapException;
import ca.zootron.util.IllegalOrderListException;
import ca.zootron.util.NoSuchMapException;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Game {

    @NotNull
    public final List<@NotNull Country> countries;
    @NotNull
    public final List<@NotNull Province> board;
    @NotNull
    public Turn turn;
    public final boolean onlyHomeSCBuilds;

    private Game(@NotNull List<@NotNull Country> countries, @NotNull List<@NotNull Province> provinces, boolean onlyHomeSCBuilds) {
        this.countries = countries;
        this.board = provinces;
        this.turn = new Turn(1, Phase.SPRING_MOVE);
        this.onlyHomeSCBuilds = onlyHomeSCBuilds;
    }

    public void resolveOrders(List<Order> orders) throws IllegalOrderListException {
        switch (turn.phase) {
            case SPRING_MOVE, FALL_MOVE -> resolveMoveOrders(orders);
            case SPRING_RETREAT, FALL_RETREAT -> resolveRetreatOrders(orders);
            case WINTER_BUILD -> resolveBuildOrders(orders);
        }
    }

    private void resolveMoveOrders(List<Order> orders) throws IllegalOrderListException {
        // TODO
    }

    private void resolveRetreatOrders(List<Order> orders) throws IllegalOrderListException {
        // TODO
    }

    private void resolveBuildOrders(List<Order> orders) throws IllegalOrderListException {
        // only build and disband orders are allowed
        orders.forEach(order -> {
            if (!(order instanceof BuildOrder || order instanceof DisbandOrder)) {
                throw new IllegalOrderListException(order, "only build and disband orders are allowed in winter");
            }
        });

        // only build orders to controlled but empty SCs are allowed
        orders.forEach(order -> {
            if (order instanceof BuildOrder buildOrder) {
                if (!(buildOrder.who.supplyCenter != null && buildOrder.who.unit == null)) {
                    throw new IllegalOrderListException(buildOrder, "only build orders to controlled but empty supply centers are allowed");
                }
            }
        });

        if (onlyHomeSCBuilds) {
            // special rule: if map is home SC builds only, only build orders in home SCs are allowed
            orders.forEach(order -> {
                if (order instanceof BuildOrder buildOrder) {
                    assert buildOrder.who.supplyCenter != null;
                    if (!(buildOrder.who.supplyCenter.controller == buildOrder.who.supplyCenter.originalController)) {
                        throw new IllegalOrderListException(buildOrder, "only build orders to home supply centers are allowed");
                    }
                }
            });
        }

        // only disband orders to units are allowed
        orders.forEach(order -> {
            if (order instanceof DisbandOrder disbandOrder) {
                if (disbandOrder.who.unit == null) {
                    throw new IllegalOrderListException(disbandOrder, "only disband orders to units are allowed");
                }
            }
        });

        // for each country
        countries.forEach(country -> {
            int adjustmentsRemaining = adjustmentsRemainingForCountry(country);

            if (adjustmentsRemaining < 0) {
                // no build orders are allowed
                orders.forEach(order -> {
                    if (order instanceof BuildOrder buildOrder) {
                        if (buildOrder.getIssuer() == country) {
                            throw new IllegalOrderListException(buildOrder, "cannot issue build orders if required to disband");
                        }
                    }
                });

                // must be exactly -adjustmentsRemaining disband orders
                if (orders.stream().filter(order -> {
                    if (order instanceof DisbandOrder disbandOrder) {
                        return disbandOrder.getIssuer() == country;
                    } else {
                        return false;
                    }
                }).count() != -adjustmentsRemaining) {
                    throw new IllegalOrderListException(null, "must have exactly as many disband orders as required");
                }
            } else if (adjustmentsRemaining > 0) {
                // no disband orders are allowed
                orders.forEach(order -> {
                    if (order instanceof DisbandOrder disbandOrder) {
                        if (disbandOrder.getIssuer() == country) {
                            throw new IllegalOrderListException(disbandOrder, "cannot issue disband orders if allowed to build");
                        }
                    }
                });

                // must be no more than adjustmentsRemaining build orders
                if (orders.stream().filter(order -> {
                    if (order instanceof BuildOrder buildOrder) {
                        return buildOrder.getIssuer() == country;
                    } else {
                        return false;
                    }
                }).count() > adjustmentsRemaining) {
                    throw new IllegalOrderListException(null, "must have no more build orders than builds allowed");
                }
            } else {
                // no adjustments permitted either way
                orders.forEach(order -> {
                    if (order.getIssuer() == country) {
                        throw new IllegalOrderListException(order, "cannot issue any orders in build phase if no adjustments to be made");
                    }
                });
            }
        });

        // orders are valid, and always succeed - perform adjustments
        orders.forEach(order -> {
            if (order instanceof BuildOrder buildOrder) {
                assert buildOrder.getIssuer() != null;
                buildOrder.who.unit = new Unit(buildOrder.where, buildOrder.getIssuer());
            } else {
                DisbandOrder disbandOrder = (DisbandOrder) order;
                disbandOrder.who.unit = null;
            }
        });
    }

    private int adjustmentsRemainingForCountry(Country country) {
        AtomicInteger remaining = new AtomicInteger();
        board.forEach(province -> {
            if (province.supplyCenter != null && province.supplyCenter.controller == country) {
                remaining.getAndIncrement();
            }
            if (province.unit != null && province.unit.owner == country) {
                remaining.getAndDecrement();
            }
        });
        return remaining.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Game game = (Game) o;
        return countries.equals(game.countries) && board.equals(game.board) && turn.equals(game.turn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countries, board, turn);
    }

    public static Game fromMap(@NotNull String name) throws NoSuchMapException, BadMapException {
        InputStream mapFile = Game.class.getResourceAsStream("/maps/" + name + ".json");
        if (mapFile == null) {
            throw new NoSuchMapException("couldn't open map " + name);
        }

        try {
            JsonMap parsed = new Gson().fromJson(new InputStreamReader(mapFile), JsonMap.class);

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
                              throw new BadMapException("couldn't parse map " + name + ": unit owned by non-existent country " + unit.owner);
                          } else {
                              return new Unit(unit.location, owner.get());
                          }
                        }).orElse(null),
                      parsed.supplyCenters.stream().filter(sc -> sc.province.equals(jsonProvince.name)).findFirst().map(sc -> {
                          if (sc.owner == null) {
                              return new SupplyCenter(null);
                          } else {
                              Optional<Country> owner = countries.stream().filter(c -> c.name.equals(sc.owner)).findFirst();
                              if (owner.isEmpty()) {
                                  throw new BadMapException("couldn't parse map " + name + ": supply center owned by non-existent country " + sc.owner);
                              } else {
                                  return new SupplyCenter(owner.get());
                              }
                          }
                      }).orElse(null));

                jsonProvince.locations.forEach(location -> p.adjacencies.put(location, new ArrayList<>()));

                return p;
            }).toList();

            // check: all units must be on the map
            parsed.units.forEach(unit -> {
                if (provinces.stream().filter(province -> province.name.equals(unit.province)).findAny().isEmpty()) {
                    throw new BadMapException("couldn't parse map " + name + ": unit placed in non-existent province " + unit.province);
                }
            });

            // check: all supply centers must be on the map
            parsed.supplyCenters.forEach(supplyCenter -> {
                if (provinces.stream().filter(province -> province.name.equals(supplyCenter.province)).findAny().isEmpty()) {
                    throw new BadMapException("couldn't parse map " + name + ": supply center placed in non-existent province " + supplyCenter.province);
                }
            });

            // province adjacencies (requires provinces to all exist)
            parsed.adjacencies.forEach(adjacency -> provinces.stream().filter(p -> p.name.equals(adjacency.from)).findFirst().ifPresentOrElse(
                  p -> {
                      List<ProvinceLocation> adjacencyList = p.adjacencies.get(adjacency.fromLocation);
                      if (adjacencyList == null) {
                          throw new BadMapException("couldn't parse map " + name + ": province " + adjacency.from + " has adjacency with undeclared location " + adjacency.fromLocation);
                      }

                      provinces.stream().filter(q -> q.name.equals(adjacency.to)).findFirst().ifPresentOrElse(
                            q -> {
                                List<ProvinceLocation> destAdjacencyList = q.adjacencies.get(adjacency.toLocation);
                                if (destAdjacencyList == null) {
                                    throw new BadMapException("couldn't parse map " + name + ": province " + adjacency.to + " has adjacency with undeclared location " + adjacency.toLocation);
                                }

                                adjacencyList.add(new ProvinceLocation(q, adjacency.toLocation));
                                destAdjacencyList.add(new ProvinceLocation(p, adjacency.fromLocation));
                            },
                            () -> {
                                throw new BadMapException("couldn't parse map " + name + ": adjacency to non-existent province " + adjacency.to);
                            }
                      );
                  },
                  () -> {
                      throw new BadMapException("couldn't parse map " + name + ": adjacency from non-existent province " + adjacency.from);
                  }
            ));

            return new Game(countries, provinces, parsed.onlyHomeSCBuilds);
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
        public boolean onlyHomeSCBuilds;
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
