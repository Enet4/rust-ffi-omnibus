import java.io.Closeable;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class main {
    public static class CObjects implements Library {
        static {
            Native.register("objects");
        }
        
        public static native Pointer zip_code_database_new();
        public static native void zip_code_database_free(Pointer /* zip_code_database_t * */ db);
        public static native void zip_code_database_populate(Pointer /* zip_code_database_t * */ db);
        public static native int zip_code_database_population_of(Pointer /* const zip_code_database_t * */ db, String zip);
    }

    static class ZipCodeDatabase implements Closeable {
        private final Pointer inner;

        public ZipCodeDatabase() {
            this.inner = CObjects.zip_code_database_new();
        }

        public void populate() {
            CObjects.zip_code_database_populate(this.inner);
        }

        public int populationOf(String zip) {
            return CObjects.zip_code_database_population_of(this.inner, zip);
        }

        public void free() {
            CObjects.zip_code_database_free(this.inner);
        }

        @Override
        public void close() {
            this.free();
        }
    }


    public static void main(String[] args) {
        try (ZipCodeDatabase database = new ZipCodeDatabase()) {
            database.populate();
        
            int pop1 = database.populationOf("90210");
            int pop2 = database.populationOf("20500");
        
            System.out.println(pop1 - pop2);
        }
    }
}