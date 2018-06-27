import annotations.Pure;
import base.Functional;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Pure
    static List<String> getCodes(String path, String[] fileExtensions, boolean shuffle) {
        List<File> files = traverseFolders(new File(path), Arrays.asList(fileExtensions));
        if (shuffle) {
            Collections.shuffle(files);
        }
        return files.stream().reduce(
                new ArrayList<>(),
                Functional.wrap((contents, file) -> {
                    contents.addAll(Files.readAllLines(file.toPath()));
                    return contents;
                }),
                (a, b) -> Stream.concat(a.stream(), b.stream()).collect(Collectors.toCollection(ArrayList::new))
        );
    }

    @Pure
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
