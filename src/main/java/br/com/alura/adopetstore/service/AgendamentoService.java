package br.com.alura.adopetstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private RelatorioService relatorioService;

    @Scheduled(cron = "0 51 11 * * *")
    public void envioEmailAgendado() {
        var estoqueZerado = relatorioService.infoEstoque();
        var faturamentoObtido = relatorioService.faturamentoObtido();
        System.out.println("Enviando email com relatório de estoque zerado: " + estoqueZerado);
        System.out.println("Enviando email com relatório de faturamento: " + faturamentoObtido);
    }
}
