import org.grape.GrapeDbMigration;

import java.io.IOException;

public class DbGenerator {
    public static void main(String[] args) throws IOException {
        GrapeDbMigration dbm = new GrapeDbMigration();
        dbm.generate("dict", "1.0.0");
    }
}
