package br.com.ronaldo.market_intelligence.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Busca informções sobre ticket médio no DummyJSON e na base local")
public class TicketMedioResponseDto {

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
    private String localTicketMedioDescontado;

    @Schema
    private String localMaiorTicket;

    @Schema
    private String localMenorTicket;

    @Schema
    private String insightTicketMedio;

    @Schema
    private String insightTicketMedioDescontado;

    public TicketMedioResponseDto() {}

    public TicketMedioResponseDto(
            Integer dummyTotalCart, String dummyTicketMedio, String dummyTicketMedioComDesconto, String dummyMaiorTicket,
            String dummyMenorTicket, Integer localTotalCart, String localTicketMedio, String localTicketMedioDescontado,
            String localMaiorTicket, String localMenorTicket, String insightTicketMedio, String insightTicketMedioDescontado) {
        this.dummyTotalCart = dummyTotalCart;
        this.dummyTicketMedio = dummyTicketMedio;
        this.dummyTicketMedioComDesconto = dummyTicketMedioComDesconto;
        this.dummyMaiorTicket = dummyMaiorTicket;
        this.dummyMenorTicket = dummyMenorTicket;
        this.localTotalCart = localTotalCart;
        this.localTicketMedio = localTicketMedio;
        this.localTicketMedioDescontado = localTicketMedioDescontado;
        this.localMaiorTicket = localMaiorTicket;
        this.localMenorTicket = localMenorTicket;
        this.insightTicketMedio = insightTicketMedio;
        this.insightTicketMedioDescontado = insightTicketMedioDescontado;
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

    public String getLocalTicketMedioDescontado() {
        return localTicketMedioDescontado;
    }

    public void setLocalTicketMedioDescontado(String localTicketMedioDescontado) {
        this.localTicketMedioDescontado = localTicketMedioDescontado;
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

    public String getInsightTicketMedio() {
        return insightTicketMedio;
    }

    public void setInsightTicketMedio(String insightTicketMedio) {
        this.insightTicketMedio = insightTicketMedio;
    }

    public String getInsightTicketMedioDescontado() {
        return insightTicketMedioDescontado;
    }

    public void setInsightTicketMedioDescontado(String insightTicketMedioDescontado) {
        this.insightTicketMedioDescontado = insightTicketMedioDescontado;
    }
}
