package world.ucode.pixelizator.util;

public class FilenamesHelper {

    public static String addSuffixToFileName(String fileName, String suffix) {
        if (fileName.length() == 0)
            return suffix;

        int index = fileName.indexOf('.');
        if (index == -1)
            return fileName + suffix;

        var name = getName(fileName);
        var extension = getExtension(fileName);
        return name + suffix + "." + extension;
    }

    private static String getName(String fileName) {
        var index = fileName.lastIndexOf('.');
        if (index == -1)
            return fileName;
        return fileName.substring(0, index);
    }

    private static String getExtension(String fileName) {
        var index = fileName.lastIndexOf('.');
        if (index == -1)
            return "";
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
