package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {

    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String title;
    private final String description;

    public Position(LocalDate fromDate, LocalDate toDate, String title, String description) {
        Objects.requireNonNull(fromDate, "FromDate cannot be null");
        Objects.requireNonNull(toDate, "ToDate cannot be null");
        Objects.requireNonNull(title, "Title cannot be null");
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.title = title;
        this.description = description;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return fromDate.equals(position.fromDate) && toDate.equals(position.toDate) && title.equals(position.title)
                && Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromDate, toDate, title, description);
    }

    @Override
    public String toString() {
        return fromDate + " - " + toDate + " " + title + ": " + description;
    }
}
