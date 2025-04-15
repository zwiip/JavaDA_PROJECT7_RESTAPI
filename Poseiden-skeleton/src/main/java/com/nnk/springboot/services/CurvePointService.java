package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service layer for the CurvePoint Entities.
 * Provides methods for the CRUD operations (create, read, update, delete) on the CurvePoint repository.
 */
@Service
public class CurvePointService {

    /* VARIABLES */
    private CurvePointRepository curvePointRepository;
    private final Logger logger = Logger.getLogger(CurvePointService.class.getName());

    /* CONSTRUCTOR */
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /* METHODS */
    /**
     * Retrieves a list of all CurvePoint entities.
     * @return a list of CurvePoint objects
     */
    public  List<CurvePoint> getCurvePoints() {
        logger.fine("Fetching all the CurvePoints.");
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        logger.info("Found " + curvePoints.size() + " curvePoints");
        return curvePoints;
    }

    /**
     * Retrieve a CurvePoint entity by its ID.
     * @param id the ID of the CurvePoint to the retrieve.
     * @return an Optional containing the matching CurvePoint or an empty Optional object.
     */
    public Optional<CurvePoint> getCurvePoint(Integer id) {
        logger.fine("Fetching the CurvePoint for the id: " + id);
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(id);
        if (curvePoint.isPresent()) {
            logger.info("Found a CurvePoint with the value: " + curvePoint.get().getCurveValue());
        } else {
            logger.warning("No CurvePoint found for the id: " + id);
        }
        return curvePoint;
    }

    /**
     * Saves a CurvePoint entity to the repository.
     * @param curvePoint the CurvePoint to save.
     */
    public void saveCurvePoint(CurvePoint curvePoint) {
        logger.fine("Saving CurvePoint.");
        curvePointRepository.save(curvePoint);
        logger.info("CurvePoint saved");
    }

    /**
     * Deletes a CurvePoint entity matching the given ID.
     * @param id the ID of the CurvePoint to delete.
     */
    public void deleteCurvePoint(Integer id) {
        logger.fine("Deleting Curvepoint.");
        curvePointRepository.deleteById(id);
        logger.info("CurvePoint deleted.");
    }
}
