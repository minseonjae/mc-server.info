package kr.codingtree.mcsi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "server_data")
@Data
public class ServerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "server_id")
    private Server server;

    @Column(name = "protocol")
    private int protocol;

    @Column(name = "protocol_name")
    private String protocolName;

    @Column(name = "connect_ping")
    private int connectPing;

    @Column(name = "status_ping")
    private int statusPing;

    @Column(name = "max_players")
    private int maxPlayers;

    @Column(name = "online_players")
    private int onlinePlayers;

    private LocalDateTime createdAt;

}
