package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        List<String> list = new LinkedList<>();
        list.add("Строка1");
        list.add("Строка2");
        list.add("Строка3");
        list.add("Строка4");
        model.addAttribute("list", list);
        model.addAttribute("count", 0);
        return "index";
    }
}