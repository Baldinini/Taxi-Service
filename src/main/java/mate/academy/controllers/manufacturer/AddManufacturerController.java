package mate.academy.controllers.manufacturer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lib.Injector;
import mate.academy.model.Manufacturer;
import mate.academy.service.ManufacturerService;

public class AddManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final ManufacturerService manufacturerService =
            (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/manufacturers/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String manufacturerName = req.getParameter("manufacturer_name");
        String manufacturerCountry = req.getParameter("manufacturer_country");
        manufacturerService.create(new Manufacturer(manufacturerName, manufacturerCountry));
        resp.sendRedirect(req.getContextPath() + "/manufacturers");
    }
}
