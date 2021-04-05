package com.geekbrains.demoboot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
