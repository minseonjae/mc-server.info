package kr.codingtree.mcsi.util.msp;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class MinecraftServerSimpleResponse implements MinecraftServerResponseInterface {

    private String protocolName;
    private int protocol;
    private int maxPlayers = 0, onlinePlayers = 0;
    private long status_Ping = -1, connect_Ping = -1;
    private JsonNode motd;

}
