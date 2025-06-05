package dev.hamishapps.buildingarchive.scene;

import dev.hamishapps.buildingarchive.Building;
import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapExplorerTest {

    @BeforeEach
    public void clear() throws Exception {
        Field f = Persistency.class.getDeclaredField("dataPoints");
        f.setAccessible(true);
        List<DataPoint> list = (List<DataPoint>) f.get(null);
        list.clear();
    }

    @Test
    public void testUploadBuildingAddsDataPoint() {
        MapExplorer explorer = new MapExplorer();
        Building b = new Building();
        b.setName("B");
        explorer.uploadBuilding(b);
        assertEquals(1, Persistency.getDataPoints().size());
    }

    @Test
    public void testAdjustments() {
        DataPoint dp = new DataPoint(new Building(), new double[]{0,0,0}, 0);
        MapExplorer explorer = new MapExplorer();
        explorer.adjustBuildingPosition(dp, new double[]{1,2,3});
        explorer.adjustBuildingRotation(dp, 90);
        assertArrayEquals(new double[]{1,2,3}, dp.getOffsets());
        assertEquals(90, dp.getRotationAngle());
    }
}
