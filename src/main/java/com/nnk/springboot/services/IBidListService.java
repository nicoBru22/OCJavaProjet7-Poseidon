package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

public interface IBidListService {
	List<BidList> getAllBid();
	BidList getBidById(Integer id);
	BidList addBid(BidList bid);
	BidList updateBidList(Integer id, BidList bidList);
	void deleteBidList(Integer id);
}
