import com.sun.jna.IntegerType;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.Library;

public class main {
    public static class Size extends IntegerType {
        public Size() {
            super(Native.SIZE_T_SIZE, true);
        }
        public Size(long value) {
            super(Native.SIZE_T_SIZE, value, true);
        }
    }

    public static class CVectorReturn implements Library {
        static {
            Native.register("vector_return");
        }

        public static native Size counter_generate(Size size, PointerByReference /* int16_t ** */ vec);
        public static native void counter_free(Pointer /* int16_t * */ vec, Size size);
    }

    static short[] generateCounter(int size) {
        if (size > 255) {
            throw new IllegalArgumentException();
        }
        PointerByReference vec = new PointerByReference();
        Size cSize = new Size(size);
        CVectorReturn.counter_generate(cSize, vec);
        short[] out = vec.getValue().getShortArray(0, size);
        CVectorReturn.counter_free(vec.getValue(), cSize);
        return out;
    }

    public static void main(String[] args) {
        for (short v: generateCounter(10)) {
            System.out.printf("%d..", v);
          }
        System.out.println();
    }
}