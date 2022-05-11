package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentMem;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccidentService {

    private AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public void of(Accident accident) {
        accidentMem.add(accident);
    }

    public List<Accident> getAccidentList() {
        return new ArrayList<>(accidentMem.getAccidentHashMap());
    }

}