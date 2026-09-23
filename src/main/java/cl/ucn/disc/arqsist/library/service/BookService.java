package cl.ucn.disc.arqsist.library.service;

import cl.ucn.disc.arqsist.library.dao.BookDao;
import cl.ucn.disc.arqsist.library.model.Book;

import java.sql.SQLException;
import java.util.List;

public final class BookService {

    private final BookDao dao;

    public BookService(BookDao dao) {
        this.dao = dao;
    }

    public List<Book> listAll() throws SQLException {
        return dao.findAll();
    }

    public Book findById(int id) throws SQLException {
        return dao.findById(id);
    }

    public Book create(Book book) throws SQLException {
        book.setAvailableCopies(book.getTotalCopies());
        dao.create(book);
        return book;
    }

    public void borrow(int bookId) throws SQLException {
        Book book = dao.findById(bookId);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        dao.update(book);
    }

    public void returnCopy(int bookId) throws SQLException {
        Book book = dao.findById(bookId);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        dao.update(book);
    }
}
