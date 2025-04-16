package senai.oBoticario_db.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import senai.oBoticario_db.model.CategoriasProduto;
import senai.oBoticario_db.service.CategoriasProdutoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@Api(tags = "Gerenciamento de Categorias de Produto") // Descrição geral do controlador
public class CategoriasProdutoController {

    @Autowired
    private CategoriasProdutoService categoriasProdutoService;

    @GetMapping
    @ApiOperation(value = "Listar todas as categorias", 
                  notes = "Retorna uma lista com todas as categorias de produto cadastradas.")
    public List<CategoriasProduto> listarTodos() {
        return categoriasProdutoService.listarTodos();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar categoria por ID", 
                  notes = "Retorna os detalhes de uma categoria específica com base no ID fornecido.")
    public ResponseEntity<CategoriasProduto> buscarPorId(
            @ApiParam(value = "ID da categoria", required = true) 
            @PathVariable long id) {
        Optional<CategoriasProduto> categoria = categoriasProdutoService.buscarPorId(id);
        return categoria.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation(value = "Criar nova categoria", 
                  notes = "Cria uma nova categoria de produto com os dados fornecidos.")
    public ResponseEntity<CategoriasProduto> criar(
            @ApiParam(value = "Dados da nova categoria", required = true) 
            @RequestBody CategoriasProduto novaCategoria) {
        CategoriasProduto salva = categoriasProdutoService.salvar(novaCategoria);
        return ResponseEntity.ok(salva);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar categoria", 
                  notes = "Atualiza os dados de uma categoria existente com base no ID fornecido.")
    public ResponseEntity<CategoriasProduto> atualizar(
            @ApiParam(value = "ID da categoria a ser atualizada", required = true) 
            @PathVariable long id, 
            @ApiParam(value = "Novos dados da categoria", required = true) 
            @RequestBody CategoriasProduto atualizada) {
        try {
            CategoriasProduto categoriaAtualizada = categoriasProdutoService.atualizar(id, atualizada);
            return ResponseEntity.ok(categoriaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar categoria", 
                  notes = "Exclui uma categoria de produto com base no ID fornecido.")
    public ResponseEntity<Void> deletar(
            @ApiParam(value = "ID da categoria a ser deletada", required = true) 
            @PathVariable long id) {
        categoriasProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
