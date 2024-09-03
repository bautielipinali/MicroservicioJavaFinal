package com.example.proveedores.controller;

import com.example.proveedores.security.ProveedoresAutenticationReq;
import com.example.proveedores.security.ProveedoresJWTUtilService;
import com.example.proveedores.security.ProveedoresToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SessionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private ProveedoresJWTUtilService productsJWTUtilService;
    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<ProveedoresToken> authenticate(@RequestBody ProveedoresAutenticationReq productAuthenticationReq) {
        logger.info("Autenticando al usuario {}", productAuthenticationReq.getUsuario());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(productAuthenticationReq.getUsuario(), productAuthenticationReq.getClave()));

        final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(productAuthenticationReq.getUsuario());

        final String jwt = productsJWTUtilService.generateToken(userDetails);

        return ResponseEntity.ok(new ProveedoresToken(jwt));
    }

}
