import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SocialNetworkImplTest {
    SocialNetworkImpl network;
    String user1 = "User 1";
    String user2 = "User 2";
    String user3 = "User 3";
    String user4 = "User 4";
    String user5 = "User 5";
    String user6 = "User 6";
    String user7 = "User 7";
    String user8 = "User 8";
    @Before
    public void setup() {
        network = new SocialNetworkImpl();
        network.addPerson(user1);
        network.addPerson(user2);
        network.addPerson(user3);
        network.addPerson(user4);
        network.addPerson(user5);
        network.addPerson(user6);
        network.addPerson(user7);
        network.addPerson(user8);
        network.addConnection(user1, user2);
        network.addConnection(user1, user4);
        network.addConnection(user2, user3);
        network.addConnection(user4, user5);
        network.addConnection(user4, user6);
        network.addConnection(user5, user6);
        network.addConnection(user6, user7);
        network.addConnection(user5, user8);
    }

    @After
    public void clear() {
        network = null;
    }

    @Test
    public void addPerson() {
        assert network.getNumberOfUsers() == 8;
        network.addPerson("User 9");
        assert network.getNumberOfUsers() == 9;
        network.addPerson("User 10");
        assert network.getNumberOfUsers() == 10;
        network.addPerson("User 11");
        assert network.getNumberOfUsers() == 11;
        network.addPerson("User 12");
        assert network.getNumberOfUsers() == 12;
        network.addPerson("User 12");
        assert network.getNumberOfUsers() == 12;
    }

    @Test
    public void addConnection() {
        assertThat(network.mapOfUsers.get(user2), hasItems(user1, user3));
        assertThat(network.mapOfUsers.get(user3), hasItems(user2));
        assertThat(network.mapOfUsers.get(user4), hasItems(user1, user5, user6));
        assertThat(network.mapOfUsers.get(user5), hasItems(user4, user6, user8));
        assertThat(network.mapOfUsers.get(user6), hasItems(user4, user5, user7));
        assertThat(network.mapOfUsers.get(user7), hasItems(user6));
        assertThat(network.mapOfUsers.get(user8), hasItems(user5));
    }

    @Test
    public void getFriends() {
        assertEquals(network.getFriends(user8, 2), new HashSet<>(Arrays.asList("User 5", "User 6", "User 4")));
        assertEquals(network.getFriends(user8, 3), new HashSet<>(Arrays.asList("User 5", "User 6", "User 4",
                                                                                 "User 1", "User 7")));
        assertEquals(network.getFriends(user4, 3), new HashSet<>(Arrays.asList("User 1", "User 2",
                                                                                 "User 3", "User 5",
                                                                                 "User 6", "User 7", "User 8")));

        assertNotEquals(network.getFriends(user8, 2), new HashSet<>(Arrays.asList("User 3", "User 6", "User 4")));
        assertNotEquals(network.getFriends(user8, 3), new HashSet<>(Arrays.asList("User 5", "User 4",
                                                                                    "User 1", "User 7")));
        assertNotEquals(network.getFriends(user4, 3), new HashSet<>(Arrays.asList("User 1", "User 2",
                                                                                    "User 3", "User 4", "User 5",
                                                                                    "User 6", "User 7", "User 8")));


        assertEquals(network.getFriends(user4, 20), new HashSet<>(Arrays.asList("User 1", "User 2",
                                                                                  "User 3", "User 5",
                                                                                  "User 6", "User 7", "User 8")));
    }
}