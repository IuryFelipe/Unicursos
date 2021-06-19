package br.unitins.unicursos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.unicursos.application.Session;
import br.unitins.unicursos.application.Util;
import br.unitins.unicursos.dao.CompraDAO;
import br.unitins.unicursos.model.Compra;
import br.unitins.unicursos.model.Curso;
import br.unitins.unicursos.model.ItemCompra;
import br.unitins.unicursos.model.Usuario;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4013295505066700387L;
	
	private Compra compra;
	
	public void finalizarCompra() {
		Usuario usuario = (Usuario) Session.getInstance().get("usuarioLogado");
		if (usuario == null) {
			Util.addErrorMessage("Você precisa estar autenticado para finalizar a compra, por favor, faça o login.");
			return;
		}
		
		if (getCompra().getListaItemCompra() == null || 
			getCompra().getListaItemCompra().isEmpty()) {
			Util.addErrorMessage("O carrinho está vazio.");
			return;
		}
		
		getCompra().setUsuario(usuario);
		
		CompraDAO dao = new CompraDAO();
		if (dao.create(getCompra())) {
			Util.addInfoMessage("Seu pedido foi realizado com sucesso!!");
			Session.getInstance().set("carrinho", null);
			setCompra(null);
		} else {
			Util.addErrorMessage("Houve um problema ao tentar finalizar a compra, "
					+ "por favor tente mais tarde. Se o problema persistir, contate o administrador da página");
		}
	}
	
	public void remover(Curso curso) {
		@SuppressWarnings("unchecked")
		List<ItemCompra> carrinho = (List<ItemCompra>) Session.getInstance().get("carrinho");
		
		
		ItemCompra iv = new ItemCompra();
		iv.setCurso(curso);
		iv.setValorUnitario(curso.getValor());
	
		if (carrinho.contains(iv)) {
			int index = carrinho.indexOf(iv);
			int quantidade = carrinho.get(index).getQuantidade();
			if (quantidade == 1)
				carrinho.remove(index);
			else
				carrinho.get(index).setQuantidade(-- quantidade );
			
		}
		
		Session.getInstance().set("carrinho", carrinho);
		
		Util.addInfoMessage("Item adicionado no carrinho.");
		
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Compra getCompra() {
		if (compra == null)
			compra = new Compra();
		
		@SuppressWarnings("unchecked")
		List<ItemCompra> carrinho = (List<ItemCompra>) Session.getInstance().get("carrinho");
		
		if (carrinho == null) 
			compra.setListaItemCompra(new ArrayList<ItemCompra>());
		else 
			compra.setListaItemCompra(carrinho);
		
		return compra;
	}

}
