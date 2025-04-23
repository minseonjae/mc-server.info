package kr.codingtree.mcsi.repository;

import kr.codingtree.mcsi.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Integer> {

    Optional<Server> findByNameEqualsIgnoreCase(String name);

    Optional<Server> findByOriginalAddressAndSrvPort(String ip, int port);

    List<Server> findDistinctByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String name, String address);

}
