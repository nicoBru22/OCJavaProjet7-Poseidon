package com.nnk.springboot.repositories;


import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface UserRepository qui étend JpaRepository pour fournir les opérations CRUD sur l'entité {@link User}.
 * <p>
 * Cette interface est annotée avec {@code @Repository} pour être détectée automatiquement par Spring et
 * permettre l'injection de dépendance dans les services qui l'utilisent.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
