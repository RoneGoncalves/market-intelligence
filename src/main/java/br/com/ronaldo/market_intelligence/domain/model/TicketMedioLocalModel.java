package br.com.ronaldo.market_intelligence.domain.model;

public class TicketMedioLocalModel {

    private Integer totalCarts;
    private Double ticketMedioLocal;
    private Double ticketMedioDescontadoLocal;
    private Double maiorTicketLocal;
    private Double menorTicketLocal;

    public TicketMedioLocalModel() {}

    public TicketMedioLocalModel(Integer totalCarts, Double ticketMedioLocal, Double ticketMedioDescontadoLocal, Double maiorTicketLocal, Double menorTicketLocal, String insight) {
        this.totalCarts = totalCarts;
        this.ticketMedioLocal = ticketMedioLocal;
        this.ticketMedioDescontadoLocal = ticketMedioDescontadoLocal;
        this.maiorTicketLocal = maiorTicketLocal;
        this.menorTicketLocal = menorTicketLocal;
    }

    public Integer getTotalCarts() {
        return totalCarts;
    }

    public void setTotalCarts(Integer totalCarts) {
        this.totalCarts = totalCarts;
    }

    public Double getTicketMedioLocal() {
        return ticketMedioLocal;
    }

    public void setTicketMedioLocal(Double ticketMedioLocal) {
        this.ticketMedioLocal = ticketMedioLocal;
    }

    public Double getTicketMedioDescontadoLocal() {
        return ticketMedioDescontadoLocal;
    }

    public void setTicketMedioDescontadoLocal(Double ticketMedioDescontadoLocal) {
        this.ticketMedioDescontadoLocal = ticketMedioDescontadoLocal;
    }

    public Double getMaiorTicketLocal() {
        return maiorTicketLocal;
    }

    public void setMaiorTicketLocal(Double maiorTicketLocal) {
        this.maiorTicketLocal = maiorTicketLocal;
    }

    public Double getMenorTicketLocal() {
        return menorTicketLocal;
    }

    public void setMenorTicketLocal(Double menorTicketLocal) {
        this.menorTicketLocal = menorTicketLocal;
    }
}
