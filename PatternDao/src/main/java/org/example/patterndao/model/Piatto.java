package org.example.patterndao.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Piatto {
    private String nome;
    private String ingredienti;
}