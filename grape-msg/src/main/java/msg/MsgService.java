package msg;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
public final class MsgService {
    private static Logger logger = LoggerFactory.getLogger(MsgService.class);

    private Map<MsgType, MsgTypeService> serviceMap = Maps.newHashMap();

    @Inject
    private List<MsgTypeService> msgTypeServiceList; // can not constructor inject for none impl

    @PostConstruct
    private void init() {
        if (msgTypeServiceList == null || msgTypeServiceList.isEmpty()) {
            logger.warn("Not support any type msg, may be something wrong.");
        } else {
            for (MsgTypeService service : msgTypeServiceList) {
                serviceMap.put(service.msgType(), service);
            }
        }
    }

    public void send(MsgType msgType, Msg msg) {
        if (serviceMap.containsKey(msgType)) {
            try {
                serviceMap.get(msgType).send(msg);
            } catch (RuntimeException e) {
                logger.error(
                        String.format("error sending msg, msg type: %s, msg: %s", msgType.name(),
                                msg.toString()), e);
            }
        } else {
            logger.warn(String.format("Not support msg type: %s", msgType.name()));
        }
    }
}
