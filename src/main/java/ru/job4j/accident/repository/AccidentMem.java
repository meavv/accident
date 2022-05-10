package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;

@Repository
public class AccidentMem {

    private static final HashMap<Integer, Accident> ACCIDENT_HASH_MAP = new HashMap<>();

    public void of(Accident accident) {
        int count = AccidentMem.getAccidentHashMap().size() + 1;
        accident.setId(count);
        ACCIDENT_HASH_MAP.put(count, accident);
    }

    public static HashMap<Integer, Accident> getAccidentHashMap() {
        return ACCIDENT_HASH_MAP;
    }
}
