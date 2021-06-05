package br.unitins.unicursos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.unicursos.application.Util;
import br.unitins.unicursos.application.ValidarCpf;
import br.unitins.unicursos.dao.DAO;
import br.unitins.unicursos.dao.UsuarioDAO;
import br.unitins.unicursos.model.Perfil;
import br.unitins.unicursos.model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable {


	private static final long serialVersionUID = -9046422907703860927L;
	private Usuario usuario = null;
	private List<Usuario> listaUsuario = null;
	private String confirmarSenha;
	private UIComponent uicCpf;
	
	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
	public String getConfirmarSenha() {
		return confirmarSenha;
	}
	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
	public UIComponent getUicCpf() {
		return uicCpf;
	}
	public void setUicCpf(UIComponent uicCpf) {
		this.uicCpf = uicCpf;
	}
	
	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
	public boolean validaCpf() {
		if (ValidarCpf.isCPF(getUsuario().getCpf()))
			return true;
		Util.addErrorMessage("O CPF é inválido");
		return false;
	}
	
	public void create() {
		if (!verificaSenha()) {
			Util.addInfoMessage("As senhas devem ser iguais");
			return;
		}
		
		DAO<Usuario> dao = new UsuarioDAO();
		
		String hashSenha = Util.hash(getUsuario().getEmail() + getUsuario().getSenha());
		getUsuario().setSenha(hashSenha);
		
		if (dao.create(getUsuario())) {
			Util.addInfoMessage("Usuário criado com sucesso!");
			limparFormulario();
		} else {
			Util.addErrorMessage("O usuário não foi criado, houve um problema com o banco de dados");
		}
	}
	
	public void update() {
		if (!verificaSenha()) {
			Util.addInfoMessage("As senhas devem ser iguais");
			return;
		}
		
		String hashSenha = Util.hash(getUsuario().getEmail() + getUsuario().getSenha());
		getUsuario().setSenha(hashSenha);
		
		DAO<Usuario> dao = new UsuarioDAO();
		if (dao.update(getUsuario())) {
			Util.addInfoMessage("Usuário alterado com sucesso!");
			limparFormulario();
		} else {
			Util.addErrorMessage("Não foi possível alterar o usuário, houve um problema com o banco de dados");
		}
	}
	
	public void delete() {
		delete(getUsuario());
	}
	
	public void delete(Usuario usu) {
		DAO<Usuario> dao = new UsuarioDAO();
		if (dao.delete(usu.getId())) {
			Util.addErrorMessage("O usuário foi deletado com sucesso!");
			limparFormulario();
		} else {
			Util.addErrorMessage("O usuário não foi deletado, houve um problema com o banco de dados");
		}
	}
	
	public void limparFormulario() {
		setUsuario(null);
		setListaUsuario(null);
	}
	
	public void edit(Usuario usu) {
		DAO<Usuario> dao = new UsuarioDAO();
		setUsuario(dao.findById(usu.getId()));
	}
	
	private boolean verificaSenha() {
		if (getUsuario().getSenha().equals(getConfirmarSenha())) {
			return true;
		}
		return false;
	}
	
	public List<Usuario> getListaUsuario(){
		if (listaUsuario == null) {
			DAO<Usuario> dao = new UsuarioDAO();
			listaUsuario = dao.findAll();
			if (listaUsuario == null) {
				listaUsuario = new ArrayList<Usuario>();
			}
		}
		return listaUsuario;
	}

}
