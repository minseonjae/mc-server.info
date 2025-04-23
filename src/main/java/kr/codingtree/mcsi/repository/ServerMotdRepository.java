package kr.codingtree.mcsi.repository;


import kr.codingtree.mcsi.entity.Server;
import kr.codingtree.mcsi.entity.ServerMotd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerMotdRepository extends JpaRepository<ServerMotd, Integer> {

    List<ServerMotd> findByServerList(Server server);
    List<ServerMotd> findByServerList_Id(int serverId);

    ServerMotd findTopByServerListOrderByCreatedAtDesc(Server server);
    ServerMotd findTopByServerList_IdOrderByCreatedAtDesc(int serverId);

}