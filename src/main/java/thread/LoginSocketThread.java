package thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginSocketThread extends Thread {

    private final static String PORT_NUMBER = "9999";

    private LogThread logThread;

    @Override
    public void run() {
        logThread = LogThread.getInstance();

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(Integer.parseInt(PORT_NUMBER));
            logThread.log("Login Thread : " + Thread.currentThread().getName());
            logThread.log(">> Login Thread is ready <<");

            while(true) {
                Socket socket = serverSocket.accept();
                logThread.log(Thread.currentThread().getName() + " :: Login Request :: " + socket.getInetAddress());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                String user = (String) in.readObject();

                //로그인 정보 맞는지 처리

                //맞다고 가정
                //맞으면 아이디 그대로 내주기!
                //아니라면 fail 메세지 주면 되지 않을까?
                out.writeBytes(user);
                out.flush();
                out.close();
                in.close();
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
