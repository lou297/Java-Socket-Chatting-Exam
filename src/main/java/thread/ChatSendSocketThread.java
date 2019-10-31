package thread;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

public class ChatSendSocketThread extends Thread{
    private Socket writeSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<ChatSendSocketThread> userList = new ArrayList<>();
    private LogThread logThread;
    private MessageQue messageQue;

    public ChatSendSocketThread(Socket writeSocket, ArrayList<ChatSendSocketThread> userList) {
        this.writeSocket = writeSocket;
        this.userList = userList;
    }

    @Override
    public void run() {
        logThread = LogThread.getInstance();
        messageQue = MessageQue.getInstance();
        try {
            out = new ObjectOutputStream(writeSocket.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            logThread.log("들어가야되는데");
            while(true) {
                sleep(100);
//                String message = in.readObject().toString();
//                messageDTO = (MessageDTO) in.readObject();
//                roomNumber = messageDTO.getRoomNumber();
//                userNickname = messageDTO.getUserNickname();

                if(!userList.contains(this)) userList.add(this);
//                for(ChatSocketThread e : curRoom) e.out.writeObject(messageDTO);
                if(!messageQue.isEmpty()) {
                    String message = messageQue.readMessage();
                    logThread.log(message + " 보내줘야지?");
                    logThread.log("사이즈 : "+userList.size());
                    for(ChatSendSocketThread e : userList) {
                        e.out.writeObject(message);
                        e.out.flush();
                    }
                }

                //메세지 읽어오기 끝.
                //사용자가 나갔을 때 처리 안했음.

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
