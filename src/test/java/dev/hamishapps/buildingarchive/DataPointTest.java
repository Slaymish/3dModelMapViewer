package dev.hamishapps.buildingarchive;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataPointTest {

    @Test
    public void testGetBuilding() {
        Building b = new Building();
        DataPoint dp = new DataPoint(b);
        assertSame(b, dp.getBuilding());
    }

    @Test
    public void testGetBuildingThrows() {
        DataPoint dp = new DataPoint(new Building());
        dp.setBuilding(null);
        assertThrows(NullPointerException.class, dp::getBuilding);
    }

    @Test
    public void testCopyConstructor() {
        Building b = new Building();
        DataPoint dp1 = new DataPoint(b, new double[]{1,2,3}, 45);
        DataPoint dp2 = new DataPoint(dp1);
        assertEquals(dp1.getBuilding(), dp2.getBuilding());
        assertArrayEquals(dp1.getOffsets(), dp2.getOffsets());
        assertEquals(dp1.getRotationAngle(), dp2.getRotationAngle());
    }

    @Test
    public void testToStringContainsOffsets() {
        Building b = new Building();
        b.setName("B");
        b.setAuthor("A");
        b.setDescription("D");
        DataPoint dp = new DataPoint(b, new double[]{1,2,3});
        String str = dp.toString();
        assertTrue(str.contains("X:1"));
        assertTrue(str.contains("Y:2"));
        assertTrue(str.contains("Z:3"));
    }
}
