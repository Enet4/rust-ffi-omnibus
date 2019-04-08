import com.sun.jna.Native;
import com.sun.jna.Library;

public class main {
    public static class CStringArguments implements Library {
        static {
            Native.register("string_arguments");
        }

        public static native long how_many_characters(String s);
    }

    public static void main(String[] args) {
        long count = CStringArguments.how_many_characters("göes to élevên");
        System.out.println(count);
    }
}