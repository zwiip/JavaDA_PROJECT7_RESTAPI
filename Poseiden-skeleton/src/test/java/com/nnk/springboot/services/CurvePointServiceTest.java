package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CurvePointServiceTest {

    private CurvePointService curvePointService;
    private CurvePoint curvePoint;

    @Mock
    CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setUp() {
        curvePointRepository = mock(CurvePointRepository.class);
        curvePointService = new CurvePointService(curvePointRepository);

        curvePoint = new CurvePoint(1, 10d, 30d);
    }

    @Test
    public void givenTwoCurvePoints_whenGetCurvePoints_thenReturnTheListWithTwoCurvePoints() {
        CurvePoint secondCurvePoint = new CurvePoint(2, 20d, 40d);
        List<CurvePoint> listOfTwoCurvePoints = new ArrayList<>();
        listOfTwoCurvePoints.add(curvePoint);
        listOfTwoCurvePoints.add(secondCurvePoint);
        when(curvePointRepository.findAll()).thenReturn(listOfTwoCurvePoints);

        List<CurvePoint> actualCurvePoints = curvePointService.getCurvePoints();

        assertEquals(2, actualCurvePoints.size());
        assertEquals(10d, actualCurvePoints.getFirst().getTerm());
        assertEquals(40d, actualCurvePoints.getLast().getValue());
    }

    @Test
    public void givenCorrectId_whenGetCurvePoint_thenReturnMatchingCurvePoint() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        Optional<CurvePoint> actualCurvePoint = curvePointService.getCurvePoint(1);

        assertTrue(actualCurvePoint.isPresent());
        assertEquals(10d, actualCurvePoint.get().getTerm());
    }

    @Test
    public void givenCorrectCurvePoint_whenSaveCurvePoint_thenCurvePointIsSaved() {
        curvePointService.saveCurvePoint(curvePoint);

        verify(curvePointRepository).save(curvePoint);
    }

    @Test
    public void givenCorrectId_whenDeleteCurvePoint_thenCurvePointIsDeleted() {
        curvePointService.deleteCurvePoint(1);

        verify(curvePointRepository).deleteById(1);
    }
}
