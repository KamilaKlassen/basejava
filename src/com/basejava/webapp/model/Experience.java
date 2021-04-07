package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class Experience {

    private final Link link;
    private List<Position> positionList;

    public Experience(String name, String url, List<Position> positionList) {
        this.link = new Link(name, url);
        this.positionList = positionList;
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return link.equals(that.link) && positionList.equals(that.positionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, positionList);
    }

    @Override
    public String toString() {
        return link + " " + positionList;
    }
}
