package kr.codingtree.mcsi.repository;

import kr.codingtree.mcsi.entity.ServerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ServerDataRepository extends JpaRepository<ServerData, Integer> {

    ServerData findTopByServer_IdOrderByCreatedAtDesc(int serverId);

    List<ServerData> findTop20ByServer_IdOrderByCreatedAtDesc(int serverId);

    List<ServerData> findByServer_IdAndCreatedAtAfterOrderByCreatedAtAsc(int serverId, LocalDateTime time);

    List<ServerData> findByServer_IdAndCreatedAtBetweenOrderByCreatedAtDesc(int serverId, LocalDateTime start, LocalDateTime end);
}