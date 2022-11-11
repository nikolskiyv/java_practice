import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User implements Runnable {
    private final Page page;
    private final String name;

    public User(Page page, String name) {
        this.page = page;
        this.name = name;
    }

    @Override
    public void run() {
        Random random = new Random();
        List<String> links = new ArrayList<>(page.getLinksWithClicks().keySet());
        String randomLink = String.valueOf(links.get(random.nextInt(links.size())));
        page.click(randomLink);
        System.out.println(name + " has just clicked " + randomLink);
    }
}
