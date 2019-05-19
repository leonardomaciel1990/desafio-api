package com.desafio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario_externo")
public class UsuarioExterno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="nu_id")
	private Integer id;
	@Column(name="nu_cpf")
	private String cpf;
	@Column(name="no_usuario")
	private String nome;
	@Column(name="de_email")
	private String email;
	@Column(name="ic_situacao")
	private String situacao;
	@Column(name="ic_perfil_acesso")
	private Integer perfilAcesso;
	@ManyToOne
	@JoinColumn(name="co_funcao")
	private FuncaoUsuarioExterno funcaoUsuarioExterno;
	@Column(name="nu_telefone")
	private String telefone;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Integer getPerfilAcesso() {
		return perfilAcesso;
	}
	public void setPerfilAcesso(Integer perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	public FuncaoUsuarioExterno getFuncaoUsuarioExterno() {
		return funcaoUsuarioExterno;
	}
	public void setFuncaoUsuarioExterno(FuncaoUsuarioExterno funcaoUsuarioExterno) {
		this.funcaoUsuarioExterno = funcaoUsuarioExterno;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
