package memdb;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

class GuavaMemTable<T> extends MemTable<T> {
  private Cache<String, T> cache;

  GuavaMemTable(String name, Class<T> valClass, int expireTime) {
    super(name, valClass, expireTime);
    cache = CacheBuilder.newBuilder().expireAfterWrite(expireTime, TimeUnit.SECONDS).build();
  }

  @Override
  public void put(String key, T value) {
    cache.put(key, value);
  }

  @Override
  public void delete(String key) {
    cache.invalidate(key);
  }

  @Override
  public Optional<T> get(String key) {
    return Optional.ofNullable(cache.getIfPresent(key));
  }
}
