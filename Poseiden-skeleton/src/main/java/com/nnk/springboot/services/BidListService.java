package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {
    private BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List<BidList> getBidLists() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> getBidList(Integer id) {
        return bidListRepository.findById(id);
    }

    public void saveBidList(BidList bidList) {
        bidListRepository.save(bidList);
    }

    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }
}
