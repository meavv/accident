package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.function.Function;

@Repository
public class AccidentHibernate {

    private final SessionFactory sf;

    private final HashMap<Integer, AccidentType> types;
    private final HashMap<Integer, Rule> rules;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
        types = getTypesBased();
        rules = getRulesBase();
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public HashMap<Integer, AccidentType> getTypes() {
        return new HashMap<>(types);
    }

    public HashMap<Integer, Rule> getRules() {
        return new HashMap<>(rules);
    }

    private HashMap<Integer, Rule> getRulesBase() {
        HashMap<Integer, Rule> hashMap = new HashMap<>();
        tx(session -> session
                .createQuery("from Rule", Rule.class)
                .list())
                .forEach(a -> hashMap.put(a.getId(), a));
        return hashMap;
    }

    private HashMap<Integer, AccidentType> getTypesBased() {
        HashMap<Integer, AccidentType> hashMap = new HashMap<>();
        tx(session -> session
                .createQuery("from AccidentType", AccidentType.class)
                .list())
                .forEach(a -> hashMap.put(a.getId(), a));
        return hashMap;
    }

   public Accident save(Accident accident, String[] ids) {
        Set<Rule> set = new HashSet<>();
        Arrays.stream(ids).forEach(
                a -> set.add(rules.get(Integer.parseInt(a)))
        );
        accident.setRules(set);
        try (Session session = sf.openSession()) {
            Transaction t = session.beginTransaction();
            session.saveOrUpdate(accident);
            t.commit();
        }
        return accident;
    }

    public Accident findById(int id) {
        return tx(session -> (session.get(Accident.class, id)));
    }

    public List<Accident> getAll() {
        return tx(session -> session
                .createQuery("select distinct a from Accident a join fetch a.type join fetch a.rules", Accident.class)
                .list());
    }
}