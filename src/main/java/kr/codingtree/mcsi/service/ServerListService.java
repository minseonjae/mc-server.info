package kr.codingtree.mcsi.service;

import kr.codingtree.mcsi.entity.ServerList;
import kr.codingtree.mcsi.repository.ServerListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServerListService {

    @Autowired
    private ServerListRepository repository;

    public Optional<ServerList> getServerList(String ip, int port) {
        return repository.findByOriginalAddressAndSrvPort(ip, port);
    }
    public boolean save(ServerList serverList) {
        if (getServerList(serverList.getOriginalAddress(), serverList.getSrvPort()).isPresent()) {
            return false;
        }
        repository.save(serverList);
        return false;
    }
}
