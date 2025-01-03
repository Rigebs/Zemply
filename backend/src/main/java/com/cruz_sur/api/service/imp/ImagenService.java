package com.cruz_sur.api.service.imp;

import com.cruz_sur.api.model.Imagen;
import com.cruz_sur.api.repository.ImagenRepository;
import com.cruz_sur.api.service.ICloudinaryService;
import com.cruz_sur.api.service.IImagenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ImagenService implements IImagenService {
    private final ICloudinaryService iCloudinaryService;
    private final ImagenRepository imageRepository;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Imagen uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no debe estar vacío.");
        }

        Map uploadResult = iCloudinaryService.upload(file);
        String imageUrl = (String) uploadResult.get("url");
        String imageId = (String) uploadResult.get("public_id");

        String name = file.getOriginalFilename();
        if (name == null) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo.");
        }

        Imagen image = new Imagen(name, imageUrl.replace("http://","https://"), imageId);
        image.setEstado('1');

        String authenticatedUsername = getAuthenticatedUsername();
        image.setUsuarioCreacion(authenticatedUsername); // Establecer usuario de creación
        image.setFechaCreacion(LocalDateTime.now()); // Establecer fecha de creación

        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Imagen imagen) throws IOException {
        iCloudinaryService.delete(imagen.getImageId());
        imageRepository.deleteById(imagen.getId());
    }

    @Override
    public Imagen findById(Long id) throws IOException {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public List<Imagen> findAll() throws IOException {
        return imageRepository.findAll();
    }

    @Override
    public List<Imagen> findAllActive() throws IOException {
        return imageRepository.findByEstado('1');
    }

    @Override
    public Imagen updateImage(Long id, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no debe estar vacío.");
        }

        Imagen existingImage = imageRepository.findById(id)
                .orElseThrow(() -> new IOException("Imagen no encontrada"));

        // Aquí se eliminará la imagen anterior de Cloudinary
        iCloudinaryService.delete(existingImage.getImageId());

        // Subir la nueva imagen
        Map uploadResult = iCloudinaryService.upload(file);
        String newImageUrl = (String) uploadResult.get("url");
        String newImageId = (String) uploadResult.get("public_id");

        existingImage.setImageUrl(newImageUrl);
        existingImage.setImageId(newImageId);
        existingImage.setName(file.getOriginalFilename());

        String authenticatedUsername = getAuthenticatedUsername();
        existingImage.setUsuarioModificacion(authenticatedUsername); // Establecer usuario de modificación
        existingImage.setFechaModificacion(LocalDateTime.now()); // Establecer fecha de modificación

        return imageRepository.save(existingImage);
    }

    @Override
    public Imagen changeStatus(Long id, Integer status) {
        Imagen imagen = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));

        imagen.setEstado(status == 1 ? '1' : '0');
        return imageRepository.save(imagen);
    }
}
