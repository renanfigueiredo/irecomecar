package com.lagoinha.connect.model.batismo;

import com.lagoinha.connect.model.connect.Connect;
import com.lagoinha.connect.model.worship.Status;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Batismo {
    @Id
    private String id;
    private String date;
    private String hour;
    private String descricao;
    private Status status;
    List<Connect> connects;
}
