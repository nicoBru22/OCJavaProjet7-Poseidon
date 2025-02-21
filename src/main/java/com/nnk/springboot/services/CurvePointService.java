package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	
	Logger logger = LogManager.getLogger(CurvePointRepository.class);
	
	@Autowired
	private CurvePointRepository curvePointRepository;

	public CurvePoint getCurvePointById(Integer id) {
		
		try {
			CurvePoint curvePoint = curvePointRepository.findById(id)
					.orElseThrow();
			return curvePoint;
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de la récupération du CurvePoint par son ID." + e);
		}
	}
	
	public List<CurvePoint> getAllCurvePoint() {
		try {
			List<CurvePoint> curvePointList = curvePointRepository.findAll();
			return curvePointList;
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de la récupération de la liste des CurvePoint." + e);
		}
	}
	
	public CurvePoint addCurvePoint(CurvePoint curvePoint) {
		try {
			
			return curvePointRepository.save(curvePoint);
			
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de l'ajout du CurvePoint." + e);
		}
	}
	
	public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
		try {
			CurvePoint curvePointToUpdate = getCurvePointById(id);
			curvePointToUpdate.setTerm(curvePoint.getTerm());
			curvePointToUpdate.setValue(curvePoint.getValue());
			
			return curvePointRepository.save(curvePointToUpdate);
			
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de la modification du CurvePoint." + e);
		}
	}
	
	public void deleteCurvePoint(Integer id) {
		try {
			CurvePoint curvePointToDelete = getCurvePointById(id);
			curvePointRepository.delete(curvePointToDelete);
		} catch (Exception e) {
			throw new RuntimeException("Erreur lors de la suppression du CurvePoint." + e);
		}
	}
	
	
	
}
