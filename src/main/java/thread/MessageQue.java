package thread;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQue {
    private Queue<String> Que;
    public static MessageQue messageQue = new MessageQue();

    MessageQue() {
        Que = new LinkedList<>();
    }

    public void addMessage(String message) {
        Que.add(message);
    }

    public String readMessage() {
        return Que.poll();
    }

    public static MessageQue getInstance() {
        return messageQue;
    }

    public boolean isEmpty() {
        return Que.isEmpty();
    }
}
