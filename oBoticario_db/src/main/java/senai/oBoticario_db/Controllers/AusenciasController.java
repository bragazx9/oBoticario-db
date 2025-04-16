package senai.oBoticario_db.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import senai.oBoticario_db.model.Ausencias;
import senai.oBoticario_db.service.AusenciasService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ausencias")
@Api(tags = "Gerenciamento de Ausências")  // Descrição geral da API
public class AusenciasController {

    @Autowired
    private AusenciasService ausenciasService;

    @GetMapping
    @ApiOperation(value = "Listar todas as ausências", 
                  notes = "Retorna uma lista com todas as ausências cadastradas no sistema.")
    public List<Ausencias> listarTodos() {
        return ausenciasService.listarTodos();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar ausência por ID", 
                  notes = "Retorna os detalhes de uma ausência específica, dado o seu ID.")
    public ResponseEntity<Ausencias> buscarPorId(
            @ApiParam(value = "ID da ausência", required = true) @PathVariable long id) {
        Optional<Ausencias> ausencia = ausenciasService.buscarPorId(id);
        return ausencia.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Criar nova ausência", 
                  notes = "Cria uma nova ausência no sistema com as informações fornecidas.")
    public ResponseEntity<Ausencias> criar(
            @ApiParam(value = "Objeto contendo os dados da nova ausência", required = true) 
            @RequestBody Ausencias novaAusencia) {
        Ausencias ausenciaSalva = ausenciasService.salvar(novaAusencia);
        return ResponseEntity.ok(ausenciaSalva);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar ausência", 
                  notes = "Atualiza os dados de uma ausência existente, dado o seu ID.")
    public ResponseEntity<Ausencias> atualizar(
            @ApiParam(value = "ID da ausência a ser atualizada", required = true) 
            @PathVariable long id, 
            @ApiParam(value = "Objeto contendo os novos dados da ausência", required = true) 
            @RequestBody Ausencias ausenciaAtualizada) {
        try {
            Ausencias atualizado = ausenciasService.atualizar(id, ausenciaAtualizada);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar ausência", 
                  notes = "Deleta uma ausência existente, dado o seu ID.")
    public ResponseEntity<Void> deletar(
            @ApiParam(value = "ID da ausência a ser deletada", required = true) 
            @PathVariable long id) {
        ausenciasService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}