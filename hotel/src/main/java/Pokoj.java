public class Pokoj {

    String typ;


    public Pokoj(String typ) {
        this.typ = typ;
    }

    @Override
    public String toString() {
        return "Pokoj{" +
                "typ='" + typ + '\'' +
                '}';
    }


    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
