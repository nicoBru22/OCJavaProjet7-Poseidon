package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface BidListRepository qui étend JpaRepository pour fournir les opérations CRUD sur l'entité {@link BidList}.
 * <p>
 * Cette interface est annotée avec {@code @Repository} pour être détectée automatiquement par Spring et
 * permettre l'injection de dépendance dans les services qui l'utilisent.
 * </p>
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {
}