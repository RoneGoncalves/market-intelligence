package br.com.ronaldo.market_intelligence.domain.adapter;

import br.com.ronaldo.market_intelligence.domain.model.TicketMedioDummyModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioLocalModel;
import br.com.ronaldo.market_intelligence.domain.model.TicketMedioResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketMedioInsightAdapterTest {

    private TicketMedioInsightAdapter adapter;

    @BeforeEach
    void setup() {
        adapter = new TicketMedioInsightAdapter();
    }

    private TicketMedioDummyModel dummy(
            int total, double medio, double medioDesc, double maior, double menor
    ) {
        TicketMedioDummyModel d = new TicketMedioDummyModel();
        d.setDummyTotalCarts(total);
        d.setDummyTicketMedio(medio);
        d.setDummyTicketMedioDescontado(medioDesc);
        d.setDummyMaiorTicket(maior);
        d.setDummyMenorTicket(menor);
        return d;
    }

    private TicketMedioLocalModel local(
            int total, double medio, double medioDesc, double maior, double menor
    ) {
        TicketMedioLocalModel l = new TicketMedioLocalModel();
        l.setTotalCarts(total);
        l.setTicketMedioLocal(medio);
        l.setTicketMedioDescontadoLocal(medioDesc);
        l.setMaiorTicketLocal(maior);
        l.setMenorTicketLocal(menor);
        return l;
    }

    @Test
    void deveCalcularInsightsCorretamente() {
        TicketMedioDummyModel dummy = dummy(10, 100.0, 80.0, 200.0, 50.0);
        TicketMedioLocalModel local = local(12, 120.0, 90.0, 250.0, 60.0);

        TicketMedioResponseModel r = adapter.calculaInsights(dummy, local);

        assertEquals(10, r.getDummyTotalCart());
        assertEquals(12, r.getLocalTotalCart());

        String dummyTicket = r.getDummyTicketMedio().replace("\u00A0", " ");
        String localTicket = r.getLocalTicketMedio().replace("\u00A0", " ");

        assertEquals("R$ 100,00", dummyTicket);
        assertEquals("R$ 120,00", localTicket);
    }


    @Test
    void insightDeveIndicarMaiorQuandoLocalForMaior() {
        TicketMedioDummyModel dummy = dummy(10, 100.0, 80.0, 200.0, 50.0);
        TicketMedioLocalModel local = local(10, 150.0, 120.0, 210.0, 60.0);

        TicketMedioResponseModel r = adapter.calculaInsights(dummy, local);

        assertTrue(r.getInsightTicketMedio().contains("local é"));
    }

    @Test
    void insightDeveIndicarMaiorQuandoDummyForMaior() {
        TicketMedioDummyModel dummy = dummy(10, 200.0, 160.0, 250.0, 60.0);
        TicketMedioLocalModel local = local(10, 150.0, 120.0, 210.0, 60.0);

        TicketMedioResponseModel r = adapter.calculaInsights(dummy, local);

        assertTrue(r.getInsightTicketMedio().contains("dummy é"));
    }

    @Test
    void insightDeveIndicarQuaseIgualQuandoVariacaoMenorQue2Porcento() {
        TicketMedioDummyModel dummy = dummy(10, 100.0, 80.0, 200.0, 50.0);
        TicketMedioLocalModel local = local(10, 101.5, 81.5, 210.0, 60.0); // ~1.5%

        TicketMedioResponseModel r = adapter.calculaInsights(dummy, local);

        assertTrue(r.getInsightTicketMedio().contains("praticamente igual"));
    }

    @Test
    void naoDeveQuebrarQuandoDummyZero() {
        TicketMedioDummyModel dummy = dummy(10, 0.0, 0.0, 200.0, 50.0);
        TicketMedioLocalModel local = local(10, 100.0, 80.0, 210.0, 60.0);

        TicketMedioResponseModel r = adapter.calculaInsights(dummy, local);

        String insight = r.getInsightTicketMedio();

        assertTrue(
                insight.contains("praticamente igual"),
                "Insight deveria indicar igualdade quando dummy=0. Insight: " + insight
        );
    }
}
