import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class SocialNetworkImpl implements SocialNetwork {
    HashMap<String, Set<String>> mapOfUsers = new HashMap<>();
    /* Добавить участника */
    @Override
    public void addPerson(String name) {
        Set<String> tmp = new HashSet<>();
        this.mapOfUsers.put(name, tmp);
    }

    /* Добавить линк ("друга") to для участника from */
    @Override
    public void addConnection(String from, String to) {
        this.mapOfUsers.get(from).add(to);
        this.mapOfUsers.get(to).add(from);
    }

    /* Получить список друзей до уровня level
       level = 1 - непосредственные "друзья",
       level = 2 - непосредственные "друзья" + "друзья" "друзей"
       итд.
     */
    @Override
    public Set<String> getFriends(String name, int level) {
        if (!this.mapOfUsers.containsKey(name)) {
            throw new UserNotFoundException("Error! No Friends found.");
        }
        Set<String> friends = new HashSet<>();
        Set<String> tmpFriends = new HashSet<>(this.mapOfUsers.get(name));
        for (int i = 0; i < level; ++i) {
            Set<String> tmp = new HashSet<>();
            for (String friend : tmpFriends) {
                tmp.addAll(this.mapOfUsers.get(friend));
                friends.add(friend);
            }
            if (friends.size() == this.mapOfUsers.size())
                break;
            tmpFriends = tmp;
            tmpFriends.removeAll(friends);
        }
        friends.remove(name);
        return friends;
    }
    int getNumberOfUsers() {
        return mapOfUsers.size();
    }
}
