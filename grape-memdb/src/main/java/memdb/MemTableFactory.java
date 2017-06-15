package memdb;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;

@Component
public class MemTableFactory {
  private MemdbConfig config;

  private MemCachedService memCachedService;

  private Map<String, MemTable> memTableMap = Maps.newHashMap();

  @Inject
  public MemTableFactory(MemdbConfig config) {
    this.config = config;
  }

  public <T> MemTable<T> getOrCreateMemTable(String topic, Class<T> valClass) {
    return getOrCreateMemTable(topic, valClass, MemTable.MAX_EXPIRE_TIME);
  }

  public synchronized <T> MemTable<T> getOrCreateMemTable(String topic, Class<T> valClass,
      int expireTime) {
    if (!memTableMap.containsKey(topic)) {
      MemTable<T> memTable;
      switch (config.getType()) {
        case MemdbConfig.TYPE_MEM_CACHED:
          memTable =
              new MemCachedMemTable<>(topic, valClass, expireTime, getOrCreateMemdbService());
          break;
        case MemdbConfig.TYPE_GUAVA:
        default:
          memTable = new GuavaMemTable<>(topic, valClass, expireTime);
      }
      memTableMap.put(topic, memTable);
    }
    return memTableMap.get(topic);
  }

  private MemCachedService getOrCreateMemdbService() {
    if (memCachedService == null) {
      memCachedService = new MemCachedService(config.getMemCachedServers());
    }
    return memCachedService;
  }
}
