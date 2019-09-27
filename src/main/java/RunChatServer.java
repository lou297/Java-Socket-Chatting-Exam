import thread.LogThread;

public class RunChatServer {
    public static void main(String[] args) {
        LogThread logThread = LogThread.getInstance();

        logThread.log("mainThread : " + Thread.currentThread().getName());
        logThread.log(">>> server is ready... <<<");

        logThread.run();
    }
}
