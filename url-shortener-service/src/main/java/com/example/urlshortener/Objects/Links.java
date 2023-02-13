package com.example.urlshortener.Objects;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "LINKS")
public class Links {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String url;
    @NotNull
    private String shortURL;
    @NotNull
    private LocalDateTime createDate;
    @NotNull
    private String unit;
    @NotNull
    private Integer amount;

}
