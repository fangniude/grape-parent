package memdb;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Getter
@Component
@PropertySource("classpath:application.properties")
class MemdbConfig {
  static final String TYPE_GUAVA = "Guava";
  static final String TYPE_MEM_CACHED = "MemCached";

  @Value("${memdb.type:Guava}")
  private String type;

  @Value("${memdb.memcached.servers:localhost:11211}")
  private String memCachedServers; // localhost:11211 localhost:11311, split with space
}
