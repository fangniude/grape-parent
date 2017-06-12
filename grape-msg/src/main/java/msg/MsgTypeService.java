package msg;

public interface MsgTypeService {
    MsgType msgType();

    void send(Msg msg);
}
