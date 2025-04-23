package kr.codingtree.mcsi.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.codingtree.mcsi.entity.Server;
import kr.codingtree.mcsi.repository.ServerRepository;
import kr.codingtree.mcsi.util.SRVResolver;
import kr.codingtree.mcsi.util.msp.MinecraftServerPing;
import kr.codingtree.mcsi.util.msp.MinecraftServerResponseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServerService {

    @Autowired
    private ServerRepository repository;

    public boolean existsServerByName(String name) {
        return getServer(name).isPresent();
    }
    public boolean existsServer(Server server) {
        return existsServer(server.getOriginalAddress(), server.getSrvPort());
    }
    public boolean existsServer(String ip, int port) {
        return getServer(ip, port).isPresent();
    }

    public Optional<Server> getServer(String name) {
        return repository.findByNameEqualsIgnoreCase(name);
    }
    public Optional<Server> getServer(String ip, int port) {
        return repository.findByOriginalAddressAndSrvPort(ip, port);
    }

    public void save(Server server) {
        repository.save(server);
    }

    public String searchServerFromWeb(String query, Model model) {
        if (query != null && !query.trim().isEmpty()) {
            List<Server> serverList = repository.findDistinctByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(query, query);
            model.addAttribute("serverList", serverList);
        }
        return "search";
    }

    public String registerServerFromWeb(HttpServletRequest request, RedirectAttributes attributes, @RequestParam String name, @RequestParam String ip, @RequestParam(required = false, defaultValue = "25565") String port, @RequestParam boolean check) {
        try {
            int serverPort = Integer.parseInt(port);

            if (!check) {
                attributes.addFlashAttribute("message", "이용약관 및 개인정보취급방침에 동의해주세요!");
            } else {
                Map.Entry<String, Integer> lookup = SRVResolver.lookup(ip, serverPort);

                boolean srv = ip.equals(lookup.getKey()) && serverPort == lookup.getValue();
                String srvAddress = lookup.getKey();
                int srvPort = lookup.getValue();
                InetAddress addr = InetAddress.getByName(srvAddress);
                String orignalAddress = addr.getHostAddress();

                if (addr.isLoopbackAddress() || addr.isSiteLocalAddress() || addr.isLinkLocalAddress() || addr.isAnyLocalAddress() || addr.isMulticastAddress()) {
                    attributes.addFlashAttribute("message", "해당 주소로는 하실 수 없습니다!");
                } else {
                    Server serverList = getServer(orignalAddress, srvPort).orElse(null);

                    if (serverList != null) {
                        attributes.addFlashAttribute("message", "이미 서버가 등록되어 있습니다. (" + serverList.getAddress() + "[" + serverList.getName() + "])");
                    } else {
                        MinecraftServerResponseInterface minecraftServerResponse = MinecraftServerPing.getServerPing(srvAddress, srvPort, 200, 4, true);

                        if (minecraftServerResponse != null) {
                            serverList = new Server();

                            serverList.setName(name);
                            serverList.setAddress(ip);
                            serverList.setPort(serverPort);
                            serverList.setOriginalAddress(orignalAddress);
                            serverList.setSrv(srv);
                            serverList.setSrvAddress(srvAddress);
                            serverList.setSrvPort(srvPort);
                            serverList.setRankingBan(false);
                            serverList.setCreatedAddress(getClientIp(request));

                            save(serverList);
                            attributes.addFlashAttribute("message", "성공적으로 서버를 등록했습니다.");
                        } else {
                            attributes.addFlashAttribute("message", "서버가 오프라인이거나 연결 할 수 없는 상태입니다.");
                        }
                    }
                }
            }
        } catch (UnknownHostException e) {
            attributes.addFlashAttribute("message", "알 수 없는 오류로 인해 추가하지 못했습니다.");
        } catch (NumberFormatException nfe) {
            attributes.addFlashAttribute("message", "서버 포트는 숫자만 입력이 가능합니다!");
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
