package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.repository.AccidentMem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AccidentService {

    private AccidentJdbcTemplate accidentMem;

    public AccidentService(AccidentJdbcTemplate accidentMem) {
        this.accidentMem = accidentMem;
    }

    public void of(Accident accident, String[] ids) {
        accidentMem.add(accident, ids);
    }

    public HashMap<Integer, AccidentType> getTypes() {
        return accidentMem.getTypes();
    }


    public List<Accident> getAccidentList() {
        return new ArrayList<>(accidentMem.getAll());
    }

    public Accident findById(int id) {
        return accidentMem.findById(id);
    }

    public HashMap<Integer, Rule>  rules() {
        return accidentMem.getRules();
    }

}
