package com.nnk.springboot.services.implService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.ICurvePointService;

/**
 * Service pour la gestion des {@link CurvePoint}.
 * <p>
 * Cette classe fournit des méthodes pour récupérer, ajouter, mettre à jour et supprimer des CurvePoints.
 * </p>
 */
@Service
public class CurvePointServiceImpl implements ICurvePointService {
	
    private static final Logger logger = LogManager.getLogger(CurvePointServiceImpl.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Récupère un {@link CurvePoint} par son ID.
     * 
     * @param id L'identifiant du CurvePoint.
     * @return Le CurvePoint correspondant.
     * @throws RuntimeException si le CurvePoint n'est pas trouvé ou en cas d'erreur d'accès à la base de données.
     */
    public CurvePoint getCurvePointById(Integer id) {
        logger.info("Entrée dans la méthode getCurvePointById() avec ID : {}", id);
        CurvePoint curvePoint = curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Aucun CurvePoint trouvé pour l'ID : {}", id);
                    return new RuntimeException("CurvePoint introuvable avec l'ID : " + id);
                });
        logger.info("CurvePoint récupéré avec succès : {}", curvePoint);
        return curvePoint;
    }

    /**
     * Récupère la liste de tous les {@link CurvePoint}.
     * 
     * @return Une liste de CurvePoints.
     * @throws RuntimeException en cas d'erreur lors de l'accès à la base de données.
     */
    public List<CurvePoint> getAllCurvePoint() {
        logger.info("Entrée dans la méthode getAllCurvePoint().");
        List<CurvePoint> curvePointList = curvePointRepository.findAll();
        logger.info("Nombre de CurvePoints récupérés : {}", curvePointList.size());
        return curvePointList;
    }

    /**
     * Ajoute un nouveau {@link CurvePoint}.
     * 
     * @param curvePoint Le CurvePoint à ajouter.
     * @return Le CurvePoint ajouté.
     * @throws RuntimeException en cas d'erreur lors de l'ajout.
     */
    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
        logger.info("Entrée dans la méthode addCurvePoint().");
        logger.debug("CurvePoint à ajouter : {}", curvePoint);
        CurvePoint savedCurvePoint = curvePointRepository.save(curvePoint);
        logger.info("CurvePoint ajouté avec succès : {}", savedCurvePoint);
        return savedCurvePoint;
    }

    /**
     * Met à jour un {@link CurvePoint} existant.
     * 
     * @param id L'identifiant du CurvePoint à mettre à jour.
     * @param curvePoint Les nouvelles valeurs du CurvePoint.
     * @return Le CurvePoint mis à jour.
     * @throws RuntimeException en cas d'erreur lors de la mise à jour.
     */
    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
        logger.info("Entrée dans la méthode updateCurvePoint() pour l'ID {}", id);
        CurvePoint curvePointToUpdate = getCurvePointById(id);
        curvePointToUpdate.setTerm(curvePoint.getTerm());
        curvePointToUpdate.setValue(curvePoint.getValue());

        logger.debug("Mise à jour du CurvePoint avec ID {} : {}", id, curvePointToUpdate);
        CurvePoint updatedCurvePoint = curvePointRepository.save(curvePointToUpdate);
        logger.info("CurvePoint mis à jour avec succès.");
        return updatedCurvePoint;
    }

    /**
     * Supprime un {@link CurvePoint} par son ID.
     * 
     * @param id L'identifiant du CurvePoint à supprimer.
     * @throws RuntimeException en cas d'erreur lors de la suppression.
     */
    public void deleteCurvePoint(Integer id) {
        logger.info("Entrée dans la méthode deleteCurvePoint() pour l'ID {}", id);
        CurvePoint curvePointToDelete = getCurvePointById(id);
        logger.debug("CurvePoint à supprimer : {}", curvePointToDelete);
        curvePointRepository.delete(curvePointToDelete);
        logger.info("CurvePoint supprimé avec succès.");
    }
}

