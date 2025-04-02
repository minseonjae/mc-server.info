package kr.codingtree.mcsi.util.msp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

@Data
public class MinecraftServerResponse implements MinecraftServerResponseInterface {

    private String protocolName;
    private int protocol;
    private int maxPlayers = 0, onlinePlayers = 0;
    private long status_Ping = -1, connect_Ping = -1;
    private JsonNode motd;
    private String favicon = "";


    public static MinecraftServerResponseInterface fromJson(String json, boolean simple) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        MinecraftServerResponseInterface result = simple ? new MinecraftServerSimpleResponse() : new MinecraftServerResponse();

        JsonNode version = root.path("version");
        result.setProtocolName(version.path("name").asText());
        result.setProtocol(version.path("protocol").asInt());

        JsonNode players = root.path("players");
        result.setMaxPlayers(players.path("max").asInt());
        result.setOnlinePlayers(players.path("online").asInt());

        if (!simple) {
            ((MinecraftServerResponse) result).setFavicon(root.path("favicon").asText());
        }

        JsonNode description = root.path("description");

        if (description.has("text")) {
            result.setMotd(description.path("text"));
        }
        if (result.getMotd().size() < 1 && description.has("extra")) {
            result.setMotd(description.path("extra"));
        }

        return result;
    }
}
