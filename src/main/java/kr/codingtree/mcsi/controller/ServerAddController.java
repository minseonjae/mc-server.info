package kr.codingtree.mcsi.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.codingtree.mcsi.entity.ServerList;
import kr.codingtree.mcsi.service.ServerListService;
import kr.codingtree.mcsi.util.SRVResolver;
import kr.codingtree.mcsi.util.msp.MinecraftServerPing;
import kr.codingtree.mcsi.util.msp.MinecraftServerResponseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@Controller
public class ServerAddController {

    @Autowired
    private ServerListService serverListService;

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping("/addserver")
    public String addServer(HttpServletRequest request, RedirectAttributes redirectAttributes, @RequestParam String name, @RequestParam String ip, @RequestParam(required = false, defaultValue = "25565") String port, @RequestParam boolean check) {
        try {
            int serverPort = Integer.parseInt(port);

            if (!check) {
                redirectAttributes.addFlashAttribute("message", "이용약관 및 개인정보취급방침에 동의해주세요!");
            } else {
                Map.Entry<String, Integer> lookup = SRVResolver.lookup(ip, serverPort);

                boolean srv = ip.equals(lookup.getKey()) && serverPort == lookup.getValue();
                String srvAddress = lookup.getKey();
                int srvPort = lookup.getValue();
                InetAddress addr = InetAddress.getByName(srvAddress);
                String orignalAddress = addr.getHostAddress();

                if (addr.isLoopbackAddress() || addr.isSiteLocalAddress() || addr.isLinkLocalAddress() || addr.isAnyLocalAddress() || addr.isMulticastAddress()) {
                    redirectAttributes.addFlashAttribute("message", "해당 주소로는 하실 수 없습니다!");
                } else {
                    ServerList serverList = serverListService.getServerList(ip, serverPort).orElse(null);

                    if (serverList != null) {
                        redirectAttributes.addFlashAttribute("message", "이미 서버가 등록되어 있습니다. (" + serverList.getAddress() + "[" + serverList.getName() + "])");
                    } else {
                        MinecraftServerResponseInterface minecraftServerResponse = MinecraftServerPing.getServerPing(srvAddress, srvPort, 200, 4, true);

                        if (minecraftServerResponse != null) {
                            serverList = new ServerList();

                            serverList.setName(name);
                            serverList.setAddress(ip);
                            serverList.setPort(serverPort);
                            serverList.setOriginalAddress(orignalAddress);
                            serverList.setSrv(srv);
                            serverList.setSrvAddress(srvAddress);
                            serverList.setSrvPort(srvPort);
                            serverList.setRankingBan(false);
                            serverList.setCreatedAddress(getClientIp(request));

                            serverListService.save(serverList);
                            redirectAttributes.addFlashAttribute("message", "성공적으로 서버를 등록했습니다.");
                        } else {
                            redirectAttributes.addFlashAttribute("message", "서버가 오프라인이거나 연결 할 수 없는 상태입니다.");
                        }
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "알 수 없는 오류로 인해 추가하지 못했습니다.");
        } catch (NumberFormatException nfe) {
            redirectAttributes.addFlashAttribute("message", "서버 포트는 숫자만 입력이 가능합니다!");
        }
        return "redirect:/add";
    }


    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown")) {
            return ip.split(",")[0].trim();
        }

        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !ip.equalsIgnoreCase("unknown")) {
            return ip;
        }

        ip = request.getRemoteAddr();

        if (ip.equals("0:0:0:0:0:0:0:1") || "::1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }
}
