package server.serverView;
import server.server.Server;
public interface ServerView {
    void sendMessage(String message);
    void setServer(Server server);
}
