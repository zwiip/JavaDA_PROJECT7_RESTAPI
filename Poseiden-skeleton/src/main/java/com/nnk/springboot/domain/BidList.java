package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Account is mandatory")
    @Length(max = 30, message = "Must not exceed 30 characters")
    private String account;

    @NotBlank(message = "Type is mandatory")
    @Length(max = 30, message = "Must not exceed 30 characters")
    private String type;

    @DecimalMin(value = "1.0", message = "Minimum value is 1.0")
    private Double bidQuantity;

    private Double askQuantity;

    private Double bid;

    private Double ask;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String benchmark;

    private Timestamp bidListDate;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String commentary;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String security;

    @Length(max = 10, message = "Must not exceed 10 characters")
    private String status;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String trader;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String book;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String creationName;

    private Timestamp creationDate;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String revisionName;

    private Timestamp revisionDate;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String dealName;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String dealType;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String sourceListId;

    @Length(max = 125, message = "Must not exceed 125 characters")
    private String side;

    public BidList() {}

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public Double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
