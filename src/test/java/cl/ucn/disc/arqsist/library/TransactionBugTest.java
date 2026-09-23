package cl.ucn.disc.arqsist.library;

import cl.ucn.disc.arqsist.library.dao.BookDao;
import cl.ucn.disc.arqsist.library.dao.LoanDao;
import cl.ucn.disc.arqsist.library.dao.MemberDao;
import cl.ucn.disc.arqsist.library.db.Database;
import cl.ucn.disc.arqsist.library.model.Book;
import cl.ucn.disc.arqsist.library.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionBugTest {

    private BookDao bookDao;
    private MemberService memberService;

    @BeforeEach
    void setUp() throws Exception {
        Database db = new Database("jdbc:sqlite::memory:");
        bookDao = new BookDao(db.connectionSource());
        MemberDao memberDao = new MemberDao(db.connectionSource());
        LoanDao loanDao = new LoanDao(db.connectionSource());
        memberService = new MemberService(memberDao, bookDao, loanDao);
    }

    @Test
    void checkoutLeavesNoPartialStateOnFailure() throws Exception {
        Book book = new Book("Clean Code", "Robert C. Martin", "9780132350884", 2);
        bookDao.create(book);

        assertThrows(Exception.class, () -> memberService.checkout(9999, book.getId()));

        Book reloaded = bookDao.findById(book.getId());
        assertEquals(reloaded.getTotalCopies(), reloaded.getAvailableCopies(),
                "availableCopies was decremented even though the loan was never created");
    }
}
