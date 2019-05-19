package com.desafio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.desafio.entity.FuncaoUsuarioExterno;
import com.desafio.entity.UsuarioExterno;
import com.desafio.repository.FuncaoUsuarioExternoRepository;
import com.desafio.repository.UsuarioExternoRepository;

@SpringBootApplication
public class DesafioApiApplication {

	@Autowired
	private UsuarioExternoRepository usuarioExternoRepository;
	
	@Autowired
	private FuncaoUsuarioExternoRepository funcaoUsuarioExternoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioApiApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            FuncaoUsuarioExterno gestor = new FuncaoUsuarioExterno();
            gestor.setCodigo(1);
            gestor.setNome("Gestor");
            
            FuncaoUsuarioExterno administrador = new FuncaoUsuarioExterno();
            administrador.setCodigo(2);
            administrador.setNome("Administrador");
            
            FuncaoUsuarioExterno frenteCaixa = new FuncaoUsuarioExterno();
            frenteCaixa.setCodigo(3);
            frenteCaixa.setNome("Frente de Caixa");
            
            System.out.println("-- Salvando Função --");
            this.funcaoUsuarioExternoRepository.save(gestor);
            this.funcaoUsuarioExternoRepository.save(administrador);
            this.funcaoUsuarioExternoRepository.save(frenteCaixa);
            List<FuncaoUsuarioExterno> funcoesUsuarios = this.funcaoUsuarioExternoRepository.findAll();
            funcoesUsuarios.forEach(funcaoUsuario -> System.out.println(funcaoUsuario));
            System.out.println("Funções usuários " + funcoesUsuarios.size());
            
            UsuarioExterno usuarioExterno = new UsuarioExterno();
            usuarioExterno.setCpf("02583256580");
            usuarioExterno.setEmail("leomaciel1990@hotmail.com");
            usuarioExterno.setFuncaoUsuarioExterno(funcoesUsuarios.get(0));
            usuarioExterno.setNome("Leonardo Maciel");
            usuarioExterno.setPerfilAcesso(1);
            usuarioExterno.setSituacao("A");
            usuarioExterno.setTelefone("98159-8539");
            this.usuarioExternoRepository.save(usuarioExterno);
            
        };
    }

}
