package dev.hamishapps.buildingarchive.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class CameraServletTest {

    @Test
    public void testDoGetReturnsJson() throws Exception {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        StringWriter sw = new StringWriter();
        Mockito.when(resp.getWriter()).thenReturn(new PrintWriter(sw));

        CameraServlet servlet = new CameraServlet();
        servlet.doGet(req, resp);

        String json = sw.toString();
        assertTrue(json.contains("posX"));
        assertTrue(json.contains("rotZ"));
    }

    @Test
    public void testDoPostUpdatesCamera() throws Exception {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        Mockito.when(req.getParameter("posX")).thenReturn("1");
        Mockito.when(req.getParameter("posY")).thenReturn("2");
        Mockito.when(req.getParameter("posZ")).thenReturn("3");
        Mockito.when(req.getParameter("rotX")).thenReturn("4");
        Mockito.when(req.getParameter("rotY")).thenReturn("5");
        Mockito.when(req.getParameter("rotZ")).thenReturn("6");

        CameraServlet servlet = new CameraServlet();
        servlet.doPost(req, resp);

        Field f = CameraServlet.class.getDeclaredField("camera");
        f.setAccessible(true);
        Object camObj = f.get(servlet);
        Field posX = camObj.getClass().getDeclaredField("posX");
        posX.setAccessible(true);
        assertEquals(1.0, posX.getDouble(camObj));
    }
}
