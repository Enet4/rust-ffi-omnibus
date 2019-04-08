import com.sun.jna.IntegerType;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class main {
    public static class Size extends IntegerType {
        public Size() {
            super(Native.SIZE_T_SIZE, true);
        }
        public Size(long value) {
            super(Native.SIZE_T_SIZE, value, true);
        }
    }

    public static class CSliceArguments implements Library {
        static {
            Native.register("slice_arguments");
        }
        
        public static native int sum_of_even(int[] numbers, Size length);
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6};
        int count = CSliceArguments.sum_of_even(numbers, new Size(numbers.length));
        System.out.println(count);
    }
}