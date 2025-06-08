package com.nnk.springboot.services.implService;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;

/**
 * Service pour gérer les opérations sur les objets BidList.
 */
@Service
public class BidListServiceImpl implements IBidListService {

    private static final Logger logger = LogManager.getLogger(BidListServiceImpl.class);

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Récupère tous les objets BidList en base de données.
     *
     * @return Liste des BidList trouvés.
     * @throws RuntimeException en cas d'erreur lors de la récupération.
     */
    public List<BidList> getAllBid() {
        logger.info("Entrée dans la méthode getAllBid().");
        List<BidList> bidList = bidListRepository.findAll();

        if (bidList.isEmpty()) {
            logger.debug("Aucun bid trouvé. La liste est vide.");
        } else {
            logger.info("Nombre de bids récupérés : {}", bidList.size());
            logger.debug("Détails des bids récupérés : {}", bidList);
        }
        return bidList;
    }

    /**
     * Récupère un BidList en fonction de son ID.
     *
     * @param id Identifiant du bid.
     * @return L'objet BidList correspondant.
     * @throws IllegalArgumentException si l'ID est nul.
     * @throws RuntimeException si aucun bid n'est trouvé.
     */
    public BidList getBidById(Integer id) {
        logger.info("Entrée dans la méthode getBidById() avec ID : {}", id);

        if (id == null) {
            logger.error("L'ID fourni est nul.");
            throw new IllegalArgumentException("L'ID ne peut pas être nul.");
        }

        return bidListRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Aucun BidList trouvé pour l'ID : {}", id);
                return new RuntimeException("BidList introuvable avec l'ID : " + id);
            });
    }

    /**
     * Ajoute un nouveau BidList en base de données.
     *
     * @param bid L'objet BidList à ajouter.
     * @return Le BidList ajouté.
     * @throws IllegalArgumentException si l'objet bid est nul.
     * @throws RuntimeException en cas d'erreur lors de l'ajout.
     */
    public BidList addBid(BidList bid) {
        logger.info("Entrée dans la méthode addBid().");

        if (bid == null) {
            logger.error("Tentative d'ajout d'un bid nul.");
            throw new IllegalArgumentException("Le bid à ajouter ne peut pas être nul.");
        }

        bid.setCreationDate(LocalDateTime.now());
        logger.debug("Nouveau bid à ajouter : {}", bid);
        BidList savedBidList = bidListRepository.save(bid);
        logger.info("Bid ajouté avec succès : {}", savedBidList);
        return savedBidList;
    }

    /**
     * Met à jour un BidList existant.
     *
     * @param id Identifiant du BidList à mettre à jour.
     * @param bidList Nouvelles valeurs du BidList.
     * @return Le BidList mis à jour.
     * @throws RuntimeException en cas d'erreur lors de la mise à jour.
     */
    public BidList updateBidList(Integer id, BidList bidList) {
        logger.info("Récupération du BidList avec ID {}", id);
        BidList bidToUpdate = getBidById(id);

        bidToUpdate.setAccount(bidList.getAccount());
        bidToUpdate.setType(bidList.getType());
        bidToUpdate.setBidQuantity(bidList.getBidQuantity());
        bidToUpdate.setRevisionDate(LocalDateTime.now());

        logger.debug("Mise à jour des valeurs du BidList : {}", bidToUpdate);
        BidList updatedBid = bidListRepository.save(bidToUpdate);
        logger.info("BidList mis à jour avec succès : {}", updatedBid);

        return updatedBid;
    }

    /**
     * Supprime un BidList en fonction de son ID.
     *
     * @param id Identifiant du BidList à supprimer.
     * @throws RuntimeException en cas d'erreur lors de la suppression.
     */
    public void deleteBidList(Integer id) {
        logger.info("Entrée dans la méthode deleteBidList() pour l'ID {}", id);

        BidList bidToDelete = getBidById(id);
        logger.debug("BidList à supprimer : {}", bidToDelete);
        bidListRepository.delete(bidToDelete);
        logger.info("BidList supprimé avec succès.");
    }
}
