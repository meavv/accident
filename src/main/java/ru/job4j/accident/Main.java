package ru.job4j.accident;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.job4j.accident.config.JdbcConfig;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Properties;

public class Main {


    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JdbcConfig jdbcConfig = new JdbcConfig();
        AccidentJdbcTemplate accidentJdbcTemplate = new AccidentJdbcTemplate(
                jdbcConfig.jdbc(
                        jdbcConfig.ds(properties.getProperty("jdbc.driver"),
                                properties.getProperty("jdbc.url"),
                                properties.getProperty("jdbc.username"),
                                properties.getProperty("jdbc.password"))));



        var all = accidentJdbcTemplate.getRuleById(1);
        System.out.println(all);

    }
}
