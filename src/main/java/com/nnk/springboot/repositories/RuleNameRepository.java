package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface RuleNameRepository qui étend JpaRepository pour fournir les opérations CRUD sur l'entité {@link RuleName}.
 * <p>
 * Cette interface est annotée avec {@code @Repository} pour être détectée automatiquement par Spring et
 * permettre l'injection de dépendance dans les services qui l'utilisent.
 * </p>
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
