package kr.codingtree.mcsi.controller;

import kr.codingtree.mcsi.entity.Server;
import kr.codingtree.mcsi.repository.ServerRepository;
import kr.codingtree.mcsi.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ServerService service;

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String query, Model model) {
        return service.searchServerFromWeb(query, model);
    }
}