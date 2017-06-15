package memdb;

import java.util.Optional;

class MemCachedMemTable<T> extends MemTable<T> {
  private final MemCachedService memCachedService;

  MemCachedMemTable(String name, Class<T> valClass, int expireTime,
      MemCachedService memCachedService) {
    super(name, valClass, expireTime);
    this.memCachedService = memCachedService;
  }

  @Override
  public void put(String key, T value) {
    memCachedService.set(tableKey(key), value, expireTime);
  }

  @Override
  public void delete(String key) {
    memCachedService.delete(tableKey(key));
  }

  @Override
  public Optional<T> get(String key) {
    return memCachedService.get(tableKey(key), valClass);
  }

  private String tableKey(String key) {
    return String.format("%s@_@%s", topic, key);
  }
}
