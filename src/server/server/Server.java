package server.server;

import client.Client;
import client.ClientGUI;
import server.repository.Repositorable;
import server.repository.Repository;
import server.serverView.ServerView;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private boolean work;
    private List<Client> clientList;
    private ServerView view;
    private Repositorable<String> repository;


    public Server(ServerView view, Repositorable<String> repository) {
        clientList = new ArrayList<>();
        this.view = view;
        this.repository = repository;
        view.setServer(this);
    }

    public void disconnectUser(Client client){
        clientList.remove(client);
        if (client != null){
            client.disconnectFromServer();
        }
    }
    public boolean connectUser(Client client){
        if (!work){
            return false;
        }
        clientList.add(client);
        return true;
    }
    public void startServer() {
        if (work){
            appendLog("Сервер уже был запущен");
        } else {
            work = true;
            appendLog("Сервер запущен!");
        }
    }
    public void stopServer() {
        if (!work){
            appendLog("Сервер уже был остановлен");
        } else {
            work = false;
            while (!clientList.isEmpty()){
                disconnectUser(clientList.get(clientList.size()-1));
            }
            appendLog("Сервер остановлен!");
        }
    }
    public void sendMessage(String text){
        if (!work){
            return;
        }
        text += "";
        appendLog(text);
        answerAll(text);
        saveInLog(text);
    }
    private void answerAll(String text){
        for (Client client: clientList){
            client.serverAnswer(text);
        }
    }
    private void saveInLog(String text){
        repository.saveInLog(text);
    }

    private void appendLog(String text){
        view.sendMessage(text + "\n");
    }
    public String getHistory() {
        return repository.readLog();
    }
    public Server getServer(){
        return this;
    }
    public String getLog() {
        return repository.readLog();
    }
}
