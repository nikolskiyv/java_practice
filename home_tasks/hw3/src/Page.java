import java.util.*;

public class Page {
    private final Map<String, Integer> linksWithClicks;
    private static final int LINKS_NUMBER = 10;

    public Page() {
        this.linksWithClicks = new HashMap<>();
        for (int i = 0; i < LINKS_NUMBER; i++) {
            linksWithClicks.put("URL #" + i, 0);
        }
    }

    public synchronized void click(String link) {
        int previousValue = linksWithClicks.get(link);
        linksWithClicks.put(link, previousValue + 1);
    }

    public Map<String, Integer> getLinksWithClicks() {
        return linksWithClicks;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        List<String> orderedLinks = new ArrayList<>(linksWithClicks.keySet());
        Collections.sort(orderedLinks);
        int clicksSummary = 0;
        for (String currentLink : orderedLinks) {
            clicksSummary += linksWithClicks.get(currentLink);
            result.append(currentLink)
                    .append(" was clicked ")
                    .append(linksWithClicks.get(currentLink))
                    .append("times;")
                    .append('\n');
        }
        return result.append("TOTAL: ")
                .append(clicksSummary)
                .append(" clicks.")
                .toString();
    }
}
