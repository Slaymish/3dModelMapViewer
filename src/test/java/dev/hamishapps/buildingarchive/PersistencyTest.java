package dev.hamishapps.buildingarchive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersistencyTest {

    @BeforeEach
    public void clear() throws Exception {
        Field f = Persistency.class.getDeclaredField("dataPoints");
        f.setAccessible(true);
        List<DataPoint> list = (List<DataPoint>) f.get(null);
        list.clear();
    }

    @Test
    public void testAddAndGetMostRecent() {
        Building b = new Building();
        Persistency.addDataPoint(new DataPoint(b));
        assertEquals(1, Persistency.getDataPoints().size());
        DataPoint recent = Persistency.getMostRecentDataPoint();
        assertSame(b, recent.getBuilding());
    }

    @Test
    public void testUpdateDataPoint() {
        Building b = new Building();
        DataPoint dp = new DataPoint(b, new double[]{0,0,0}, 0);
        Persistency.addDataPoint(dp);

        DataPoint updated = new DataPoint(b, new double[]{1,2,3}, 45);
        Persistency.updateDataPoint(updated);

        DataPoint stored = Persistency.getDataPoints().get(0);
        assertArrayEquals(new double[]{1,2,3}, stored.getOffsets());
        assertEquals(45, stored.getRotationAngle());
    }
}
