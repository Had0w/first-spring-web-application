package com.geekbrains.demoboot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Data
@NoArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    public Item(String title) {
        this.title = title;
    }
}
