import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private JFrame frame = new JFrame("Client");
    private JTextArea chatArea = new JTextArea(20, 50);
    private JTextField inputField = new JTextField(40);
    private JButton sendButton = new JButton("Send");

    public ClientGUI(String host, int port) {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            buildGUI();

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        chatArea.append(msg + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildGUI() {
        chatArea.setEditable(false);
        frame.setLayout(new BorderLayout());

        JPanel bottom = new JPanel();
        bottom.add(inputField);
        bottom.add(sendButton);

        sendButton.addActionListener(e -> {
            writer.println("ME(Client): " + inputField.getText());
            inputField.setText("");
        });

        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

