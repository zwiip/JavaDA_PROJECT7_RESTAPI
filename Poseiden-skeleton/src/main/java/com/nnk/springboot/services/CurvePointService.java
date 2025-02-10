package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {
    /* VARIABLES */
    private CurvePointRepository curvePointRepository;

    /* CONSTRUCTOR */
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /* METHODS */
    public  List<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    public Optional<CurvePoint> getCurvePoint(Integer id) {
        return curvePointRepository.findById(id);
    }

    public void saveCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    public void deleteCurvePoint(Integer id) {
        curvePointRepository.deleteById(id);
    }
}
