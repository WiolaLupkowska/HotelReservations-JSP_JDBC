import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DBUtilAdmin extends DBUtil {

    private String URL;
    private String name;
    private String password;

    public DBUtilAdmin(String URL) {

        this.URL = "jdbc:mysql://localhost:3306/hotel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";
    }


    public List<Rezerwacja> getRezerwacja() throws Exception {

        System.out.println("ok");

        List<Rezerwacja> rezerwacja = new ArrayList<>();

        Connection connRezerwacja = null;
        Connection connPokoj = null;
        Connection connKlient = null;
        Statement statementRezerwacja = null;
        Statement statementKlient = null;
        Statement statementPokoj = null;
        ResultSet resultSetRezerwacja = null;
        ResultSet resultSetKlient = null;
        ResultSet resultSetPokoj = null;
        Klient klient = null;
        Pokoj pokoj=null;

        try {

            // polaczenie z BD
            connRezerwacja = DriverManager.getConnection(URL, name, password);


            // zapytanie SELECT
            String sqlRezerwacja = "SELECT * FROM rezerwacja";
            statementRezerwacja = connRezerwacja.createStatement();

            // wykonanie zapytania SQL
            resultSetRezerwacja = statementRezerwacja.executeQuery(sqlRezerwacja);


            // przetworzenie wyniku zapytania

            while (resultSetRezerwacja.next()) {
                // pobranie danych z rzedu
                int id = resultSetRezerwacja.getInt("id");
                String imie = resultSetRezerwacja.getString("imie");
                String nazwisko = resultSetRezerwacja.getString("nazwisko");
                String typ = resultSetRezerwacja.getString("typ");
                String email = resultSetRezerwacja.getString("email");
                double cena = resultSetRezerwacja.getDouble("cena");
                Date dataOdDate = resultSetRezerwacja.getDate("dataOd");
                Date dataDoDate = resultSetRezerwacja.getDate("dataDo");

                klient = new Klient(imie,nazwisko, email);
                pokoj = new Pokoj(typ);
                // dodanie do listy nowego obiektu

                rezerwacja.add(new Rezerwacja(id,klient, pokoj, cena, dataOdDate.toLocalDate(), dataDoDate.toLocalDate()));
                //rezerwacja.add(new Rezerwacja(new Klient("Anna", "Maj","maja@mail.com"), new Pokoj("basic"), 1000,localDate1, localDate2));

            }

        } finally {

            // zamkniecie obiektow JDBC
            close(connRezerwacja, statementRezerwacja, resultSetRezerwacja);
        }


        return rezerwacja;


    }

    public void addRezerwacja(Rezerwacja rezerwacja) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie INSERT i ustawienie jego parametrow
            String sql = "INSERT INTO rezerwacja(imie, nazwisko, typ, email, cena, dataOd, dataDo) " +
                    "VALUES(?,?,?,?,?,?,?)";

            ZoneId defaultZoneId = ZoneId.systemDefault();
            Date dataOdDate = (Date) Date.from(rezerwacja.getDataOd().atStartOfDay(defaultZoneId).toInstant());  ///czy to pojdzie?
            Date dataDoDate = (Date) Date.from(rezerwacja.getDataDo().atStartOfDay(defaultZoneId).toInstant());  ///czy to pojdzie?

            statement = conn.prepareStatement(sql);
            statement.setString(1, rezerwacja.getKlient().getImie());
            statement.setString(2, rezerwacja.getKlient().getNazwisko());
            statement.setString(3, rezerwacja.getPokoj().getTyp());
            statement.setString(4, rezerwacja.getKlient().getEmail());
            statement.setDouble(5, rezerwacja.getCena());
            statement.setDate(6, Date.valueOf(rezerwacja.getDataOd()));
            statement.setDate(7, Date.valueOf(rezerwacja.getDataOd()));


            // wykonanie zapytania
            statement.execute();


        } finally {

            close(conn, statement, null);

        }

    }

    public Rezerwacja getRezerwacja(String id) throws Exception {

        Rezerwacja rezerwacja = null;
        Connection connRezerwacja = null;
        Connection connKlient = null;
        PreparedStatement statementRezerwacja = null;
        PreparedStatement statementKlient = null;
        ResultSet resultSetRezerwacja = null;
        ResultSet resultSetKlient = null;
        Klient klient;

        try {


            int idInt = Integer.parseInt(id);
            // polaczenie z BD
            connRezerwacja = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sqlRezerwacja = "SELECT * FROM rezerwacja WHERE id =?";

            statementRezerwacja = connRezerwacja.prepareStatement(sqlRezerwacja);
            statementRezerwacja.setInt(1, idInt);


            // wykonanie zapytania
            resultSetRezerwacja = statementRezerwacja.executeQuery();


            // przetworzenie wyniku zapytania

            if (resultSetRezerwacja.next()) {
//                int pid = resultSet.getInt("id");
                String imie = resultSetRezerwacja.getString("imie");
                String nazwisko = resultSetRezerwacja.getString("nazwisko");
                String typ = resultSetRezerwacja.getString("typ");
                String email = resultSetRezerwacja.getString("email");
                double cena = resultSetRezerwacja.getDouble("cena");
                Date dataOdDate = resultSetRezerwacja.getDate("dataOd");
                Date dataDoDate = resultSetRezerwacja.getDate("dataDo");



                // utworzenie obiektu
                rezerwacja = new Rezerwacja(new Klient(imie,nazwisko,email), new Pokoj(typ),cena, dataOdDate.toLocalDate(),dataDoDate.toLocalDate());

            } else {
                throw new Exception("Could not find reservation with id " + id);
            }

            return rezerwacja;

        } finally {

            // zamkniecie obiektow JDBC
            close(connRezerwacja, statementRezerwacja, resultSetRezerwacja);
            close(connKlient, statementKlient, resultSetKlient);

        }

    }

    public void updateRezerwacja(Rezerwacja rezerwacja) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie UPDATE
            String sql = "UPDATE rezerwacja SET imie=?, nazwisko=?, typ=?, email=?, cena=?," +
                    "dataOd=?, dataDo=? WHERE id =?";

            statement = conn.prepareStatement(sql);
            statement.setString(1, rezerwacja.getKlient().getImie());
            statement.setString(2, rezerwacja.getKlient().getNazwisko());
            statement.setString(3, rezerwacja.getPokoj().getTyp());
            statement.setString(4, rezerwacja.getKlient().getEmail());
            statement.setDouble(5, rezerwacja.getCena());
            statement.setDate(6, Date.valueOf(rezerwacja.getDataOd()));
            statement.setDate(7, Date.valueOf(rezerwacja.getDataOd()));
            statement.setString(8, String.valueOf(rezerwacja.getId()));


            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void deleteRezerwacja(String id) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            int idInt = Integer.parseInt(id);

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie DELETE
            String sql = "DELETE FROM rezerwacja WHERE id =?";

            statement = conn.prepareStatement(sql);
            statement.setInt(1, idInt);

            // wykonanie zapytania
            statement.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
