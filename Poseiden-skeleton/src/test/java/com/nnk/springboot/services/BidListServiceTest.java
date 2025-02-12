package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class BidListServiceTest {
    private BidListService bidListService;
    private BidList bidList;

    @Mock
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setUp() {
        bidListRepository = mock(BidListRepository.class);
        bidListService = new BidListService(bidListRepository);

        bidList = new BidList("Account Test", "Type Test", 10d);
    }

    @Test
    public void givenTwoBidList_whenGetBidLists_thenReturnTheListWithThoBidLists() {
        BidList secondBidList = new BidList("Second Account", "Type 2", 15d);
        List<BidList> listOfTwoBidLists = new ArrayList<>();
        listOfTwoBidLists.add(bidList);
        listOfTwoBidLists.add(secondBidList);
        when(bidListRepository.findAll()).thenReturn(listOfTwoBidLists);

        List<BidList> actualBidLists = bidListService.getBidLists();

        assertEquals(2, actualBidLists.size());
    }

    @Test
    public void givenCorrectBidListIdçwhenGetBidList_thenReturnMatchingBidlist() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bidList));

        Optional<BidList> actualBidList = bidListService.getBidList(1);

        assertTrue(actualBidList.isPresent());
        assertEquals("Account Test", actualBidList.get().getAccount());
    }

    @Test
    public void givenCorrectBidList_whenSaveBidList_thenBidListIsSaved() {
        bidListService.saveBidList(bidList);

        verify(bidListRepository).save(bidList);
    }

    @Test
    public void givenCorrectId_whenDeleteBidList_thenBidListIsDeleted() {
        bidListService.deleteBidList(1);

        verify(bidListRepository).deleteById(1);
    }
}
