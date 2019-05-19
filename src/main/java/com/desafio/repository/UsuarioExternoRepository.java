package com.desafio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.desafio.entity.UsuarioExterno;

public interface UsuarioExternoRepository extends JpaRepository<UsuarioExterno, Integer> {

	UsuarioExterno findByCpf(
			String cpf);
	
	Page<UsuarioExterno> findByNomeIgnoreCaseContainingAndSituacaoIgnoreCaseContainingAndFuncaoUsuarioExternoCodigo(
			String nome, String situacao,Integer codigo, Pageable pages);
	
	Page<UsuarioExterno> findByNomeIgnoreCaseContainingAndSituacaoIgnoreCaseContaining(
			String nome, String situacao, Pageable pages);
	

}
