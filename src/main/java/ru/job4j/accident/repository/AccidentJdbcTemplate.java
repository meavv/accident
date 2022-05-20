package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    private final HashMap<Integer, AccidentType> types;
    private final HashMap<Integer, Rule> rules;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        types = getTypesBased();
        rules = getRulesBase();
    }

    public Accident add(Accident accident, String[] ids) {
        System.out.println(accident);
        if (accident.getId() == 0) {
            jdbc.update("insert into accident (name, text, address, type, rules) values (?,?,?,?,?)",
                    accident.getName(),
                    accident.getText(),
                    accident.getAddress(),
                    accident.getType().getId(),
                    ids);
        } else {
            jdbc.update("update accident set name = ?, text = ?, address = ?, type = ?, rules = ? where id = ?",
                    accident.getName(),
                    accident.getText(),
                    accident.getAddress(),
                    accident.getType().getId(),
                    ids,
                    accident.getId());
        }
        return accident;
    }

    private HashMap<Integer, AccidentType> getTypesBased() {
        HashMap<Integer, AccidentType> hashMap = new HashMap();
        jdbc.query("select id, name from types",
                (rs) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    hashMap.put(accidentType.getId(), accidentType);
                });
        return hashMap;
    }

    public HashMap<Integer, AccidentType> getTypes() {
        return new HashMap<>(types);
    }

    public HashMap<Integer, Rule> getRules() {
        return new HashMap<>(rules);
    }

    private HashMap<Integer, Rule> getRulesBase() {
        HashMap<Integer, Rule> hashMap = new HashMap();
        jdbc.query("select id, name from rules",
                (rs) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    hashMap.put(rule.getId(), rule);
                });
        return hashMap;
    }

    public Accident findById(int id) {
        Accident accident = new Accident();
        jdbc.query("select id, name, text, address, type, rules from accident where id = ?",
                resultSet -> {
                    createAccident(accident, resultSet);
                }, id);
        return accident;
    }

    private void createAccident(Accident accident, ResultSet resultSet) throws SQLException {
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setText(resultSet.getString("text"));
        accident.setAddress(resultSet.getString("address"));
        accident.setType(types.get(resultSet.getInt("type")));
        var array = resultSet.getArray("rules").getArray();
        String[] stringValues = (String[]) array;
        Set<Rule> set = new HashSet<>();
        Arrays.stream(stringValues).forEach(
                a -> set.add(rules.get(Integer.parseInt(a.replace("'", ""))))
        );
        accident.setRules(set);
    }


    public List<Accident> getAll() {
        return jdbc.query("select id, name, text, address, type, rules from accident",
                (rs, row) -> {
                    Accident accident = new Accident();
                    createAccident(accident, rs);
                    return accident;
                });
    }
}