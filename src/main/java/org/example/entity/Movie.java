package org.example.entity;

import org.example.annotations.SerializableField;
import org.example.annotations.Serialized;
import org.example.annotations.SerializerGenerator;

@SerializerGenerator
@Serialized()
public class Movie {

    @SerializableField(name = "title")
    private String title;

    @SerializableField(name = "director")
    private String director;

    @SerializableField
    private int releaseYear;

    public Movie(String title, String director, int releaseYear) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
}