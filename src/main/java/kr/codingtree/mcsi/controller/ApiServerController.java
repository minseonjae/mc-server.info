package kr.codingtree.mcsi.controller;

import kr.codingtree.mcsi.util.msp.MinecraftServerPing;
import kr.codingtree.mcsi.util.msp.MinecraftServerResponseInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiServerController {

    @GetMapping("/getserver/{host}")
    public MinecraftServerResponseInterface getServerStatus(@PathVariable String host) {
        MinecraftServerResponseInterface response = MinecraftServerPing.getServerPing(host, 25565, 1000, 4,  true);
        return response;
    }

    @GetMapping("/getserver/{host}/{port}")
    public MinecraftServerResponseInterface getServerStatus(@PathVariable String host, @PathVariable int port) {
        MinecraftServerResponseInterface response = MinecraftServerPing.getServerPing(host, port, 1000, 4, false);
        return response;
    }

    @GetMapping("/getserver/{host}/{port}/{protocol}")
    public MinecraftServerResponseInterface getServerStatus(@PathVariable String host, @PathVariable int port, @PathVariable int protocol) {
        MinecraftServerResponseInterface response = MinecraftServerPing.getServerPing(host, port, 1000, protocol, false);
        return response;
    }
}
