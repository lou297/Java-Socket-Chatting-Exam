package thread;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatRoomSocketThread extends Thread {

    private static final String PORT_NUMBER = "8888";
    private LogThread logThread;

    @Override
    public void run() {
        logThread = LogThread.getInstance();
        ServerSocket serverSocket = null;

        try {
            logThread.log("ChatRoom Thread : " + Thread.currentThread().getName());
            serverSocket = new ServerSocket(Integer.parseInt(PORT_NUMBER));
            logThread.log(">> Socket Room Thread is Ready.. <<");

            while(true) {
                try {
                    Socket socket = serverSocket.accept();
                    ChatSocketThread chatSocketThread = new ChatSocketThread(socket);
                    chatSocketThread.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
