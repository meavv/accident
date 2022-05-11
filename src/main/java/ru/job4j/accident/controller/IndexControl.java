package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexControl {

    @GetMapping("/")
    public String index(Model model) {
        AccidentService accidentService = new AccidentService();
        List<Accident> list = accidentService.getAccidentList();
        model.addAttribute("list", list);
        return "index";
    }
}
