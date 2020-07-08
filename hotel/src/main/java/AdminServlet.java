

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static java.time.temporal.ChronoUnit.DAYS;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    private DBUtilAdmin dbUtil;
    private final String db_url = "jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {

            dbUtil = new DBUtilAdmin(db_url);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType("text/html");

        String name = request.getParameter("loginInput");
        String password = request.getParameter("passwordInput");

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

            List<Rezerwacja> rezerwacjaList = null;


            try {

                rezerwacjaList = dbUtil.getRezerwacja();
//                LocalDate localDate1 = LocalDate.of(2020, 8, 19);
//                LocalDate localDate2 = LocalDate.of(2020, 9, 19);
//                rezerwacjaList.add(new Rezerwacja(new Klient("Anna", "Maj", "maja@mail.com"), new Pokoj("basic"), 1000.0D, localDate1, localDate2));
                System.out.println("tu"+rezerwacjaList);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
            request.setAttribute("REZERWACJA_LIST", rezerwacjaList);

            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/home.html");
            dispatcher.include(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {


        try {

            // odczytanie zadania
            String command = request.getParameter("command");

            if (command == null)
                command = "LIST";

            switch (command) {

                case "LIST":
                    listRezerwacja(request, response);
                    break;

                case "ADD":
                    addRezerwacja(request, response);
                    break;

                case "LOAD":
                    loadRezerwacja(request, response);
                    break;

                case "UPDATE":
                    updateRezerwacja(request, response);
                    break;

                case "DELETE":
                    deleteRezerwacja(request, response);
                    break;

                default:
                    listRezerwacja(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void deleteRezerwacja(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie danych z formularza
        String id = request.getParameter("rezerwacjaID");

        // usuniecie telefonu z BD
        dbUtil.deleteRezerwacja(id);

        // wyslanie danych do strony z lista telefonow
        listRezerwacja(request, response);

    }
////////////////
    private void updateRezerwacja(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie danych z formularza
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
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String type = request.getParameter("type");
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
        if (type.equals("deluxe")) {
            cena = days * deluxeInt;
        } else if (type.equals("comfort")) {
            cena = days * comfortInt;
        } else if (type.equals("basic")) {
            cena = days * basicInt;
        }



        // utworzenie nowego telefonu
        Rezerwacja rezerwacja = new Rezerwacja(klient, pokoj, cena, dateFrom, dateTo );

        // uaktualnienie danych w BD
        dbUtil.updateRezerwacja(rezerwacja);

        // wyslanie danych do strony z lista telefonow
        listRezerwacja(request, response);

    }

    private void loadRezerwacja(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // odczytanie id telefonu z formularza
        String id = request.getParameter("rezerwacjaID");

        // pobranie  danych telefonu z BD
        Rezerwacja rezerwacja = dbUtil.getRezerwacja(id);

        // przekazanie telefonu do obiektu request
        request.setAttribute("REZERWACJA", rezerwacja);

        // wyslanie danych do formmularza JSP (update_rezerwacja_form)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update_rezerwacja_form.jsp");
        dispatcher.forward(request, response);

    }

    private void addRezerwacja(HttpServletRequest request, HttpServletResponse response) throws Exception {





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
            String surname = request.getParameter("surname");
            String email = request.getParameter("email");
            String type = request.getParameter("type");
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
        this.dbUtil.addRezerwacja(rezerwacja);
        //this.dbUtil.addKlient(klient);
        this.listRezerwacja(request, response);

//            List<Rezerwacja> reservation = new ArrayList<>();
//            reservation.add(new Rezerwacja(klient,pokoj,cena, dateFrom,dateTo));
//            request.setAttribute("reservations", reservation);
//
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/view_reservation.jsp");
//
//            // krok 2
//            dispatcher.forward(request, response);
//        }catch (NullPointerException e){
//            RequestDispatcher dispatcherError = request.getRequestDispatcher("/error.jsp");
//            dispatcherError.forward(request, response);
//        }




        // utworzenie obiektu klasy Rezerwacja
//        Rezerwacja rezerwacja = new Rezerwacja(klient, pokoj, cena, dateFrom, dateTo );

        // dodanie nowego obiektu do BD
        dbUtil.addRezerwacja(rezerwacja);



    }

    private void listRezerwacja(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Rezerwacja> rezerwacjaList = dbUtil.getRezerwacja();


        // dodanie listy do obiektu zadania
        request.setAttribute("REZERWACJA_LIST", rezerwacjaList);

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

    }


    private boolean validate(String name, String pass) {
        boolean status = false;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //
            conn = getConnection(db_url, name, pass);
            status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

}
