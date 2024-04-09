package com.take.home.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "color")
public class Color {
    @Id
    @Column(name = "color_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rgb;
    private String paletteColor;
    // the hex code of this color
    private String htmlCode;
    // a floating point number that shows what part of the image is in this color
    private String percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Image image;
}
