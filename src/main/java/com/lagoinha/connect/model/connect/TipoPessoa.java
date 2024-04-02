package com.lagoinha.connect.model.connect;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum TipoPessoa {
    RECOMECO("Recomeço"),
    MEMBRO("Membro"), 
    VOLUNTARIO("Voluntário"),
    KIDS("Criança"),
    BATIZAR("Pronto para o Batismo");

    private String descricao;

}
