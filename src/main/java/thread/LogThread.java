package thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class LogThread extends Thread {
    private static String DIR_PATH = "../resources";
    private static String FILE_NAME = "ChatLog.txt";
    private BufferedWriter bw, bwFile;

    private static LogThread logThread = new LogThread();
    private Calendar calendar;
    private static Queue<String> messageQueue;
    private boolean flag = true;

    private LogThread() {
        try {
            //채팅 날짜 출력을 위한 Calendar
            calendar = Calendar.getInstance();
            //입력 받았던 메세지를 담아두다 출력하는 Queue
            messageQueue = new LinkedList<>();
            //화면에 출력하는 bw
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
            //txt에 채팅 내역을 저장하는 bw
            bwFile = new BufferedWriter(new FileWriter(DIR_PATH + FILE_NAME));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LogThread getInstance() {
        return logThread;
    }

    public void log(String message) {
        messageQueue.add(message);
    }

    @Override
    public void run() {
        logThread.log("Log Thread : " + Thread.currentThread().getName());
        logThread.log(">> LogThread is ready... <<");
        String str;
        while(flag) {
            if(!messageQueue.isEmpty()) {
                try {
                   str = "[" +  calendar.getTime() + "]" + messageQueue.peek() + "\n";
                   bw.write(str);
                   writeFile(str);
                   bw.flush();
                   messageQueue.poll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            bw.close();
            bwFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String str) {
        try {
            bwFile.write(str);
            bwFile.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
