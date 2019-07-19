import org.testng.annotations.Test;

import java.nio.file.Paths;

/**
 * @author abrar
 * since 7/19/2019
 */

public class TestLifeExpectancy {
    public static void main(String[] args) {
        String currentPath = Paths.get("").toAbsolutePath().toString();
        System.out.println("Current path is: " + currentPath);
    }

    @Test
    public void runApp() throws Exception {
        LifeExpectancyApplet.main("LifeExpectancyApplet");
        Thread.sleep(10000);
    }
}
