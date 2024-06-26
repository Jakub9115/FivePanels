package at.spengergasse.fivepanels.model.doctor;

import at.spengergasse.fivepanels.model.common.Image;

import static at.spengergasse.fivepanels.foundation.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Profile {

    // not null, min 1 character, max 128 characters
    private String name;
    // not null, min 1 character, max 128 characters
    private String title;
    // not null, not blank
    private Country location;
    // not null
    private Set<Specialization> tags;
    // not null
    private Integer rating;
    //not null
    private Image avatar;

    public Country getLocation() {
        return location;
    }

    public Set<Specialization> getTags() {
        return tags;
    }

    public Integer getRating() {
        return rating;
    }

    public Profile(String name, String title, String location, String... tags) {
        setName(name);
        setTitle(title);
        setLocation(location);
        setTags(tags);
        this.rating = 0;
    }

    public void setRating(Integer rating) {
        isNotNull(rating, "rating");
        this.rating += rating;
    }

    public void setName(String name) {
        isGreaterThanOrEqual(name.length(), "name", 1, "1");
        hasMaxLength(name, 129, "name");
        this.name = name;
    }

    public void setTitle(String title) {
        isGreaterThanOrEqual(title.length(), "title", 1, "1");
        hasMaxLength(title, 129, "title");
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLocation(String location) {
        isNotBlank(location, "location");

        this.location = new Country(location);
    }

    public void setTags(String... tags) {
        this.tags = new HashSet<>();
        Arrays.stream(tags).forEach(this::addTag);
    }

    public void addTag(String tag) {
        isNotNull(tag, "tag");
        Specialization specialization = new Specialization(tag);
        if (tags.contains(specialization))
            throw new UserException(STR."addTag(): User already has this tag");
        tags.add(specialization);
    }

    public void removeTag(String tag) {
        isNotNull(tag, "tag");
        tags.remove(new Specialization(tag));
    }

    public String getName() {
        return name;
    }

    public String getTitleAndName() {
        return STR."\{title} \{name}";
    }

    public void addRating(int rating) {
        if (rating < 0)
            throw new UserException(STR."addRating(): can not add negative rating");
        this.rating += rating;
    }

    public void setAvatar(Image avatar) {
        isNotNull(avatar, "avatar");
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return STR."Profile{name='\{name}\{'\''}, title='\{title}\{'\''}, location=\{location}, tags=\{tags}, rating=\{rating}, avatar=\{avatar}\{'}'}";
    }
}
