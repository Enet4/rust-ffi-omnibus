import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Library;

public class main {
    public interface CStringReturn extends Library {
        Pointer /*char * */ theme_song_generate(byte length);
        
        void theme_song_free(Pointer song /* char * */);
    }

    static final CStringReturn C_STRING_RETURN = (CStringReturn)Native.load("string_return", CStringReturn.class);

    static String generateThemeSong(int length) {
        if (length > 255) {
            throw new IllegalArgumentException();
        }
        Pointer song = C_STRING_RETURN.theme_song_generate((byte)length);
        String out = song.getString(0, "UTF8");
        C_STRING_RETURN.theme_song_free(song);
        return out;
    }

    public static void main(String[] args) {
        String song = generateThemeSong(5);
        System.out.println(song);
    }
}