import java.io.FileInputStream;
import java.io.InputStream;

public class ReadExample {
    public static void main(String[] args) throws Exception {
        InputStream is = new FileInputStream("./data.txt");
        int readByte;
        while (true) {
            readByte = is.read();
            if (readByte == -1) {
                break;
            }
            System.out.println((char) readByte);
        }
        is.close();
    }
}