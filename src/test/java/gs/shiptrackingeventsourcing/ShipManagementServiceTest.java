package gs.shiptrackingeventsourcing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;

class ShipManagementServiceTest {

    private ShipManagementService shipManagementService;

    @BeforeEach
    public void setup() {
        shipManagementService = new ShipManagementService();
    }

    @Test
    public void addShip_addsNewShipsToRegistry() {
        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        Assertions.assertEquals(2, shipManagementService.getShipList().size());
    }

    @Test
    public void addShip_doesNotAddDuplicateShipsToRegistry() {
        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());

        Assertions.assertEquals(1, shipManagementService.getShipList().size());
    }

    @Test
    public void removeShip_removesShipThatExistsFromRegistry() {
        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        Assertions.assertEquals(2, shipManagementService.getShipList().size());

        shipManagementService.removeShip("BF");

        Assertions.assertEquals(1, shipManagementService.getShipList().size());
    }

    @Test
    public void removeShip_doesNotRemovesShipThatDoesNotExistsFromRegistry() {
        Assertions.assertEquals(0, shipManagementService.getShipList().size());

        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        Assertions.assertEquals(2, shipManagementService.getShipList().size());

        shipManagementService.removeShip("AA");

        Assertions.assertEquals(2, shipManagementService.getShipList().size());
    }

    @Test
    public void getShipList_returnsTheCompleteCurrentRegistry() {
        shipManagementService.addShip(Ship.builder().id("FB").name("Foobar").build());
        shipManagementService.addShip(Ship.builder().id("BF").name("Barfoo").build());

        List<Ship> expectedList = asList(
                Ship.builder().id("FB").name("Foobar").build(),
                Ship.builder().id("BF").name("Barfoo").build()
        );

        Assertions.assertTrue(shipManagementService.getShipList().containsAll(expectedList));

        shipManagementService.removeShip("AA");

        Assertions.assertTrue(shipManagementService.getShipList().containsAll(expectedList));

        shipManagementService.removeShip("BF");

        Assertions.assertTrue(shipManagementService.getShipList().containsAll(List.of(
                Ship.builder().id("FB").name("Foobar").build()
        )));
    }
}