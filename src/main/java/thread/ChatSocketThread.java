package thread;

import dto.MessageDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatSocketThread extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LogThread logThread;

    private ArrayList<ChatSocketThread> curRoom;
    private MessageDTO messageDTO;
    private int roomNumber;
    private String userNickname;
    private String message;

    public ChatSocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            logThread = LogThread.getInstance();
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            logThread.log(Thread.currentThread().getName() + " : Socket Open " + socket.getInetAddress());
            while(true) {
                sleep(10);
                messageDTO = (MessageDTO) in.readObject();
                roomNumber = messageDTO.getRoomNumber();
                userNickname = messageDTO.getUserNickname();

                if(curRoom.contains(this)) curRoom.add(this);

                for(ChatSocketThread e : curRoom) e.out.writeObject(messageDTO);

                //메세지 읽어오기 끝.
                //사용자가 나갔을 때 처리 안했음.

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
