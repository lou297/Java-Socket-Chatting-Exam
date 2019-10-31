package thread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ChatRoomSocketThread extends Thread {

    private static final String PORT_NUMBER = "8888";
    private static final String WRITE_PORT_NUMBER = "7777";
    public static Queue<String> messageQue = new LinkedList<>();
    private static ArrayList<ChatSendSocketThread> userList = new ArrayList<>();
    private LogThread logThread;

    @Override
    public void run() {
        logThread = LogThread.getInstance();
        ServerSocket serverSocket = null;
        ServerSocket serverWriteSocket = null;

        try {
            logThread.log("ChatRoom Thread : " + Thread.currentThread().getName());
            serverSocket = new ServerSocket(Integer.parseInt(PORT_NUMBER));
            serverWriteSocket = new ServerSocket(Integer.parseInt(WRITE_PORT_NUMBER));
            logThread.log(">> Socket Room Thread is Ready.. <<");

            while(true) {
                try {
                    sleep(100);
                    Socket readSocket = serverSocket.accept();
                    Socket writeSocket = serverWriteSocket.accept();
                    logThread.log("여기 들어옴");
                    //read 해주는 소켓
                    ChatSocketThread chatSocketThread = new ChatSocketThread(readSocket);
                    chatSocketThread.start();
                    //write 해주는 소켓
                    ChatSendSocketThread chatSendSocketThread = new ChatSendSocketThread(writeSocket, userList);
                    chatSendSocketThread.start();



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
