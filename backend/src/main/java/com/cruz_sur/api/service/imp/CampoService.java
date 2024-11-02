package com.cruz_sur.api.service.imp;

import com.cruz_sur.api.model.Campo;
import com.cruz_sur.api.model.User;
import com.cruz_sur.api.repository.CampoRepository;
import com.cruz_sur.api.repository.UserRepository;
import com.cruz_sur.api.service.ICampoService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CampoService implements ICampoService {

    private final CampoRepository campoRepository;
    private final UserRepository userRepository; // Añade esto

    @Override
    public Campo save(Campo campo) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        campo.setUsuarioCreacion(authenticatedUsername);
        campo.setFechaCreacion(LocalDateTime.now());
        campo.setEstado('1');

        // Obtiene el usuario autenticado y establece en el campo
        User usuario = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        campo.setUsuario(usuario);

        return campoRepository.save(campo);
    }

    @Override
    public Campo update(Long id, Campo campo) {
        Campo campoExistente = campoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campo no encontrado"));
        campoExistente.setNombre(campo.getNombre());
        campoExistente.setPrecio(campo.getPrecio());
        campoExistente.setDescripcion(campo.getDescripcion());

        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        campoExistente.setUsuarioModificacion(authenticatedUsername);
        campoExistente.setFechaModificacion(LocalDateTime.now());

        return campoRepository.save(campoExistente);
    }

    @Override
    public List<Campo> all() {
        return campoRepository.findAll();
    }

    @Override
    public Campo changeStatus(Long id, Integer status) {
        Campo campo = campoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campo no encontrado"));

        campo.setEstado(status == 1 ? '1' : '0');
        return campoRepository.save(campo);
    }

    @Override
    public Optional<Campo> byId(Long id) {
        return campoRepository.findById(id);
    }
}
