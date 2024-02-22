package org.vtorbin.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.vtorbin.dao.PuzzleDao;
import org.vtorbin.model.Puzzle;
import org.vtorbin.util.HibernateUtil;

import java.util.Optional;

public class PuzzleDaoImpl implements PuzzleDao {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public Puzzle add(Puzzle puzzle) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(puzzle);
            return puzzle;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
    @Override
    public Optional<Puzzle> getById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            return Optional.of(session.get(Puzzle.class, id));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Puzzle> getRandom(int elo) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String hql = """
                    from Puzzle p
                    where p.rating - p.deviation <= :elo and :elo <= p.rating + p.deviation
                    ORDER BY RAND()""";
            Query<Puzzle> query = session.createQuery(hql, Puzzle.class);
            return query.setParameter("elo", elo)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void delete(Puzzle puzzle) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(puzzle);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
