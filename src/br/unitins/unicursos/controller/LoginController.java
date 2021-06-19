package br.unitins.unicursos.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.unitins.unicursos.application.Session;
import br.unitins.unicursos.application.Util;
import br.unitins.unicursos.dao.UsuarioDAO;
import br.unitins.unicursos.model.Perfil;
import br.unitins.unicursos.model.Usuario;

@Named
@RequestScoped
public class LoginController {
	private Usuario usuario;

	public String autenticar() {
		UsuarioDAO dao = new UsuarioDAO();
		System.out.println("Dados informados: "+getUsuario().getEmail()+" e senha: "+getUsuario().getSenha());
		String hash = Util.hash(getUsuario().getSenha() + getUsuario().getEmail());
		System.out.println("Hash: "+hash);
		getUsuario().setSenha(hash);
		Usuario usuarioLogado = dao.Login(getUsuario());
		if (usuarioLogado != null) {
			if(usuarioLogado.isAtivo()) {
				System.out.println("Usuário autenticado com sucesso.");
				Session.getInstance().set("usuarioLogado", usuarioLogado);
				if(usuarioLogado.getPerfil().equals(Perfil.ADMINISTRADOR)  || usuarioLogado.getPerfil().equals(Perfil.INSTRUTOR)) {
					return "administrativo.xhtml";
				}else {
					return "catalogo.xhtml";
				}
				//return "index.xhtml";
			}else {
				Util.addErrorMessage("Usuário inativo.");
			}
		}else {
			Util.addErrorMessage("Login ou senha inválidos.");
		}
		return null;
		
	}

	public Usuario getUsuario() {
		if(usuario==null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void limpar() {
		usuario = null;
	}
}
