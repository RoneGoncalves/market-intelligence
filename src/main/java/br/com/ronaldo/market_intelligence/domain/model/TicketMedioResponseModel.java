package br.com.ronaldo.market_intelligence.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class TicketMedioResponseModel {

    @Schema
    private Integer dummyTotalCart;

    @Schema
    private String dummyTicketMedio;

    @Schema
    private String dummyTicketMedioComDesconto;

    @Schema
    private String dummyMaiorTicket;

    @Schema
    private String dummyMenorTicket;

    @Schema
    private Integer localTotalCart;

    @Schema
    private String localTicketMedio;

    @Schema
    private String localTicketMedioComDesconto;

    @Schema
    private String localMaiorTicket;

    @Schema
    private String localMenorTicket;

    @Schema
    private String insight;

    public TicketMedioResponseModel() {}

    public TicketMedioResponseModel(
            Integer dummyTotalCart, String dummyTicketMedio, String dummyTicketMedioComDesconto,
            String dummyMaiorTicket, String dummyMenorTicket, String localTicketMedio,
            Integer localTotalCart, String localTicketMedioComDesconto, String localMaiorTicket,
            String localMenorTicket, String insight) {
        this.dummyTotalCart = dummyTotalCart;
        this.dummyTicketMedio = dummyTicketMedio;
        this.dummyTicketMedioComDesconto = dummyTicketMedioComDesconto;
        this.dummyMaiorTicket = dummyMaiorTicket;
        this.dummyMenorTicket = dummyMenorTicket;
        this.localTicketMedio = localTicketMedio;
        this.localTotalCart = localTotalCart;
        this.localTicketMedioComDesconto = localTicketMedioComDesconto;
        this.localMaiorTicket = localMaiorTicket;
        this.localMenorTicket = localMenorTicket;
        this.insight = insight;
    }

    public Integer getDummyTotalCart() {
        return dummyTotalCart;
    }

    public void setDummyTotalCart(Integer dummyTotalCart) {
        this.dummyTotalCart = dummyTotalCart;
    }

    public String getDummyTicketMedio() {
        return dummyTicketMedio;
    }

    public void setDummyTicketMedio(String dummyTicketMedio) {
        this.dummyTicketMedio = dummyTicketMedio;
    }

    public String getDummyTicketMedioComDesconto() {
        return dummyTicketMedioComDesconto;
    }

    public void setDummyTicketMedioComDesconto(String dummyTicketMedioComDesconto) {
        this.dummyTicketMedioComDesconto = dummyTicketMedioComDesconto;
    }

    public String getDummyMaiorTicket() {
        return dummyMaiorTicket;
    }

    public void setDummyMaiorTicket(String dummyMaiorTicket) {
        this.dummyMaiorTicket = dummyMaiorTicket;
    }

    public String getDummyMenorTicket() {
        return dummyMenorTicket;
    }

    public void setDummyMenorTicket(String dummyMenorTicket) {
        this.dummyMenorTicket = dummyMenorTicket;
    }

    public Integer getLocalTotalCart() {
        return localTotalCart;
    }

    public void setLocalTotalCart(Integer localTotalCart) {
        this.localTotalCart = localTotalCart;
    }

    public String getLocalTicketMedio() {
        return localTicketMedio;
    }

    public void setLocalTicketMedio(String localTicketMedio) {
        this.localTicketMedio = localTicketMedio;
    }

    public String getLocalTicketMedioComDesconto() {
        return localTicketMedioComDesconto;
    }

    public void setLocalTicketMedioComDesconto(String localTicketMedioComDesconto) {
        this.localTicketMedioComDesconto = localTicketMedioComDesconto;
    }

    public String getLocalMaiorTicket() {
        return localMaiorTicket;
    }

    public void setLocalMaiorTicket(String localMaiorTicket) {
        this.localMaiorTicket = localMaiorTicket;
    }

    public String getLocalMenorTicket() {
        return localMenorTicket;
    }

    public void setLocalMenorTicket(String localMenorTicket) {
        this.localMenorTicket = localMenorTicket;
    }

    public String getInsight() {
        return insight;
    }

    public void setInsight(String insight) {
        this.insight = insight;
    }
}
