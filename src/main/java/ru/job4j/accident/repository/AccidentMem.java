package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

// @Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> accidentHashMap = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private final HashMap<Integer, AccidentType> types = new HashMap<>();
    private final HashMap<Integer, Rule> rules = new HashMap<>();

    public AccidentMem() {
        rules.put(1, Rule.of(1, "Статья. 1"));
        rules.put(2, Rule.of(2, "Статья. 2"));
        rules.put(3, Rule.of(3, "Статья. 3"));
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2, AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        for (int i = 1; i < 4; i++) {
            Accident accident = new Accident();
            accident.setText("text" + i);
            accident.setAddress("address" + i);
            accident.setName("name" + i);
            accident.setType(types.get(i));
            String[] s = new String[]{String.valueOf(i)};
            add(accident, s);
        }
    }

    public void add(Accident accident, String[] ids) {
        Set<Rule> set = new HashSet<>();
        Arrays.stream(ids).forEach(
                a -> set.add(rules.get(Integer.parseInt(a)))
        );

        accident.setRules(set);
        accident.setType(types.get(accident.getType().getId()));

        if (accident.getId() == 0) {
            int id = count.addAndGet(1);
            accident.setId(id);
            accidentHashMap.put(id, accident);
        } else {
            accidentHashMap.replace(accident.getId(), accident);
        }
    }

    public HashMap<Integer, AccidentType> getTypes() {
        return types;
    }

    public Accident findById(int id) {
        return accidentHashMap.get(id);
    }

    public Collection<Accident> getAccidentHashMap() {
        return accidentHashMap.values();
    }

    public HashMap<Integer, Rule>  getRules() {
        return rules;
    }
}
