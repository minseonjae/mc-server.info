package kr.codingtree.mcsi.repository;

import kr.codingtree.mcsi.entity.ServerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ServerDataRepository extends JpaRepository<ServerData, Integer> {

    ServerData findTopByServerList_IdOrderByCreatedAtDesc(int serverId);

    List<ServerData> findTop20ByServerList_IdOrderByCreatedAtDesc(int serverId);

    List<ServerData> findByServerList_IdAndCreatedAtAfterOrderByCreatedAtAsc(int serverId, LocalDateTime time);

    List<ServerData> findByServerList_IdAndCreatedAtBetween(int serverId, LocalDateTime start, LocalDateTime end);
}