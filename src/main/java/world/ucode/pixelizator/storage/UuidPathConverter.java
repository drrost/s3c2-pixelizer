package world.ucode.pixelizator.storage;

import java.util.UUID;

public class UuidPathConverter {

    public static String path(UUID uuid, int n) {
        String string = String.valueOf(uuid);
        return path(string, n);
    }

    public static String path(String uuid, int n) {
        String string = String.valueOf(uuid);
        String result = "";
        for (int i = 0; i < n; i++) {
            result += string.substring(0, i + 1) + "/";
        }
        return result;
    }
}
