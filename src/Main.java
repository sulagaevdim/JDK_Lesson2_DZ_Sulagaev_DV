import client.Client;
import client.ClientGUI;
import server.repository.Repository;
import server.server.Server;
import server.serverView.ServerWindow;

public class Main {
    public static void main(String[] args) {

        Server server = new Server(new ServerWindow(), new Repository());
        new Client(new ClientGUI(), server);
        new Client(new ClientGUI(), server);
    }
}
