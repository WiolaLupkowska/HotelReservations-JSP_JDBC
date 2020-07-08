import java.time.LocalDate;

public class Rezerwacja {

    private int id;
    private Klient klient;
    private Pokoj pokoj;
    private double cena;
    private LocalDate dataOd;
    private LocalDate dataDo;

    public Rezerwacja(Klient klient, Pokoj pokoj, double cena, LocalDate dataOd, LocalDate dataDo) {

        this.klient = klient;
        this.pokoj = pokoj;
        this.cena = cena;
        this.dataOd = dataOd;
        this.dataDo = dataDo;
    }

    public Rezerwacja(int id, Klient klient, Pokoj pokoj, double cena, LocalDate dataOd, LocalDate dataDo) {
this.id=id;
        this.klient = klient;
        this.pokoj = pokoj;
        this.cena = cena;
        this.dataOd = dataOd;
        this.dataDo = dataDo;
    }

    @Override
    public String toString() {
        return "Rezerwacja{" +
                "id=" + id +
                ", klient=" + klient +
                ", pokoj=" + pokoj +
                ", cena=" + cena +
                ", dataOd=" + dataOd +
                ", dataDo=" + dataDo +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Pokoj getPokoj() {
        return pokoj;
    }

    public void setPokoj(Pokoj pokoj) {
        this.pokoj = pokoj;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public LocalDate getDataOd() {
        return dataOd;
    }

    public void setDataOd(LocalDate dataOd) {
        this.dataOd = dataOd;
    }

    public LocalDate getDataDo() {
        return dataDo;
    }

    public void setDataDo(LocalDate dataDo) {
        this.dataDo = dataDo;
    }
}