package memdb;

import java.util.Optional;

public abstract class MemTable<T> {
  static final int MAX_EXPIRE_TIME = Integer.MAX_VALUE; // 68 years
  String topic;
  Class<T> valClass;
  int expireTime; // unit: second

  MemTable(String topic, Class<T> valClass, int expireTime) {
    this.topic = topic;
    this.valClass = valClass;
    this.expireTime = expireTime;
  }

  public abstract void put(String key, T value);

  public abstract void delete(String key);

  public abstract Optional<T> get(String key);

  public T getOrDefault(String key, T defaultVal) {
    return get(key).orElse(defaultVal);
  }
}
