import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author abrar
 * since 7/19/2019
 */

public class TestLifeExpectency {
    public static void main(String[] args) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
    }
}
