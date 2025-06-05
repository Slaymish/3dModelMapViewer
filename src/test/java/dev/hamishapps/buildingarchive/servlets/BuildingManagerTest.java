package dev.hamishapps.buildingarchive.servlets;

import dev.hamishapps.buildingarchive.Building;
import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingManagerTest {

    @BeforeEach
    public void clear() throws Exception {
        Field f = Persistency.class.getDeclaredField("dataPoints");
        f.setAccessible(true);
        List<DataPoint> list = (List<DataPoint>) f.get(null);
        list.clear();
    }

    @Test
    public void testDoGetReturnsBuildingJson() throws Exception {
        Building b = new Building();
        b.setName("House");
        Persistency.addDataPoint(new DataPoint(b));

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        Mockito.when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        BuildingManager servlet = new BuildingManager();
        servlet.doGet(req, resp);

        String json = sw.toString();
        assertTrue(json.contains("House"));
    }
}
