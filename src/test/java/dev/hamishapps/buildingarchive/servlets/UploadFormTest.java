package dev.hamishapps.buildingarchive.servlets;

import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UploadFormTest {

    @BeforeEach
    public void clear() throws Exception {
        Field f = Persistency.class.getDeclaredField("dataPoints");
        f.setAccessible(true);
        List<DataPoint> list = (List<DataPoint>) f.get(null);
        list.clear();
    }

    @Test
    public void testDoPostAddsBuilding() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ServletContext context = Mockito.mock(ServletContext.class);
        Mockito.when(request.getServletContext()).thenReturn(context);

        Mockito.when(request.getParameterNames()).thenReturn(Collections.enumeration(Arrays.asList("name","author","description")));
        Mockito.when(request.getParameter("name")).thenReturn("Name");
        Mockito.when(request.getParameter("author")).thenReturn("Auth");
        Mockito.when(request.getParameter("description")).thenReturn("Desc");
        Mockito.when(request.getPart("modelFile")).thenReturn(null);

        UploadForm form = new UploadForm();
        form.doPost(request, response);

        Mockito.verify(response).sendRedirect("explore.html");
        Mockito.verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
        assertEquals(1, Persistency.getDataPoints().size());
    }
}
