package com.rest.api.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Muhammad Atta
 *
 */

@Entity
@Table(name = "employee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column(name = "name", nullable = false, unique = true)
    @Column(name = "name")
    private String name;

    @Column(name = "state", columnDefinition = "default 'ADDED'")
    @Enumerated(value = EnumType.STRING)
    private EmployeeEvents state  = EmployeeEvents.ADDED;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(EmployeeEvents state) {
        this.state = state;
    }

    public EmployeeEvents getState(EmployeeEvents state) {
        return state;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && state == employee.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, state);
    }


}
