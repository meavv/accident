package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.AccidentRepositoryRules;
import ru.job4j.accident.repository.AccidentRepositoryTypes;

import java.util.*;

@Service
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentRepositoryRules accidentRepositoryRules;
    private final AccidentRepositoryTypes accidentRepositoryTypes;
    private final HashMap<Integer, AccidentType> types;
    private final HashMap<Integer, Rule> rules;

    public AccidentService(AccidentRepository accidentRepository,
                           AccidentRepositoryRules accidentRepositoryRules,
                           AccidentRepositoryTypes accidentRepositoryTypes) {
        this.accidentRepository = accidentRepository;
        this.accidentRepositoryRules = accidentRepositoryRules;
        this.accidentRepositoryTypes = accidentRepositoryTypes;
        types = getTypes();
        rules = getRules();
    }

    public void of(Accident accident, String[] ids) {
        Set<Rule> set = new HashSet<>();
        Arrays.stream(ids).forEach(
                a -> set.add(rules.get(Integer.parseInt(a)))
        );
        accident.setRules(set);
        accidentRepository.save(accident);
    }

    public List<Accident> getAccidentList() {
        List<Accident> res = new ArrayList<>(accidentRepository.getList());
        return new ArrayList<>(res);
    }

    public HashMap<Integer, AccidentType> types() {
        return new HashMap<>(types);
    }

    public HashMap<Integer, Rule> rules() {
        return new HashMap<>(rules);
    }


    private HashMap<Integer, AccidentType> getTypes() {
        HashMap<Integer, AccidentType> types = new HashMap<>();
        accidentRepositoryTypes.findAll().forEach(a -> types.put(a.getId(), a));
        return new HashMap<>(types);
    }

    public Accident findById(int id) {
        return accidentRepository.getById(id);
    }

    private HashMap<Integer, Rule> getRules() {
        HashMap<Integer, Rule> rules = new HashMap<>();
        accidentRepositoryRules.findAll().forEach(a -> rules.put(a.getId(), a));
        return new HashMap<>(rules);
    }

}
