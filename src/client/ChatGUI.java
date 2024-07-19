package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import chat.ChatException;
import chat.ChatFactory;
import chat.MessageContainer;
import chat.Sender;

public class ChatGUI extends JFrame {
    private static final String KEY_TO_EXIT = "q";
    private static final int RECEIVER_BUFFER_SIZE = 1000;

    private JTextField localPortField;
    private JTextField serverPortField;
    private JTextField nameField;
    private JTextArea chatArea;
    private JTextField messageField;
    private Sender sender;
    private String from;

    public ChatGUI() {
        setTitle("Chat Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Porta local:"));
        localPortField = new JTextField();
        inputPanel.add(localPortField);

        inputPanel.add(new JLabel("Porta remota:"));
        serverPortField = new JTextField();
        inputPanel.add(serverPortField);

        inputPanel.add(new JLabel("Nome:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton connectButton = new JButton("Conectar");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
            }
        });

        JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(messageField, BorderLayout.SOUTH);
        panel.add(connectButton, BorderLayout.WEST);
        panel.add(sendButton, BorderLayout.EAST);

        add(panel);
    }

    private void connect() {
        int localPort;
        int serverPort;
        try {
            localPort = Integer.parseInt(localPortField.getText());
            serverPort = Integer.parseInt(serverPortField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira números válidos para as portas.");
            return;
        }

        from = nameField.getText();
        if (from.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um nome.");
            return;
        }

        try {
            sender = ChatFactory.build("localhost", serverPort, localPort, new GUIContainer(chatArea));
            JOptionPane.showMessageDialog(this, "Conectado com sucesso.");
        } catch (ChatException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText();

        if (message.equals(KEY_TO_EXIT)) {
            System.exit(0);
        }

        if (!message.isEmpty() && sender != null) {
            String formattedMessage = String.format("%s%s%s", from, MessageContainer.FROM, message);
            try {
                sender.send(formattedMessage);
                messageField.setText("");
                chatArea.append(formattedMessage + "\n"); // Adiciona a mensagem enviada ao chat do remetente
            } catch (ChatException e) {
                JOptionPane.showMessageDialog(this, "Erro ao enviar mensagem: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatGUI().setVisible(true);
            }
        });
    }

    private static class GUIContainer implements MessageContainer {
        private JTextArea chatArea;

        public GUIContainer(JTextArea chatArea) {
            this.chatArea = chatArea;
        }

        @Override
        public void newMessage(String message) {
            if (message == null || message.equals("")) {
                return;
            }
            chatArea.append(message + "\n"); // Adiciona a mensagem recebida ao chat
        }
    }
}
