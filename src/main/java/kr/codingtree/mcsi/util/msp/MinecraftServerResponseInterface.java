package kr.codingtree.mcsi.util.msp;

import com.fasterxml.jackson.databind.JsonNode;

public interface MinecraftServerResponseInterface {

    String getProtocolName();
    void setProtocolName(String protocolName);

    int getProtocol();
    void setProtocol(int protocol);

    int getMaxPlayers();
    void setMaxPlayers(int maxPlayers);

    int getOnlinePlayers();
    void setOnlinePlayers(int onlinePlayers);

    long getStatus_Ping();
    void setStatus_Ping(long status_Ping);

    long getConnect_Ping();
    void setConnect_Ping(long connect_Ping);

    JsonNode getMotd();
    void setMotd(JsonNode motd);


    default String motdToHTML() {
        StringBuilder sb = new StringBuilder();

        if (getMotd() != null && getMotd().isArray()) {
            for (JsonNode jsonNode : getMotd()) {
                String colorCode = null;

                if (jsonNode.get("bold") != null && jsonNode.get("bold").asBoolean()) {
                    sb.append("<b>");
                }
                if (jsonNode.get("italic") != null && jsonNode.get("italic").asBoolean()) {
                    sb.append("<i>");
                }
                if (jsonNode.get("underlined") != null && jsonNode.get("underlined").asBoolean()) {
                    sb.append("<u>");
                }
                if (jsonNode.get("strikethrough") != null && jsonNode.get("strikethrough").asBoolean()) {
                    sb.append("<s>");
                }

                if (jsonNode.get("color") != null && jsonNode.get("color").isTextual()) {
                    String color = jsonNode.get("color").asText().toLowerCase();

                    switch (color) {
                        case "black":
                            colorCode = "000000";
                            break;
                        case "dark_blue":
                            colorCode = "0000AA";
                            break;
                        case "dark_green":
                            colorCode = "00AA00";
                            break;
                        case "dark_aqua":
                            colorCode = "00AAAA";
                            break;
                        case "dark_red":
                            colorCode = "AA0000";
                            break;
                        case "dark_purple":
                            colorCode = "AA00AA";
                            break;
                        case "gold":
                            colorCode = "FFAA00";
                            break;
                        case "gray":
                            colorCode = "AAAAAA";
                            break;
                        case "dark_gray":
                            colorCode = "555555";
                            break;
                        case "blue":
                            colorCode = "5555FF";
                            break;
                        case "green":
                            colorCode = "55FF55";
                            break;
                        case "aqua":
                            colorCode = "55FFFF";
                            break;
                        case "red":
                            colorCode = "FF5555";
                            break;
                        case "light_purple":
                            colorCode = "FF55FF";
                            break;
                        case "yellow":
                            colorCode = "FBD500";
                            break;
                        case "white":
                            colorCode = "FFFFFF";
                            break;
                    }

                    if (colorCode != null) {
                        sb.append("<font color=\"#" + colorCode + "\">");
                    }
                }

                if (jsonNode.get("text").isTextual()) {
                    String text = jsonNode.get("text").asText().replace("\n", "<br>").replace("\t", "&nbsp;&nbsp;&nbsp;").replace(" ", "&nbsp;");
                    sb.append(text);
                }

                if (colorCode != null) {
                    sb.append("</font>");
                }

                if (jsonNode.get("strikethrough") != null && jsonNode.get("strikethrough").asBoolean()) {
                    sb.append("</s>");
                }
                if (jsonNode.get("underlined") != null && jsonNode.get("underlined").asBoolean()) {
                    sb.append("</u>");
                }
                if (jsonNode.get("italic") != null && jsonNode.get("italic").asBoolean()) {
                    sb.append("</i>");
                }
                if (jsonNode.get("bold") != null && jsonNode.get("bold").asBoolean()) {
                    sb.append("</b>");
                }
            }
        }

        return sb.toString();
    }
}
