package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service layer for the BidList Entities.
 * Provides methods for the CRUD operations (create, read, update, delete) on the BidList repository.
 */
@Service
public class BidListService {
    /* VARIABLES */
    private BidListRepository bidListRepository;
    private final Logger logger = Logger.getLogger(BidListService.class.getName());

    /* CONSTRUCTOR */
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /* METHODS */
    /**
     * Retrieves a list of all BidList entities.
     * @return a list of BidList objects
     */
    public List<BidList> getBidLists() {
        logger.fine("Fetching all the BidLists");
        List<BidList> bidLists = bidListRepository.findAll();
        logger.info("Found " + bidLists.size() + " bidlists.");
        return bidLists;
    }

    /**
     * Retrieve a BidList entity by its ID.
     * @param id the ID of the BidList to retrieve.
     * @return an Optional containing the matching BidList or an empty Optional object.
     */
    public Optional<BidList> getBidList(Integer id) {
        logger.fine("Fetching a bidlist for the id: " + id);
        Optional<BidList> bidList = bidListRepository.findById(id);
        if (bidList.isPresent()) {
            logger.info("Found a BidList for the account: " + bidList.get().getAccount());
        } else {
            logger.warning("No BidList found for the id: " + id);
        }
        return bidList;
    }

    /**
     * Saves a BidList entity to the repository.
     * @param bidList the BidList to save.
     */
    public void saveBidList(BidList bidList) {
        logger.fine("Saving the BidList for the account: " + bidList.getAccount());
        bidListRepository.save(bidList);
        logger.info("BidList saved for the account: " + bidList.getAccount());
    }

    /**
     * Deletes a BidList entity matching the given ID.
     * @param id the ID of the BidList to delete.
     */
    public void deleteBidList(Integer id) {
        logger.fine("Deleting the BidList with the id: " + id);
        bidListRepository.deleteById(id);
        logger.info("BidList deleted.");
    }
}
