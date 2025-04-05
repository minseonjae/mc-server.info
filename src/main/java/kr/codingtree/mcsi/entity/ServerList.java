package kr.codingtree.mcsi.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "server_list")
@Data
public class ServerList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 16)
    private String name;

    @Column(length = 64)
    private String address;

    private int port;

    private boolean srv;

    @Column(name = "srv_address", length = 64)
    private String srvAddress;

    @Column(name = "srv_port")
    private int srvPort;

    @Column(name = "original_address", length = 64)
    private String originalAddress;

    @Column(name = "ranking_ban")
    private boolean rankingBan;

    @Column(name = "created_address", length = 64)
    private String createdAddress;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
