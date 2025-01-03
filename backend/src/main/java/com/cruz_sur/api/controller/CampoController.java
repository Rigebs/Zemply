package com.cruz_sur.api.controller;

import com.cruz_sur.api.dto.CampoDTO;
import com.cruz_sur.api.dto.CamposHomeDTO;
import com.cruz_sur.api.dto.SedeConCamposDTO;
import com.cruz_sur.api.model.Campo;
import com.cruz_sur.api.service.ICampoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/campos")
@AllArgsConstructor
public class CampoController {

    private final ICampoService campoService;

    @GetMapping
    public ResponseEntity<List<CampoDTO>> all() {
        List<CampoDTO> campos = campoService.all();
        return new ResponseEntity<>(campos, HttpStatus.OK);
    }

    @GetMapping("/compania")
    public ResponseEntity<List<CampoDTO>> campos() {
        List<CampoDTO> campos = campoService.campos();
        return new ResponseEntity<>(campos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> byId(@PathVariable Long id) {
        Optional<CampoDTO> campoDTO = campoService.byId(id);
        return campoDTO.<ResponseEntity<Object>>map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Campo no encontrado", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{usuarioId}/with-sede")
    public ResponseEntity<List<SedeConCamposDTO>> getCamposByUsuarioIdWithSede(@PathVariable Long usuarioId) {
        List<SedeConCamposDTO> campos = campoService.findByUsuarioIdWithSede(usuarioId);
        return campos.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(campos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody Campo campo) {
        campoService.save(campo);
        return new ResponseEntity<>("Campo creado con éxito", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Campo campo) {
        campoService.update(id, campo);
        return new ResponseEntity<>("Campo actualizado con éxito", HttpStatus.OK);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<String> changeStatus(@PathVariable Long id, @PathVariable Integer status) {
        campoService.changeStatus(id, status);
        String statusMessage = status == 1 ? "activado" : "desactivado";
        return new ResponseEntity<>("Campo " + statusMessage + " con éxito", HttpStatus.OK);
    }
    @GetMapping("/available-sedes")
    public ResponseEntity<List<CamposHomeDTO>> getAvailableSedesAndCampos(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam String distritoNombre,
            @RequestParam String provinciaNombre,
            @RequestParam String departamentoNombre,
            @RequestParam String fechaReserva,
            @RequestParam(required = false, defaultValue = "") String tipoDeporteNombre) {

        List<CamposHomeDTO> combinedResults = campoService.getAvailableSedesAndCamposWithSede(
                usuarioId, distritoNombre, provinciaNombre, departamentoNombre, fechaReserva, tipoDeporteNombre);

        return combinedResults.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(combinedResults, HttpStatus.OK);
    }

}
