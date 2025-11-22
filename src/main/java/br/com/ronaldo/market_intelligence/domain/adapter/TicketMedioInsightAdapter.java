package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.model.TicketMedioDummyModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioResponseModel;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class TicketMedioInsightAdapter {

    private static final Locale BRAZIL = new Locale("pt", "BR");
    private static final NumberFormat moedaBR = NumberFormat.getCurrencyInstance(BRAZIL);
    private static final NumberFormat percentualFormat =
            new java.text.DecimalFormat("#0.0"); // 1 casa decimal

    public TicketMedioResponseModel calculaInsights(
            TicketMedioDummyModel dummyTicket,
            TicketMedioLocalModel ticketLocal) {

        TicketMedioResponseModel response = new TicketMedioResponseModel();

        response.setDummyTotalCart(dummyTicket.getDummyTotalCarts());
        response.setDummyTicketMedio(moedaBR.format(dummyTicket.getDummyTicketMedio()));
        response.setDummyTicketMedioComDesconto(moedaBR.format(dummyTicket.getDummyTicketMedioDescontado()));
        response.setDummyMaiorTicket(moedaBR.format(dummyTicket.getDummyMaiorTicket()));
        response.setDummyMenorTicket(moedaBR.format(dummyTicket.getDummyMenorTicket()));

        response.setLocalTotalCart(ticketLocal.getTotalCarts());
        response.setLocalTicketMedio(moedaBR.format(ticketLocal.getTicketMedioLocal()));
        response.setLocalTicketMedioDescontado(moedaBR.format(ticketLocal.getTicketMedioDescontadoLocal()));
        response.setLocalMaiorTicket(moedaBR.format(ticketLocal.getMaiorTicketLocal()));
        response.setLocalMenorTicket(moedaBR.format(ticketLocal.getMenorTicketLocal()));

        double pTicket = calculaPercentual(ticketLocal.getTicketMedioLocal(), dummyTicket.getDummyTicketMedio());
        double pTicketDesc = calculaPercentual(ticketLocal.getTicketMedioDescontadoLocal(), dummyTicket.getDummyTicketMedioDescontado());

        String insightTicketMedio = gerarInsightComparativo(
                ticketLocal.getTicketMedioLocal(),
                dummyTicket.getDummyTicketMedio(),
                "ticket médio",
                pTicket
        );

        String insightTicketMedioDesc = gerarInsightComparativo(
                ticketLocal.getTicketMedioDescontadoLocal(),
                dummyTicket.getDummyTicketMedioDescontado(),
                "ticket médio descontado",
                pTicketDesc
        );

        response.setInsightTicketMedio(insightTicketMedio);
        response.setInsightTicketMedioDescontado(insightTicketMedioDesc);

        return response;
    }

    private double calculaPercentual(double local, double dummy) {
        if (dummy == 0) return 0;
        return ((local - dummy) / dummy) * 100;
    }

    private String gerarInsightComparativo(double local, double dummy, String label, double percentual) {
        double abs = Math.abs(percentual);
        String percentFormatado = percentualFormat.format(abs).replace(".", ",");

        if (abs <= 2.0) {
            return "O " + label + " é praticamente igual nas duas bases (variação de " + percentFormatado + "%).";
        }

        if (local > dummy) {
            return "O " + label + " local é " + percentFormatado + "% maior que o dummy.";
        } else {
            return "O " + label + " dummy é " + percentFormatado + "% maior que o local.";
        }
    }
}
