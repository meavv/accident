package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> accidentHashMap = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(1);

    public int getCount() {
        return count.get();
    }

    public Accident findById(int id) {
        return accidentHashMap.get(id);
    }

    public void add(int id, Accident accident) {
        accidentHashMap.put(id, accident);
        count.addAndGet(1);
    }

    public boolean containsKey(int id) {
        return accidentHashMap.containsKey(id);
    }

    public Collection<Accident> getAccidentHashMap() {
        return accidentHashMap.values();
    }
}
