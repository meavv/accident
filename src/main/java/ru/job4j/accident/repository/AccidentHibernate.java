package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;

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

    public HashMap<Integer, AccidentType> getTypes() {
        return new HashMap<>(types);
    }

    public HashMap<Integer, Rule> getRules() {
        return new HashMap<>(rules);
    }

    private HashMap<Integer, Rule> getRulesBase() {
        HashMap<Integer, Rule> hashMap = new HashMap<>();
        try (Session session = sf.openSession()) {
            List<Rule> list = session
                    .createQuery("from Rule", Rule.class)
                    .list();
            list.forEach(a -> hashMap.put(a.getId(), a));
        }
        return hashMap;
    }

    private HashMap<Integer, AccidentType> getTypesBased() {
        HashMap<Integer, AccidentType> hashMap = new HashMap<>();
        try (Session session = sf.openSession()) {
            List<AccidentType> list = session
                    .createQuery("from AccidentType", AccidentType.class)
                    .list();
            list.forEach(a -> hashMap.put(a.getId(), a));
        }
        return hashMap;
    }

    public Accident save(Accident accident, String[] ids) {
        Set<Rule> set = new HashSet<>();
        try (Session session = sf.openSession()) {
            Arrays.stream(ids).forEach(
                    a -> set.add(rules.get(Integer.parseInt(a)))
            );
            accident.setRules(set);
            session.save(accident);
            System.out.println(accident);
            return accident;
        }
    }

    public Accident findById(int id) {
        try (Session session = sf.openSession()) {
            return session.get(Accident.class, id);
        }
    }

    public List<Accident> getAll() {
        List<Accident> list;
        try (Session session = sf.openSession()) {
            list =  session
                    .createQuery("select distinct a from Accident a join fetch a.type", Accident.class)
                    .getResultList();
        }

        return list;
    }
}