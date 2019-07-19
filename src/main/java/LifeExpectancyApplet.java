import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author abrar
 * since 7/18/2019
 */

public class LifeExpectancyApplet extends PApplet {
    private String commonProjectDir;
    private AbstractMapProvider mapProvider;
    private UnfoldingMap map;
    private int backgroundGray = color(10, 50, 10);
    private Map<String, Float> lifeExpData;
    private List<Feature> countries;
    private List<Marker> countryMarkers;

    public LifeExpectancyApplet() {
        mapProvider = new Microsoft.AerialProvider();
        commonProjectDir = Paths.get("").toAbsolutePath().toString() + "\\data\\";
        if(!System.getProperty("os.name").split("\\s")[0].toLowerCase().equals("windows")){
            commonProjectDir = commonProjectDir.replaceAll("\\\\", "/");
        }
    }

    public LifeExpectancyApplet(AbstractMapProvider provider) {
        mapProvider = provider;
        commonProjectDir = Paths.get("").toAbsolutePath().toString() + "\\data\\";
        if(!System.getProperty("os.name").split("\\s")[0].toLowerCase().equals("windows")){
            commonProjectDir = commonProjectDir.replaceAll("\\\\", "/");
        }
    }

    public void setup() {
        size(1280, 720, JAVA2D);
        background(backgroundGray);
        map = new UnfoldingMap(this, 50, 50, 1280, 720, mapProvider);
        lifeExpData = readLifeExpDataFromCSV(commonProjectDir + "LifeExpectancyWorldBankModule3.csv");
        MapUtils.createDefaultEventDispatcher(this, map);
        initMapElements();
        shadeCountries();
    }

    public void draw() {
        map.draw();
    }

    private HashMap<String, Float> readLifeExpDataFromCSV(String file) {
        HashMap<String, Float> data = new HashMap<>();
        String[] rows = loadStrings(file);
        for (String currentRow : rows) {
            String countryName = null;
            float lifeExp = 0;
            String[] columnData = currentRow.split(",");
            if (columnData.length == 6 && !columnData[5].equals("..")) {
                countryName = columnData[4];
                lifeExp = Float.parseFloat(columnData[5]);
                data.put(countryName, lifeExp);
            }
        }
        return data;
    }

    private void initMapElements() {
        String geoDataDir = commonProjectDir + "countries.geo.json";
        countries = GeoJSONReader.loadData(this, geoDataDir);
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        map.addMarkers(countryMarkers);
    }

    private void shadeCountries() {
        for (Marker currentMarker : countryMarkers) {
            String currentCountryId = currentMarker.getId();
            if (lifeExpData.containsKey(currentCountryId)) {
                float currentCountryLifeExp = lifeExpData.get(currentCountryId);
                //map 40-90 to 0-255 colors
                int currentColorLevel = (int) map(currentCountryLifeExp, 40, 90, 0, 255);
                //the more colorLevel is, the marker will be Blue-er
                currentMarker.setColor(color(255 - currentColorLevel, 100, currentColorLevel));
            } else {
                //default gray color for countries whose data is unavialable in lifeExpData
                currentMarker.setColor(color(150, 150, 150));
            }
        }
    }
}
