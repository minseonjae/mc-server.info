package kr.codingtree.mcsi.repository;

import kr.codingtree.mcsi.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Integer> {

    Optional<Server> findByNameEqualsIgnoreCase(String name);

    Optional<Server> findByOriginalAddressAndSrvPort(String ip, int port);

    int countDistinctByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String name, String address);

    @Query(value = """
        SELECT DISTINCT * FROM server_list
            WHERE LOWER(name) LIKE LOWER(CONCAT('%', :query, '%')) 
                OR LOWER(address) LIKE LOWER(CONCAT('%', :query, '%'))
            LIMIT :limit OFFSET :offset
    """, nativeQuery = true)
    List<Server> searchServersWithPaging(@Param("query") String query, @Param("limit") int limit, @Param("offset") int offset);
}
