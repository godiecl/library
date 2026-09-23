package cl.ucn.disc.arqsist.library.service;

import cl.ucn.disc.arqsist.library.dao.BookDao;
import cl.ucn.disc.arqsist.library.dao.LoanDao;
import cl.ucn.disc.arqsist.library.dao.MemberDao;
import cl.ucn.disc.arqsist.library.model.Book;
import cl.ucn.disc.arqsist.library.model.Loan;
import cl.ucn.disc.arqsist.library.model.Member;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public final class MemberService {

    private final MemberDao memberDao;
    private final BookDao bookDao;
    private final LoanDao loanDao;

    public MemberService(MemberDao memberDao, BookDao bookDao, LoanDao loanDao) {
        this.memberDao = memberDao;
        this.bookDao = bookDao;
        this.loanDao = loanDao;
    }

    public Member register(Member member) throws SQLException {
        memberDao.create(member);
        return member;
    }

    public List<Member> findAll() throws SQLException {
        return memberDao.findAll();
    }

    public Loan checkout(int memberId, int bookId) throws SQLException {
        Member member = memberDao.findById(memberId);
        Book book = bookDao.findById(bookId);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookDao.update(book);

        String dueDate = LocalDate.now().plusDays(14).toString();
        Loan loan = new Loan(member, book, LocalDate.now().toString(), dueDate);
        loanDao.create(loan);
        return loan;
    }
}
