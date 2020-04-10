package onlyfullstack.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;

public class Student {

    private Long rollNumber;
    private String name;
    private String email;
    private String collegeName;

    public Student(Long rollNumber, String name, String email, String collegeName) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.email = email;
        this.collegeName = collegeName;
    }

    public Long getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(Long rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (email != null ? !email.equals(student.email) : student.email != null) return false;
        return collegeName != null ? collegeName.equals(student.collegeName) : student.collegeName == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (collegeName != null ? collegeName.hashCode() : 0);
        return result;
    }
}
