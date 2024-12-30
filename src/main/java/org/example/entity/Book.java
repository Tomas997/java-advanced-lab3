package org.example.entity;

import org.example.annotations.SerializableField;
import org.example.annotations.Serialized;
import org.example.annotations.SerializerGenerator;

import java.io.Serializable;

@SerializerGenerator
@Serialized()
public class Book implements Serializable {

    @SerializableField
    private String title;

    @SerializableField
    private String author;

    @SerializableField
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}