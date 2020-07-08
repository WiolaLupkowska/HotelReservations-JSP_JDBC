import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class DBUtilClient extends DBUtil {


    private DataSource dataSource;

    public DBUtilClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }





    @Override
    public void addRezerwacja(Rezerwacja rezerwacja) throws Exception {

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            // polaczenie z BD
            conn = dataSource.getConnection();

            // zapytanie INSERT i ustawienie jego parametrow
            String sql = "INSERT INTO rezerwacja(imie, nazwisko, typ, email, cena, dataOd, dataDo) " +
                    "VALUES(?,?,?,?,?,?,?)";

//            ZoneId defaultZoneId = ZoneId.systemDefault();
//            Date dataOdDate = (Date) Date.from(rezerwacja.getDataOd().atStartOfDay(defaultZoneId).toInstant());  ///czy to pojdzie?
//            Date dataDoDate = (Date) Date.from(rezerwacja.getDataDo().atStartOfDay(defaultZoneId).toInstant());  ///czy to pojdzie?



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


    }





