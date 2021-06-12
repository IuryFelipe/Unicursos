package br.unitins.unicursos.model;

import java.time.LocalDate;
import java.util.List;

public class Compra {
	
	private Integer id;
	private LocalDate data;
	private Double totalVenda;
	private Usuario usuario;
	private List<ItemCompra> listaItemCompra;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public Double getTotalVenda() {
		return totalVenda;
	}
	public void setTotalVenda(Double totalVenda) {
		this.totalVenda = totalVenda;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<ItemCompra> getListaItemCompra() {
		return listaItemCompra;
	}
	public void setListaItemCompra(List<ItemCompra> listaItemCompra) {
		this.listaItemCompra = listaItemCompra;
	}
	
	

}
