import de.fhpotsdam.unfolding.providers.Google;
import processing.core.PApplet;

/**
 * @author abrar
 * since 7/19/2019
 */

public class LifeExpectancyRunner {
    public static void main(String[] args) {
        //call any map provider of your choice from here
        LifeExpectancyApplet lifeExpectancyApplet = new LifeExpectancyApplet(new Google.GoogleMapProvider());
        PApplet.main("LifeExpectancyApplet");
    }
}
