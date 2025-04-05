package kr.codingtree.mcsi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "server_motd")
@Data
public class ServerMotd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "server_id")
    private ServerList serverList;

    @Column(columnDefinition = "json")
    private String motd;

    private LocalDateTime createdAt;

}