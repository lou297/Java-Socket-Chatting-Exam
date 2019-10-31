package thread;

import dto.MessageDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatSocketThread extends Thread {
    private Socket readSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LogThread logThread;
    private MessageQue messageQue;

    private ArrayList<ChatSocketThread> curRoom = new ArrayList<>();

    private MessageDTO messageDTO;
    private int roomNumber;
    private String userNickname;
    private String message;

    public ChatSocketThread(Socket readSocket) {
        this.readSocket = readSocket;
    }

    @Override
    public void run() {
        try {
            logThread = LogThread.getInstance();
            messageQue = MessageQue.getInstance();
            in = new ObjectInputStream(readSocket.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            while(true) {
                sleep(100);
                String message = in.readObject().toString();
//                messageDTO = (MessageDTO) in.readObject();
//                roomNumber = messageDTO.getRoomNumber();
//                userNickname = messageDTO.getUserNickname();
                messageQue.addMessage(message);
                logThread.log(message +" 받아서 넣었음");
//                if(!curRoom.contains(this)) curRoom.add(this);
//                logThread.log("메세지 전송" + message);
//                for(ChatSocketThread e : curRoom) e.out.writeObject(messageDTO);
//                for(ChatSocketThread e : curRoom) {
//                    e.out.writeObject(message);
//                    e.out.flush();
//                }

                //메세지 읽어오기 끝.
                //사용자가 나갔을 때 처리 안했음.

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
