package msg.service;

import msg.Msg;
import msg.MsgType;

public interface MsgTypeService {
  MsgType msgType();

  void send(Msg msg);
}
