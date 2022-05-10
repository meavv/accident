package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.Accident;

import java.util.LinkedList;
import java.util.List;

@Controller
public class IndexControl {

    @GetMapping("/")
    public String index(Model model) {
        List<Accident> list = new LinkedList<>();

        for (int i = 0; i < 7; i++) {
            Accident a = new Accident();
            a.setId(i);
            a.setName("name" + i);
            a.setAddress("adress" + i);
            a.setText("text" + i);
            list.add(a);
        }

        model.addAttribute("list", list);
        return "index";
    }
}
