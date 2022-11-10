package ar.edu.unlam.actas.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static void checkFileExists(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getFile(String filename) throws UnsupportedEncodingException {
        return new File(URLDecoder.decode(filename, StandardCharsets.UTF_8.toString()));
    }

    public static FileWriter getFileWriter(String filename) throws IOException {
        return new FileWriter(URLDecoder.decode(filename, StandardCharsets.UTF_8.toString()));
    }
}
