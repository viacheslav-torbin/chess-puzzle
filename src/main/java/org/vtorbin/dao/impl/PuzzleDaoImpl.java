package org.vtorbin.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.vtorbin.dao.PuzzleDao;
import org.vtorbin.model.Puzzle;
import org.vtorbin.util.HibernateUtil;

import java.util.Optional;

public class PuzzleDaoImpl implements PuzzleDao {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Puzzle add(Puzzle puzzle) {
        try (Session session = sessionFactory.openSession()) {
            session.persist(puzzle);
            return puzzle;
        }
    }
    @Override
    public Optional<Puzzle> getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.get(Puzzle.class, id));
        }
    }

    @Override
    public Optional<Puzzle> getRandom(int elo) {
        try (Session session = sessionFactory.openSession()) {
            String hql = """
                    from Puzzle p
                    where p.rating - p.deviation <= :elo and :elo <= p.rating + p.deviation
                    ORDER BY RAND()""";
            Query<Puzzle> query = session.createQuery(hql, Puzzle.class);
            return query.setParameter("elo", elo)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        }
    }

    @Override
    public void delete(Puzzle puzzle) {
        try (Session session = sessionFactory.openSession()) {
            session.remove(puzzle);
        }
    }
}
