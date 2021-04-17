package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public void write(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            //write contacts
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            //write sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getText());
                    case QUALIFICATIONS, ACHIEVEMENT -> writeList(dos, ((ListSection) section).getList(), dos::writeUTF);
                    case EDUCATION, EXPERIENCE -> writeList(dos, ((OrganizationSection) section).getExperienceList(), experience -> {
                        dos.writeUTF(experience.getLink().getName());
                        dos.writeUTF(experience.getLink().getUrl());
                        writeList(dos, experience.getPositionList(), position -> {
                            writeDate(dos, position.getStartDate());
                            writeDate(dos, position.getEndDate());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
                        });
                    });
                }
            }
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> resume.addSection(sectionType,
                            new ListSection(readList(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION -> resume.addSection(sectionType,
                            new OrganizationSection(readList(dis, () -> new Experience(
                                    new Link((dis.readUTF()), dis.readUTF()),
                                    readList(dis, () -> new Experience.Position(
                                            readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF()
                                    ))
                            ))));
                }
            }
            return resume;
        }
    }

    private interface Reader<T> {
        T doRead() throws IOException;
    }

    private interface Writer<T> {
        void doWrite(T t) throws IOException;
    }

    private <T> void writeList(DataOutputStream dos, List<T> list, Writer<T> writer) throws IOException {
        dos.writeInt(list.size());
        for (T element : list) {
            writer.doWrite(element);
        }
    }

    private <T> List<T> readList(DataInputStream dis, Reader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.doRead());
        }
        return list;
    }

    private void writeDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}