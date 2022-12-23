package library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryImpl implements Library {
    private final String jdbcUrl;
    private final String user;
    private final String password;

    public LibraryImpl(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    private void insertQuery(String query) {
        try (Connection ignored = getConnection()) {
            try (Statement stmt = getConnection().createStatement()) {
                stmt.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateQuery(String addSql, int id_1, int id_2) {
        try (Connection ignored = getConnection()) {
            try(PreparedStatement stmt = getConnection().prepareStatement(addSql)) {
                stmt.setInt(1, id_1);
                stmt.setInt(2, id_2);
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewBook(Book book) {
        String query = "insert into books (book_id, book_title) values ("+book.getId()+", '"+book.getTitle()+"')";
        insertQuery(query);
    }

    @Override
    public void addAbonent(Student student) {
        String query = "insert into ABONENTS(student_id, student_name) values("+student.getId()+", '"+student.getName()+"')";
        try (Connection ignored = getConnection()) {
            try(Statement stmt = getConnection().createStatement()) {
                stmt.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void borrowBook(Book book, Student student) {
        String query = "update books set student_id = ? where book_id = ?";
        try (Connection ignored = getConnection()) {
            try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
                stmt.setInt(1, student.getId());
                stmt.setInt(2, book.getId());
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(Book book, Student student) {
        String query = "update books set student_id = null where book_id = ? and student_id = ?";
        updateQuery(query, book.getId(), student.getId());
    }

    @Override
    public List<Book> findAvailableBooks() {
        String query = "select book_id, book_title from books where student_id is null";
        List<Book> books = new ArrayList<>();

        try (Connection ignored = getConnection()) {
            try(Statement stmt = getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    books.add(new Book(rs.getInt("book_id"), rs.getString("book_title")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Student> getAllStudents() {
        String query = "select student_id, student_name from abonents";
        List<Student> students = new ArrayList<>();

        try (Connection ignored = getConnection()) {
            try(Statement stmt = getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    students.add(new Student(rs.getInt("student_id"), rs.getString("student_name")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
