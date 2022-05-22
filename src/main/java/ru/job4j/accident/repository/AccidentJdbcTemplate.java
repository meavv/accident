package ru.job4j.accident.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private int insertMessage(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into accident (name, text, address, type) values (?,?,?,?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        return Integer.parseInt(keyHolder.getKeyList().get(0).get("id").toString());
    }

    private Set<Rule> insertToAccidentRules(String[] ids, int id) {
        Set<Rule> set = new HashSet<>();
        Arrays.stream(ids).forEach(
                a -> {
                    int i = Integer.parseInt(a);
                    set.add(rules.get(i));
                    jdbc.update("insert into accident_rules (id_accident, id_rules) values (?, ?)",
                            id, i);
                }
        );
        return set;
    }

    public Accident add(Accident accident, String[] ids) {
        if (accident.getId() == 0) {
            int id = insertMessage(accident);
            accident.setRules(insertToAccidentRules(ids, id));
        } else {
            jdbc.update("update accident set name = ?, text = ?, address = ?, type = ? where id = ?",
                    accident.getName(),
                    accident.getText(),
                    accident.getAddress(),
                    accident.getType().getId(),
                    accident.getId());
            jdbc.update("delete from accident_rules where id_accident = ?",
                    accident.getId());
            insertToAccidentRules(ids, accident.getId());
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
        jdbc.query("select id, name, text, address, type from accident where id = ?",
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
        accident.setRules(getRuleById(accident.getId()));
    }

    public List<Accident> getAll() {
        return jdbc.query("select id, name, text, address, type from accident",
                (rs, row) -> {
                    Accident accident = new Accident();
                    createAccident(accident, rs);
                    return accident;
                });
    }

    public Set<Rule> getRuleById(int id) {
        List<Rule> ruleList = jdbc.query("select accident.id, accident_rules.id_rules from accident\n" +
                        "join accident_rules on accident.id = accident_rules.id_accident\n" +
                        "join rules on accident_rules.id_rules = rules.id\n" +
                        "where accident.id = ?",
                (rs, row) -> rules.get(rs.getInt("id_rules")), id);
        return new HashSet<>(ruleList);
    }

}