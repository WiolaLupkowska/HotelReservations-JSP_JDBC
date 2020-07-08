import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {

    //Context initCtx = new InitialContext();
    //Context envCtx = (Context) initCtx.lookup("java:comp/env");
    // Look up our data source
    //private DataSource dataSource= (DataSource)envCtx.lookup("jdbc/hotel");
//    private DBUtilClient dbUtil;//=new DBUtilClient(dataSource);
//    private final String db_url = "jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";



    private DataSource dataSource;
    private DBUtilClient dbUtil;

    public ClientServlet() throws NamingException {

         //Obtain our environment naming context
        Context initCtx = null;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            dataSource = (DataSource)
                    //envCtx.lookup("jdbc/rezerwacja_web_app");
                    envCtx.lookup("jdbc/hotel");
            System.out.println("ok");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();

        try {

            dbUtil = new DBUtilClient(dataSource);
           // dbUtil = new DBUtilClient(db_url);
            System.out.println("ok");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



      //  try {

            System.out.println("doget");
        ServletContext context = this.getServletContext();
        String deluxe = context.getInitParameter("deluxe");
        String comfort = context.getInitParameter("comfort");
        String basic = context.getInitParameter("basic");
        System.out.println(deluxe);

        int deluxeInt = Integer.parseInt(deluxe);
        int comfortInt = Integer.parseInt(comfort);
        int basicInt = Integer.parseInt(basic);
        System.out.println(deluxeInt);

        // odczytanie danych z formularza
        String name = request.getParameter("name");
        System.out.println("name"+name);
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String type = request.getParameter("type");
        System.out.println("typ: "+type);
        String dateToStr = request.getParameter("dateTo");
        String dateFromStr = request.getParameter("dateFrom");
        LocalDate dateFrom = LocalDate.parse(dateFromStr);
        LocalDate dateTo = LocalDate.parse(dateToStr);

        Klient klient = new Klient(name, surname, email);
        Pokoj pokoj = new Pokoj(type);


        long period = DAYS.between(dateFrom, dateTo);
        System.out.println(period);
        int days = Math.toIntExact(period);

        int cena = 0;
        System.out.println("type ok");
        if (type.equals("deluxe")) {
            cena = days * deluxeInt;
        } else if (type.equals("comfort")) {
            cena = days * comfortInt;
        } else if (type.equals("basic")) {
            cena = days * basicInt;
        }




        // utworzenie obiektu klasy Rezerwacja
        Rezerwacja rezerwacja = new Rezerwacja(klient, pokoj, cena, dateFrom, dateTo );




        // dodanie nowego obiektu do BD
        try {
            dbUtil.addRezerwacja(rezerwacja);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Rezerwacja> reservation = new ArrayList<>();
        reservation.add(new Rezerwacja(klient,pokoj,cena, dateFrom,dateTo));
        request.setAttribute("REZERWACJA_LIST", reservation);
        System.out.println("ok2");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/view_reservation.jsp");
        System.out.println("ok3");
        // krok 2
        dispatcher.forward(request, response);
//    }catch (NullPointerException e){
//        RequestDispatcher dispatcherError = request.getRequestDispatcher("/error.jsp");
//        dispatcherError.forward(request, response);
//    }

//        // powrot do listy
//        listRezerwacja(request, response);
//        try {
//
//            // lista telefonow (MVC)
//            listRezerwacja(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

//    private void listRezerwacja(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        // pobranie danych dzieki DBUtil
//        List<Rezerwacja> rezerwacjaList = dbUtil.getRezerwacja();
//
//        // dodanie telefonow do zadania
//        request.setAttribute("REZERWACJA_LIST", rezerwacjaList);
//        System.out.println("ok");
//
//        // wyslanie danych do JSP
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/client_view.jsp");
//        System.out.println("ok2");
//        dispatcher.forward(request, response);
//        System.out.println("ok3");
//
//    }


}
