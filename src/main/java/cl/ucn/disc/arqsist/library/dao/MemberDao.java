package cl.ucn.disc.arqsist.library.dao;

import cl.ucn.disc.arqsist.library.model.Member;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public final class MemberDao {

    private final Dao<Member, Integer> dao;

    public MemberDao(ConnectionSource connectionSource) throws SQLException {
        this.dao = DaoManager.createDao(connectionSource, Member.class);
    }

    public List<Member> findAll() throws SQLException {
        return dao.queryForAll();
    }

    public Member findById(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public void create(Member member) throws SQLException {
        dao.create(member);
    }

    public void update(Member member) throws SQLException {
        dao.update(member);
    }
}
