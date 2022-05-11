package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccidentService {

    private static final AccidentMem ACCIDENT_MEM = new AccidentMem();

    public void of(Accident accident) {

        if (!ACCIDENT_MEM.containsKey(accident.getId())) {
            accident.setId(ACCIDENT_MEM.getCount());
            ACCIDENT_MEM.add(accident.getId(), accident);
        } else {
            Accident a = ACCIDENT_MEM.findById(accident.getId());
            a.setName(accident.getName());
            a.setAddress(accident.getAddress());
            a.setText(accident.getText());
            ACCIDENT_MEM.add(accident.getId(), a);
        }
    }

    public List<Accident> getAccidentList() {
        return new ArrayList<>(ACCIDENT_MEM.getAccidentHashMap());
    }

}
