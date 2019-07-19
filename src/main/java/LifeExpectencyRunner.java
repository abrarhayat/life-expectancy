import de.fhpotsdam.unfolding.providers.OpenMapSurferProvider;
import processing.core.PApplet;

/**
 * @author abrar
 * since 7/19/2019
 */

public class LifeExpectencyRunner {
    public static void main(String[] args) {
        LifeExpectancyApplet lifeExpectencyApplet = new LifeExpectancyApplet(new OpenMapSurferProvider.Roads());
        PApplet.main("LifeExpectancyApplet");
    }
}
