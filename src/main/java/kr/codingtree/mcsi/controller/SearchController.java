package kr.codingtree.mcsi.controller;

import kr.codingtree.mcsi.entity.ServerList;
import kr.codingtree.mcsi.repository.ServerListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ServerListRepository serverListRepository;

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String query, Model model) {
        if (query != null && !query.trim().isEmpty()) {
            List<ServerList> serverList = serverListRepository.findDistinctByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(query, query);
            model.addAttribute("serverList", serverList);
        }
        return "search";
    }
}