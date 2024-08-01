package client;

import server.server.Server;

public class Client{
    private Server server;
    private boolean connected;
    private String name;
    private ClientView view;

    public Client(ClientView view, Server server){
        this.view = view;
        this.server = server;
        view.setClient(this);
    }

    public String getName() {
        return name;
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)){
            printText("Вы успешно подключились!\n");
            connected = true;
            String log = server.getHistory();
            if (log != null){
                printText(log);
            }
        } else {
            printText("Подключение не удалось");
        }
        return false;
    }
    public void disconnectFromServer() {
        if (connected) {
            connected = false;
            view.disconnectFromServer();
            server.disconnectUser(this);
            printText("Вы были отключены от сервера!");
        }
    }
    public void sendMessage(String message){
        if (connected){
            if (!message.isEmpty()){
                server.sendMessage(name + ": " + message);
            }
        } else {
            printText("Нет подключения к серверу");
        }
    }
    public void serverAnswer(String answer) {
        printText(answer);
    }
    private void printText(String text){
        view.sendMessage(text);
    }
}
