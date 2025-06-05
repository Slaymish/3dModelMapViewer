package dev.hamishapps.buildingarchive;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BuildingTest {

    @Test
    public void testHasEnoughParamsTrue() {
        Building b = new Building();
        b.setName("A");
        b.setAuthor("Auth");
        b.setDescription("Desc");
        assertTrue(b.hasEnoughParams());
    }

    @Test
    public void testHasEnoughParamsFalse() {
        Building b = new Building();
        b.setName("A");
        b.setAuthor("Auth");
        // description missing
        assertFalse(b.hasEnoughParams());
    }

    @Test
    public void testHasBuilding() {
        Building b = new Building();
        assertFalse(b.hasBuilding());
        b.setModelPath("model.obj");
        assertTrue(b.hasBuilding());
    }

    @Test
    public void testToString() {
        Building b = new Building();
        b.setName("N");
        b.setAuthor("A");
        b.setDescription("D");
        assertEquals("NAD", b.toString());
    }
}
