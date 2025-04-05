package kr.codingtree.mcsi.repository;


import kr.codingtree.mcsi.entity.ServerList;
import kr.codingtree.mcsi.entity.ServerMotd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerMotdRepository extends JpaRepository<ServerMotd, Integer> {

    List<ServerMotd> findByServerList(ServerList serverList);
    List<ServerMotd> findByServerList_Id(int serverId);

    ServerMotd findTopByServerListOrderByCreatedAtDesc(ServerList serverList);
    ServerMotd findTopByServerList_IdOrderByCreatedAtDesc(int serverId);

}