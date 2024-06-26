package at.spengergasse.fivepanels.model.doctor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Country {
    // not null
    private static Set<String> countries;

    private String country;

    public Country(String country) {
        setCountry(country);
    }

    public void setCountry(String country) {
        if (!countries.contains(country))
            throw new UserException("setCountries(): country does not exist");
        this.country = country;
    }

    public static Set<String> getCountries() {

        return countries;
    }

    static {
        String path = "src/main/resources/countries.txt";
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            countries = stream.collect(Collectors.toSet());

        } catch (IOException e) {
            throw new UserException(e.getMessage() + STR."error reading file \{path}");
        }
    }

    @Override
    public String toString() {
        return country;
    }
}
