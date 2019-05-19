package com.desafio.controller;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.desafio.entity.Response;
import com.desafio.entity.UsuarioExterno;
import com.desafio.repository.UsuarioExternoRepository;
import com.desafio.util.ValidaCPF;

@RestController
@RequestMapping("/usuarios")
public class UsuarioExternoController {
	
	@Autowired
	private UsuarioExternoRepository usuarioExternoRepository;
	
	//@GetMapping
	public List<UsuarioExterno> listar() {
		return (List<UsuarioExterno>) usuarioExternoRepository.findAll();
	}
	
	@PostMapping()
	public ResponseEntity<Response<UsuarioExterno>> incluir(HttpServletRequest request, @RequestBody UsuarioExterno usuarioExterno,
			BindingResult result) {
		Response<UsuarioExterno> response = new Response<UsuarioExterno>();
		try {
			
			validarIncluir(usuarioExterno, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			UsuarioExterno pagamentoPersisted = (UsuarioExterno) usuarioExternoRepository.save(usuarioExterno);
			response.setData(pagamentoPersisted);
			response.setMensagem("Cadastro efetuado com sucesso!");
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validarIncluir(UsuarioExterno usuarioExterno, BindingResult result) {
		
		// FE2. CPF Inválido
		if (!ValidaCPF.isCPF(usuarioExterno.getCpf())){
			result.addError(new ObjectError("Usuario",
					"Operação não realizada. CPF digitado é inválido."));
			return;
		}
		// FE1. Usuário já incluído  Operação não realizada. Usuário já incluído.
		if (usuarioExternoRepository.findByCpf(usuarioExterno.getCpf()) != null ){
			result.addError(new ObjectError("Usuario",
					"Operação não realizada. Usuário já incluído."));
			return;
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Integer id) {
		Response<String> response = new Response<String>();
		Optional<UsuarioExterno> usuarioExternoOptional = usuarioExternoRepository.findById(id);
		UsuarioExterno usuarioExterno = usuarioExternoOptional.get();
		if (usuarioExterno == null) {
			response.getErrors().add("Registro não encontrado id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		usuarioExternoRepository.deleteById(id);
		response.setMensagem("Exclusão efetuada com sucesso.");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<UsuarioExterno>> buscar(@PathVariable Integer id) {
		Response<UsuarioExterno> response = new Response<UsuarioExterno>();
		Optional<UsuarioExterno> usuarioOptional =  usuarioExternoRepository.findById(id);
		UsuarioExterno usuarioExterno = new UsuarioExterno();
		usuarioExterno = usuarioOptional.get();
		if (usuarioExterno == null) {
			response.getErrors().add("Registro não encontrado id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(usuarioExterno);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping
    public  ResponseEntity<Response<Page<UsuarioExterno>>> buscar(
    		 							@RequestParam(value="nome", required=false) String nome,
    		 							@RequestParam(value="situacao", required=false) String situacao,
    		 							@RequestParam(value="perfil", required=false) Integer perfil,
    		 							@RequestParam(value="pagina", required=false) Integer pagina, 
    		 							@RequestParam(value="limite", required=false) Integer limite
    		 							) {
		
		List<UsuarioExterno> listUsuarios =  usuarioExternoRepository.findAll();
		nome = nome == null ? "" : nome;
		situacao = situacao == null ? "" : situacao;
		pagina = pagina == null ? 0 : pagina;
		limite = limite == null ? listUsuarios.size() : limite; 
		
		Response<Page<UsuarioExterno>> response = new Response<Page<UsuarioExterno>>();
		Page<UsuarioExterno> usuarios = null;
		
			Pageable pages = PageRequest.of(pagina, limite);
			
			if(perfil == null) {
				usuarios = usuarioExternoRepository
						.findByNomeIgnoreCaseContainingAndSituacaoIgnoreCaseContaining(nome,situacao,pages);
			}else {
				usuarios = usuarioExternoRepository
						.findByNomeIgnoreCaseContainingAndSituacaoIgnoreCaseContainingAndFuncaoUsuarioExternoCodigo(nome,situacao,perfil,pages);		
			}
		response.setData(usuarios);
		return ResponseEntity.ok(response);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<UsuarioExterno>> atualizar(@PathVariable("id") Integer id, HttpServletRequest request, @RequestBody UsuarioExterno usuarioExterno,
			BindingResult result) {
		Response<UsuarioExterno> response = new Response<UsuarioExterno>();
		try {
			Optional<UsuarioExterno> usuOptional = usuarioExternoRepository.findById(id);
			UsuarioExterno usuExterno = usuOptional.get();
			
			usuExterno.setCpf(usuarioExterno.getCpf());
			usuExterno.setEmail(usuarioExterno.getEmail());
			usuExterno.setNome(usuarioExterno.getNome());
			usuExterno.setFuncaoUsuarioExterno(usuarioExterno.getFuncaoUsuarioExterno());
			usuExterno.setPerfilAcesso(usuarioExterno.getPerfilAcesso());
			usuExterno.setSituacao(usuarioExterno.getSituacao());
			usuExterno.setTelefone(usuarioExterno.getTelefone());
			
			UsuarioExterno usuarioPersistido = usuarioExternoRepository.save(usuExterno);
			response.setData(usuarioPersistido);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
}
