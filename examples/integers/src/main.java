import com.sun.jna.Native;
import com.sun.jna.Library;

public class main {
    public static class CIntegers implements Library {
        static {
            Native.register("integers");
        }

        public static native int addition(int a, int b);
    }

    public static void main(String[] args) {
        int sum = CIntegers.addition(1, 2);
        System.out.println(sum);
    }
}