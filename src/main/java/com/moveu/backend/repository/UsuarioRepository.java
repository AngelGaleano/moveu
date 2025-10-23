package com.moveu.backend.repository;

import com.moveu.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca usuario por email (opcional para evitar nulls)
    Optional<Usuario> findByEmail(String email);


}
