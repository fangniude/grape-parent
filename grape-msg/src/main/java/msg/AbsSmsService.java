package msg;


public abstract class AbsSmsService implements MsgTypeService {
    @Override
    public MsgType msgType() {
        return MsgType.SMS;
    }
}
