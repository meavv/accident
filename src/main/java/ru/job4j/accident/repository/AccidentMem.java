package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> accidentHashMap = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);

    public AccidentMem() {
        for (int i = 1; i < 5; i++) {
            Accident accident = new Accident();
            accident.setText("text" + i);
            accident.setAddress("address" + i);
            accident.setName("name" + i);
            System.out.println(accident);
            add(accident);
        }
    }

    public void add(Accident accident) {
        if (accident.getId() == 0) {
            int id = count.addAndGet(1);
            accident.setId(id);
            accidentHashMap.put(id, accident);
        } else {
            accidentHashMap.replace(accident.getId(), accident);
        }
    }

    public Accident findById(int id) {
        return accidentHashMap.get(id);
    }

    public Collection<Accident> getAccidentHashMap() {
        return accidentHashMap.values();
    }
}
