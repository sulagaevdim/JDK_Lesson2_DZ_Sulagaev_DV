package client;

public interface ClientView {
    void sendMessage(String message);
    void disconnectFromServer();
    void setClient(Client client);
}