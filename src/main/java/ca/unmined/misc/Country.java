package ca.unmined.misc;

public class Country {

    public final String name;
    public final String countryCode;
    public String updated;
    public long confirmed;
    public long dead;
    public long recovered;

    public Country(String name,
                   String code,
                   long confirmed,
                   long dead,
                   long recovered,
                   String updated) {
        this.name = name;
        this.countryCode = code;
        this.confirmed = confirmed;
        this.dead = dead;
        this.recovered = recovered;
        this.updated = updated;
    }

    public void update(long confirmed, long dead, long recovered) {
        this.confirmed = confirmed;
        this.dead = dead;
        this.recovered = recovered;
    }

}
