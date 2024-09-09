package br.com.alura.adopetstore.service;

import br.com.alura.adopetstore.email.EmaildeRelatorioGerado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AgendamentoService {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private EmaildeRelatorioGerado emaildeRelatorioGerado;

    @Scheduled(cron = "0 23 13 * * *")
    public void envioEmailAgendado() {
        var estoqueZerado = relatorioService.infoEstoque();
        var faturamentoObtido = relatorioService.faturamentoObtido();

        CompletableFuture.allOf(estoqueZerado, faturamentoObtido).join();

        try {
            emaildeRelatorioGerado.enviar(estoqueZerado.get(), faturamentoObtido.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Enviando email com relatório de estoque zerado: " + estoqueZerado);
        System.out.println("Enviando email com relatório de faturamento: " + faturamentoObtido);
    }
}
