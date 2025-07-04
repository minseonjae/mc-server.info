package kr.codingtree.mcsi.controller;

import kr.codingtree.mcsi.util.SRVResolver;
import kr.codingtree.mcsi.util.msp.MinecraftServerPing;
import kr.codingtree.mcsi.util.msp.MinecraftServerResponseInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiServerController {

    @GetMapping("/getserver/{host}")
    public MinecraftServerResponseInterface getServerStatus(@PathVariable String host) {
        Map.Entry<String, Integer> entry = SRVResolver.lookup(host, 25565);
        MinecraftServerResponseInterface response = MinecraftServerPing.getServerPing(entry.getKey(), entry.getValue(), 1000, 4,  true);
        return response;
    }

    @GetMapping("/getserver/{host}/{port}")
    public MinecraftServerResponseInterface getServerStatus(@PathVariable String host, @PathVariable int port) {
        Map.Entry<String, Integer> entry = SRVResolver.lookup(host, port);
        MinecraftServerResponseInterface response = MinecraftServerPing.getServerPing(entry.getKey(), entry.getValue(), 1000, 4,  true);
        return response;
    }

    @GetMapping("/getserver/{host}/{port}/{protocol}")
    public MinecraftServerResponseInterface getServerStatus(@PathVariable String host, @PathVariable int port, @PathVariable int protocol) {
        Map.Entry<String, Integer> entry = SRVResolver.lookup(host, port);
        MinecraftServerResponseInterface response = MinecraftServerPing.getServerPing(entry.getKey(), entry.getValue(), 1000, 4,  true);
        return response;
    }
}
