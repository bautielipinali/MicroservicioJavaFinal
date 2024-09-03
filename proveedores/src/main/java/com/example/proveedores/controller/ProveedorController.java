package com.example.proveedores.controller;

import com.example.proveedores.model.Proveedor;
import com.example.proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {

    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Proveedor> getProveedores() {
        return this.proveedorService.getProveedores();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> createProveedor(@Valid @RequestBody Proveedor proveedor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return proveedorService.createProveedor(proveedor);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProveedor(@PathVariable("id") Long id, @RequestBody Proveedor updatedProveedor) {
        return proveedorService.updateProveedor(id, updatedProveedor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteProveedor(@PathVariable("id") Long id) {
        return proveedorService.deleteProveedor(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findProveedorById(@PathVariable("id") Long id) {
        return proveedorService.findProveedor(id);
    }
}
