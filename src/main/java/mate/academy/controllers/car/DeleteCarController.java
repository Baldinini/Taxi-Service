package mate.academy.controllers.car;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.lib.Injector;
import mate.academy.service.CarService;

public class DeleteCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private final CarService carService =
            (CarService) injector.getInstance(CarService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String carId = req.getParameter("id");
        Long id = Long.valueOf(carId);
        carService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
