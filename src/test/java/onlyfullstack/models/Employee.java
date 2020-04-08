package onlyfullstack.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlRootElement;

public class Employee {

  private Long id;

  private String firstName;

  private String lastName;

  private Long salary;

  private String email;

  public Employee(Long id, String firstName, String lastName, Long salary, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.salary = salary;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Long getSalary() {
    return salary;
  }

  public void setSalary(Long salary) {
    this.salary = salary;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Employee employee = (Employee) o;

    if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
    if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
    if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
    if (salary != null ? !salary.equals(employee.salary) : employee.salary != null) return false;
    return email != null ? email.equals(employee.email) : employee.email == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (salary != null ? salary.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }
}
