import java.util.Set;

/* Социальная сеть */
public interface SocialNetwork {
    /* Добавить участника */
    void addPerson(String name);

    /* Добавить линк ("друга") to для участника from */
    void addConnection(String from, String to);

    /* Получить список друзей до уровня level
       level = 1 - непосредственные "друзья",
       level = 2 - непосредственные "друзья" + "друзья" "друзей"
       итд.
     */
    Set<String> getFriends(String name, int level);
}

