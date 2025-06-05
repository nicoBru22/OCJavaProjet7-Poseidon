package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

public interface IRatingService {
	List<Rating> getAllRating();
	Rating addRating(Rating rating);
	Rating getRatingById(Integer id);
	Rating updateRating(Integer id, Rating rating);
	void deleteRating(Integer id);
	
}
