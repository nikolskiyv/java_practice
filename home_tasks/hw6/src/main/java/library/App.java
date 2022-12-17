package library;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

public class App {

    private static final Logger log = Logger.getLogger(basket.App.class);

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        // Кредиты для подключения к БД
        String db_url = "jdbc:mysql://localhost:3306/library";
        String db_user = "root";
        String db_pass = "";

        // Создаем экземпляр библиотеки
        Library library = new LibraryImpl(db_url, db_user, db_pass);

        // Создаем коннект к базе
        Connection connection = DriverManager.getConnection(db_url, db_user, db_pass);

        // Создаем таблицы в базе
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader("src/main/java/library/tables.sql"));
        sr.runScript(reader);

        // Создаем экземпляры книг
        Book book_1 = new Book(1, "Чистый код");
        Book book_2 = new Book(2, "Почему Python лучше, чем Java");
        Book book_3 = new Book(3, "Легкий способ бросить курить");

        // Добавим книги в библиотеку
        library.addNewBook(book_1);
        library.addNewBook(book_2);
        library.addNewBook(book_3);

        // Создаем экземпляры студентов
        Student student_1 = new Student(1, "Александр");
        Student student_2 = new Student(2, "Матвей");

        // Выдаем студентам абонементы
        library.addAbonent(student_1);
        library.addAbonent(student_2);

        // Студенты берут книги из библиотеки
        library.borrowBook(book_1, student_1);
        library.borrowBook(book_2, student_2);

        // Посмотрим информацию по всем студентам
        log.info(library.getAllStudents());

        // Посмотрим доступные в библиотеке книги
        log.info(library.findAvailableBooks());

        // Александр возвращает книгу в библиотеку
        library.returnBook(book_1, student_1);

        // Стала ли книга снова доступной?
        log.info(library.findAvailableBooks());

    }
}
