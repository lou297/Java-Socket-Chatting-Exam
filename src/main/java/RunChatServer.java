import thread.ChatRoomSocketThread;
import thread.LogThread;
import thread.LoginSocketThread;

public class RunChatServer {
    public static void main(String[] args) {
        LogThread logThread = LogThread.getInstance();
        logThread.start();

        logThread.log("mainThread : " + Thread.currentThread().getName());
        logThread.log(">>> server is ready... <<<");

        LoginSocketThread loginSocketThread = new LoginSocketThread();
        loginSocketThread.start();

        ChatRoomSocketThread chatRoomSocketThread = new ChatRoomSocketThread();
        chatRoomSocketThread.start();

    }
}
