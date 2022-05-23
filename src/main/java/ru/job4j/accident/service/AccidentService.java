package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentHibernate;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AccidentService {

    private AccidentHibernate accidentHibernate;

    public AccidentService(AccidentHibernate accidentMem) {
        this.accidentHibernate = accidentMem;
    }

    public void of(Accident accident, String[] ids) {
        accidentHibernate.save(accident, ids);
    }

    public List<Accident> getAccidentList() {
        var s = accidentHibernate.getAll();

        return new ArrayList<>(s);
    }

    public HashMap<Integer, AccidentType> getTypes() {
        return accidentHibernate.getTypes();
    }

     public Accident findById(int id) {
        return accidentHibernate.findById(id);
    }

    public HashMap<Integer, Rule>  rules() {
        return accidentHibernate.getRules();
    }

}
