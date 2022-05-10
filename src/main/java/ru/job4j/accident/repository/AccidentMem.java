package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.HashMap;

@Repository
public class AccidentMem {

    private static final HashMap<Integer, Accident> ACCIDENT_HASH_MAP = new HashMap<>();

    public void of(Accident accident) {
        if (!ACCIDENT_HASH_MAP.containsKey(accident.getId())) {
            int count = accident.getId();
            ACCIDENT_HASH_MAP.put(count, accident);
        } else {
            Accident a = ACCIDENT_HASH_MAP.get(accident.getId());
            a.setName(accident.getName());
            a.setAddress(accident.getAddress());
            a.setText(accident.getText());
        }
    }

    public static HashMap<Integer, Accident> getAccidentHashMap() {
        return ACCIDENT_HASH_MAP;
    }
}
