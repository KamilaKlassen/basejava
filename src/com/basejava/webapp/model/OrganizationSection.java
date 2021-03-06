package com.basejava.webapp.model;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Experience> experienceList;

    public OrganizationSection() {
    }

    public OrganizationSection(Experience... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Experience> experienceList) {
        Objects.requireNonNull(experienceList, "This section cannot be null");
        this.experienceList = experienceList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return experienceList.equals(that.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceList);
    }
}
