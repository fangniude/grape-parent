package memdb;

import com.alibaba.fastjson.JSON;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;


class MemCachedService {
  private static final Logger logger = LoggerFactory.getLogger(MemCachedService.class);
  private MemcachedClient mcc;

  MemCachedService(String servers) {
    try {
      mcc = new XMemcachedClient(AddrUtil.getAddresses(servers));
    } catch (IOException e) {
      logger.error(String.format("Create mem cached client fail, servers: %s", servers), e);
    }
  }

  public void set(String key, Object value, int expireTime) {
    String val = JSON.toJSONString(value, true);
    try {
      mcc.set(key, expireTime, val);
    } catch (TimeoutException | InterruptedException | MemcachedException e) {
      logger.error(String.format("set key: %s, val: %s, expireTime %d", key, val, expireTime), e);
    }
  }

  public void delete(String key) {
    try {
      mcc.delete(key);
    } catch (TimeoutException | InterruptedException | MemcachedException e) {
      logger.error(String.format("delete key: %s", key), e);
    }
  }

  public <T> Optional<T> get(String key, Class<T> clazz) {
    String json = null;

    try {
      json = mcc.get(key);
    } catch (TimeoutException | MemcachedException | InterruptedException e) {
      logger.error(String.format("get key: %s, class: %s", key, clazz.getName()), e);
    }

    if (json == null) {
      return Optional.empty();
    }
    T object = JSON.parseObject(json, clazz);
    return Optional.of(object);
  }
}
