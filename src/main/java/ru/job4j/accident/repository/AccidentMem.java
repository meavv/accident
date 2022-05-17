package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final HashMap<Integer, Accident> accidentHashMap = new HashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private final HashMap<Integer, AccidentType> types = new HashMap<>();


    public AccidentMem() {
        types.put(1, AccidentType.of(1, "Две машины"));
        types.put(2 ,AccidentType.of(2, "Машина и человек"));
        types.put(3, AccidentType.of(3, "Машина и велосипед"));
        for (int i = 1; i < 4; i++) {
            Accident accident = new Accident();
            accident.setText("text" + i);
            accident.setAddress("address" + i);
            accident.setName("name" + i);
            accident.setType(types.get(i));
            System.out.println(accident);
            add(accident);
        }
    }

    public void add(Accident accident) {
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
}
