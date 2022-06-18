package guru.qa;

public enum EnumArtists {
    JAO("The Girl From Ipanema", "The Girl From Ipanema (Garota De Ipanema)" + " " + "João Gilberto," + " " + "Stan Getz"),
    ASTRUD("Corcovado", "Corcovado" + " " + "João Gilberto," + " " + "Astrud Gilberto");
    public final String search;
    public final String result;

    EnumArtists(String search, String result) {
        this.search = search;
        this.result = result;

    }
}
