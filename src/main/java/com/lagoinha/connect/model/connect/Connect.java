package com.lagoinha.connect.model.connect;

import java.util.Objects;

import lombok.*;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Connect {

	@Id
	private String id;
	private String name;
	private String birthDate;
	private String responsible;
	private String phone;

}
