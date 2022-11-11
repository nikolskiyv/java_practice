import java.util.HashSet;
import java.util.Set;

public class Main {
    static final int THREADS_NUMBER = 50;
    static final int USERS_PER_THREAD = 10;

    public static void main(String[] args) {
        Page page = new Page();

        //Сюда положим созданные потоки
        Set<Thread> threads = new HashSet<>();
        //Создаём потоки
        for (int i = 0; i < THREADS_NUMBER; i++) {
            final int finalI = i;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < USERS_PER_THREAD; j++) {
                    User user = new User(page, "User #" + j + " from thread #" + finalI);
                    user.run();
                }
            });
            threads.add(thread);
        }

        long startTime = System.currentTimeMillis();
        //Запускаем потоки
        for (Thread thread : threads) {
            thread.start();
        }
        //Ждём, пока потоки закончат работу
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException interruptedException) {
                System.out.println("Thread " + thread.getName() + " was interrupted!");
            }
        }
        long endTime = System.currentTimeMillis();
        double secondsSpent = ((double) (endTime - startTime)) / 1000.0;

        System.out.println("Links clicked in " + secondsSpent + " seconds");
        System.out.println(page);
    }
}
