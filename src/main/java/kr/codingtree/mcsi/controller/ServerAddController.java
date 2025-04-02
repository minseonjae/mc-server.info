package kr.codingtree.mcsi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ServerAddController {

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping("/addserver")
    public String addServer(@RequestParam String name, @RequestParam String hostname, @RequestParam int port, @RequestParam boolean check) {
        return "add";
    }
}
