package com.lagoinha.connect.model.worship;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Worship {

	@Id
	private String id;
	private String date;
	private String hour;
	private String descricao;
	private Status status;
	private List<ConnectBracelet> connectBracelet;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<ConnectBracelet> getConnectBracelet() {
		return connectBracelet;
	}
	public void setConnectBracelet(List<ConnectBracelet> connectBracelet) {
		this.connectBracelet = connectBracelet;
	}
	
	@Override
	public String toString() {
		return "Worship [id=" + id + ", date=" + date + ", hour=" + hour + ", status=" + status + ", connectBracelet="
				+ connectBracelet + "]";
	}
	
	
	
	
}
