package client;

import server.server.Server;
import server.serverView.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private Client client;

    private JTextArea log;
    private JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    private JPasswordField password;
    private JButton btnLogin, btnSend;
    private JPanel headerPanel;

    public ClientGUI(){

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setLocation(500, 100);

        createPanel();
        setVisible(true);
    }

    private void connectToServer(){
        if (client.connectToServer(tfLogin.getText())) {
            hideHeaderPanel(false);
        }
    }

    public void answer(String text){
        appendLog(text);
    }
    private void appendLog(String text){
        log.append(text + "\n");
    }

    private void sendMessage(){
        client.sendMessage(tfMessage.getText());
        tfMessage.setText("");
    }
    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel(){
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField(getRandomName());
        password = new JPasswordField("123456");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }
    String getRandomName() {
        String[] logins = new String[] {"Дмитрий", "Андрей", "Александр", "Наталья", "Мария"};
        Random random = new Random();
        return logins[random.nextInt(0, 5)];
    }

    private Component createLog(){
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n'){
                    sendMessage();
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }

    @Override
    public void sendMessage(String message) {
        appendLog(message);
    }

    @Override
    public void disconnectFromServer() {
        hideHeaderPanel(true);
        client.disconnectFromServer();
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }
    private void hideHeaderPanel(boolean visible){
        headerPanel.setVisible(visible);
    }
}
