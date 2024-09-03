package com.example.proveedores.service;

import com.example.proveedores.model.Proveedor;
import com.example.proveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> getProveedores() {
        return proveedorRepository.findAll();
    }

    public ResponseEntity<Object> findProveedor(Long id) {
        Optional<Proveedor> existingProveedorOptional = proveedorRepository.findById(id);
        if (existingProveedorOptional.isPresent()) {
            Proveedor existingProveedor = existingProveedorOptional.get();
            return new ResponseEntity<>(existingProveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo encontrar el proveedor", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> createProveedor(Proveedor proveedor) {
        proveedorRepository.save(proveedor);
        return new ResponseEntity<>("Proveedor creado", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateProveedor(Long id, Proveedor updatedProveedor) {
        Optional<Proveedor> existingProveedorOptional = proveedorRepository.findById(id);
        if (existingProveedorOptional.isPresent()) {
            Proveedor existingProveedor = existingProveedorOptional.get();
            existingProveedor.setNombre(updatedProveedor.getNombre());
            existingProveedor.setCuit(updatedProveedor.getCuit());
            existingProveedor.setDireccion(updatedProveedor.getDireccion());

            proveedorRepository.save(existingProveedor);

            return new ResponseEntity<>("Proveedor actualizado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo encontrar el proveedor", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> deleteProveedor(Long id) {
        Optional<Proveedor> existingProveedorOptional = proveedorRepository.findById(id);
        if (existingProveedorOptional.isPresent()) {
            proveedorRepository.deleteById(id);
            return new ResponseEntity<>("Se eliminó el proveedor correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo encontrar el proveedor", HttpStatus.NOT_FOUND);
        }
    }
}
