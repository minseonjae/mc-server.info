package kr.codingtree.mcsi.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.codingtree.mcsi.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ServerAddController {

    @Autowired
    private ServerService service;

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping("/addserver")
    public String addServer(HttpServletRequest request, RedirectAttributes attributes, @RequestParam String name, @RequestParam String ip, @RequestParam(required = false, defaultValue = "25565") String port, @RequestParam boolean check) {
        return service.registerServerFromWeb(request, attributes, name, ip, port, check);
    }

}
