import com.sun.jna.Native;
import com.sun.jna.Library;
import com.sun.jna.Structure;

public class main {

    public interface CTuples extends Library {
        Tuple flip_things_around(Tuple tuple);
    }
    
    @Structure.FieldOrder({"x", "y"})
    public static class Tuple extends Structure implements Structure.ByValue {
        static final CTuples C_TUPLES = (CTuples)Native.load("tuples", CTuples.class);
        public int x;
        public int y;

        public Tuple() {
            this(0, 0);
        }

        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Tuple flipThingsAround() {
            return C_TUPLES.flip_things_around(this);
        }
    }

    public static void main(String[] args) {
        Tuple initial = new Tuple(10, 20);
        Tuple result = initial.flipThingsAround();
        System.out.printf("(%d,%d)\n", result.x, result.y);
    }
}