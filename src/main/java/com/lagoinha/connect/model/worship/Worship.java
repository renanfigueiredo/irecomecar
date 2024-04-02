package com.lagoinha.connect.model.worship;

import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Worship {

	@Id
	private String id;
	private String date;
	private String hour;
	private String descricao;
	private Status status;
	private List<ConnectBracelet> connectBracelet;
	private WorshipDetails worshipDetails;
}
