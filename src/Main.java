import annotations.Pure;
import base.Functional;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

    }

}

class Utils {
    @Pure
    static int countLines(String path, String[] fileExtensions) {
        return traverseFolders(new File(path), Arrays.asList(fileExtensions)).stream().reduce(
                0,
                Functional.wrap((sum, file) -> sum + Files.readAllLines(file.toPath()).size()),
                Integer::sum);
    }

    private static List<File> traverseFolders(File root, List<String> validExtensions) {
        List<File> files = new ArrayList<>();
        if (root.isFile() && validExtensions.stream().anyMatch(x -> root.getName().endsWith(x))) {
            files.add(root);
        } else if (root.isDirectory()) {
            for (File file : Objects.requireNonNull(root.listFiles())) {
                files.addAll(traverseFolders(file, validExtensions));
            }
        }
        return files;
    }
}
