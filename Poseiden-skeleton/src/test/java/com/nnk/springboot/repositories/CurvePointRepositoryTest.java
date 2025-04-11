package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CurvePointRepositoryTest {

	@Autowired
	private CurvePointRepository curvePointRepository;

	private CurvePoint curvePoint;

	@BeforeEach
	public void setup() {
		curvePoint = new CurvePoint(1,10d, 30d);
	}

	@Test
	public void saveCurvePointTest() {
		curvePoint = curvePointRepository.save(curvePoint);
		assertNotNull(curvePoint.getId());
        assertEquals(30d, curvePoint.getValue());
	}

	@Test
	public void updateCurvePoint() {
		curvePoint.setValue(20d);
		curvePoint = curvePointRepository.save(curvePoint);
        assertEquals(20d, curvePoint.getValue());
	}

	@Test
	public void findAllCurvePointTest() {
		curvePoint = curvePointRepository.save(curvePoint);
		List<CurvePoint> listResult = curvePointRepository.findAll();
		assertEquals(1,listResult.size());
	}

	@Test
	public void curvePointTest() {
		curvePoint = curvePointRepository.save(curvePoint);
		Integer id = curvePoint.getId();
		curvePointRepository.delete(curvePoint);
		Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
		assertFalse(curvePointList.isPresent());
	}
}