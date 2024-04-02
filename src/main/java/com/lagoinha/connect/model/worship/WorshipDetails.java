package com.lagoinha.connect.model.worship;

import com.lagoinha.connect.model.voluntario.Escala;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WorshipDetails {

    private Integer totalPessoasPresentes;
    private Integer totalPessoasOnline;
    private Integer totalVoluntarios;
    private Integer totalKids;
    private Integer totalRecomecosOnline;
    private Integer totalRecomecosPresentes;
    private Escala escala;

}
