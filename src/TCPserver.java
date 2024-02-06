//使用方法，实例化TCPserver>调用start方法
//构建方法TCPserver，输入参数port，初始化状态参数running=false，检查port是否是合法端口，抛出自定义错误IllegalArgumentException。
//
//方法startServer 调用startServer方法会将running值置为ture，同时开启一个绑定在port上的TCPserver，用accept方法，阻塞式等待连接，有Client来请求的时候建立一个socket，并接收消息。
//方法stopserver 调用stop方法会把run值置false， 同时会关闭已经建立的socket，关闭已经建立的server
//要求Client在发送完消息后自行断开，TCPserver重新调用accept方法等待连接，同一时间只能接收一个Client，直到run值被置false循环退出并关闭socket，server。


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TCPserver extends Thread{
    private int port;
    private volatile boolean running;
    private Socket socket;
    private ServerSocket server;
    private GUI gui;

    public TCPserver(int n ,GUI gui) throws IllegalArgumentException{
        running = false;
        port = n;
        this.gui = gui;

        if (port < 1024 || port > 65535) {
                throw new IllegalArgumentException("非法端口号");
            }

    }

    public void startServer()throws IOException {
        running = true;
        server = new ServerSocket(port);
        gui.startButton.setEnabled(false);
        gui.stopButton.setEnabled(true);
        GetLocalIP showIP = new GetLocalIP();
        for (String s : showIP.lan) {
            gui.textArea.append("本地IP："+s+"\n");
        }

        while(running){
            try {
                System.out.println("开始监听");
                gui.textArea.append("开始监听"+"\n");
                socket = server.accept();
                System.out.println("建立连接");
                gui.textArea.append("建立连接"+"\n");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String timeString = now.format(formatter);
                    System.out.println(timeString + " : " + line);
                    gui.textArea.append(timeString + " : " + line+"\n");

                }
                socket.close();
                System.out.println("连接断开");
                gui.textArea.append( " 连接断开 " +"\n");

            }catch (IOException e){
                if(running){
                    e.printStackTrace();
                }
                break;
            }


        }

    }


    public void stopserver()  {
        running = false;

        if (server != null && !server.isClosed()) {
            try {
                server.close();
                gui.textArea.append( " 监听结束 "+"\n");
                gui.startButton.setEnabled(true);
                gui.stopButton.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        try {
            this.startServer();
        } catch (IOException e) {
            System.out.println(e.toString());
 
        }
    }
}
