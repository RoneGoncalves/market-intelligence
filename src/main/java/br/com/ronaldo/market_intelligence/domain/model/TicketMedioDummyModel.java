package br.com.ronaldo.market_intelligence.domain.model;
public class TicketMedioDummyModel {

    private Integer dummyTotalCarts;
    private Double dummyTicketMedio;
    private Double dummyTicketMedioDescontado;
    private Double dummyMaiorTicket;
    private Double dummyMenorTicket;

    public TicketMedioDummyModel() {}

    public TicketMedioDummyModel(Integer dummyTotalCarts, Double dummyTicketMedio, Double dummyTicketMedioDescontado, Double dummyMaiorTicket, Double dummyMenorTicket, String insight) {
        this.dummyTotalCarts = dummyTotalCarts;
        this.dummyTicketMedio = dummyTicketMedio;
        this.dummyTicketMedioDescontado = dummyTicketMedioDescontado;
        this.dummyMaiorTicket = dummyMaiorTicket;
        this.dummyMenorTicket = dummyMenorTicket;
    }

    public Integer getDummyTotalCarts() {
        return dummyTotalCarts;
    }

    public void setDummyTotalCarts(Integer dummyTotalCarts) {
        this.dummyTotalCarts = dummyTotalCarts;
    }

    public Double getDummyTicketMedio() {
        return dummyTicketMedio;
    }

    public void setDummyTicketMedio(Double dummyTicketMedio) {
        this.dummyTicketMedio = dummyTicketMedio;
    }

    public Double getDummyTicketMedioDescontado() {
        return dummyTicketMedioDescontado;
    }

    public void setDummyTicketMedioDescontado(Double dummyTicketMedioDescontado) {
        this.dummyTicketMedioDescontado = dummyTicketMedioDescontado;
    }

    public Double getDummyMaiorTicket() {
        return dummyMaiorTicket;
    }

    public void setDummyMaiorTicket(Double dummyMaiorTicket) {
        this.dummyMaiorTicket = dummyMaiorTicket;
    }

    public Double getDummyMenorTicket() {
        return dummyMenorTicket;
    }

    public void setDummyMenorTicket(Double dummyMenorTicket) {
        this.dummyMenorTicket = dummyMenorTicket;
    }
}
