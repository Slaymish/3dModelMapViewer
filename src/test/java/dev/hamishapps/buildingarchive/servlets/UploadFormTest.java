package dev.hamishapps.buildingarchive.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UploadFormTest {

    @Test
    public void testDoGet() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        UploadForm form = new UploadForm();
        form.doGet(request, response);
        Mockito.verify(response).sendRedirect("/");
    }
}
