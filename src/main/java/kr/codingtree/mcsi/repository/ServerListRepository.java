package kr.codingtree.mcsi.repository;

import kr.codingtree.mcsi.entity.ServerList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerListRepository extends JpaRepository<ServerList, Integer> {

    Optional<ServerList> findByOriginalAddressAndSrvPort(String ip, int port);

    List<ServerList> findDistinctByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String name, String address);

}
