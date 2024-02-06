import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.util.HashSet;

public class GUI {

    public JFrame frame;
    private JTextField portTextField;
    public JButton startButton;
    public JButton stopButton;
    public JTextArea textArea;
    private TCPserver server;


    public GUI() {
        initialize();
    }
//窗口界面初始化方法
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());

        portTextField = new JTextField();
        portTextField.setColumns(10);
        frame.getContentPane().add(portTextField);
        portTextField.setText("12000");

        startButton = new JButton("启动 Server");
        frame.getContentPane().add(startButton);

        stopButton = new JButton("停止 Server");
        stopButton.setEnabled(false); // initially disabled
        frame.getContentPane().add(stopButton);

        textArea = new JTextArea(60, 80);
        frame.getContentPane().add(new JScrollPane(textArea));

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    int port = Integer.parseInt(portTextField.getText());
                    if(port < 1024 || port > 65535){
                        throw new IllegalAccessException("非法端口号");
                    }

                    startServer(port);


                }catch(Exception a ){
                    System.out.println("无效端口\n");
                    textArea.append("无效端口\n");
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                stopServer();
            }
        });
    }


//启动server
    private void startServer(int port) {
        server = new TCPserver(port,this);
        server.start();


        }


//停止server
    private void stopServer() {
        server.stopserver();


    }
}
