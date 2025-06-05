package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

public interface ICurvePointService {
	CurvePoint getCurvePointById(Integer id);
	List<CurvePoint> getAllCurvePoint();
	CurvePoint addCurvePoint(CurvePoint curvePoint);
	CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint);
	void deleteCurvePoint(Integer id);
}
