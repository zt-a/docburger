package com.zhyrgal.docburger.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "TEXT")
    private String description; // Необязательно

    @Column
    private String image; // URL или путь к картинке

    // Внешний ключ на категорию
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
