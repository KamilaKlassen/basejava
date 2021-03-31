package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {

    private final Link link;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String title;
    private final String description;

    public Experience(Link link, LocalDate fromDate, LocalDate toDate, String title, String description) {
        Objects.requireNonNull(link,"This section cannot be null");
        Objects.requireNonNull(fromDate,"This section cannot be null");
        Objects.requireNonNull(toDate,"This section cannot be null");
        Objects.requireNonNull(title,"This section cannot be null");
        Objects.requireNonNull(description,"This section cannot be null");
        this.link = link;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.title = title;
        this.description = description;
    }

    public Link getLink() {
        return link;
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
        Experience that = (Experience) o;
        return link.equals(that.link) && fromDate.equals(that.fromDate) && toDate.equals(that.toDate) &&
                title.equals(that.title) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, fromDate, toDate, title, description);
    }

    @Override
    public String toString() {
        return link + " " + fromDate + " - " + toDate + "\n" + title + ": " + description;
    }
}
