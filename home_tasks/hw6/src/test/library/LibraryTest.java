package library;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;

public class LibraryTest {
    private static final String db_url = "jdbc:mysql://localhost:3306/library";
    private static final String db_user = "root";
    private static final String db_pass = "";
    private final Library library = new LibraryImpl(db_url, db_user, db_pass);

    @BeforeClass
    public static void beforeClass() throws Exception {
        Connection connection = DriverManager.getConnection(db_url, db_user, db_pass);

        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader("src/main/java/library/tables.sql"));
        sr.runScript(reader);
    }

    @After
    public void tearDown() {}

    @Test
    public void addNewBook() {
        Book book_1 = new Book(1, "Book_1");
        Book book_2 = new Book(2, "Book_2");

        library.addNewBook(book_1);
        library.addNewBook(book_2);

        List<Book> books = library.findAvailableBooks();
        assertThat(books, hasItems(book_1, book_2));
    }

    @Test
    public void addAbonent() {
        Student student_1 = new Student(1, "Student_1");
        Student student_2 = new Student(2, "Student_2");

        library.addAbonent(student_1);
        library.addAbonent(student_2);

        List<Student> students = library.getAllStudents();
        assertThat(students, hasItems(student_1, student_2));
    }

    @Test
    public void borrowBook() {
        Book book_1 = new Book(1, "Book_1");
        Student student_1 = new Student(1, "Student_1");

        library.addNewBook(book_1);
        library.addAbonent(student_1);
        library.borrowBook(book_1, student_1);

        List<Book> books = library.findAvailableBooks();

        assertThat(books.isEmpty(), is(true));
    }

    @Test
    public void returnBook() throws Exception {
        Book book_1 = new Book(1, "Book_1");
        Student student_1 = new Student(1, "Student_1");

        library.addNewBook(book_1);
        library.addAbonent(student_1);
        library.borrowBook(book_1, student_1);

        List<Book> freeBooks = library.findAvailableBooks();

        library.returnBook(book_1, student_1);

        List<Book> freeBooksAfterReturn = library.findAvailableBooks();

        assertThat(freeBooks.isEmpty(), is(true));
        assertThat(freeBooksAfterReturn, hasItems(book_1));
    }

    @Test
    public void findAvailableBooks() throws Exception {
        Book book_1 = new Book(1, "Book_1");
        Student student_1 = new Student(1, "Student_1");

        library.addNewBook(book_1);
        library.addAbonent(student_1);
        library.borrowBook(book_1, student_1);

        List<Book> freeBooks = library.findAvailableBooks();

        library.returnBook(book_1, student_1);

        List<Book> freeBooksAfterReturn = library.findAvailableBooks();

        assertThat(freeBooks.isEmpty(), is(true));
        assertThat(freeBooksAfterReturn, hasItems(book_1));

    }

    @Test
    public void getAllStudents() throws Exception {
        Student student1 = new Student(1, "Student_1");
        Student student2 = new Student(2, "Student_2");

        library.addAbonent(student1);
        library.addAbonent(student2);

        List<Student> students = library.getAllStudents();

        assertThat(students, hasItems(student1, student2));
    }
}