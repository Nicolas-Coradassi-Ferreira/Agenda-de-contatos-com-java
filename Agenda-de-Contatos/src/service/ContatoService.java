package service;

import model.Contato;

import java.util.List;

public interface ContatoService {

    boolean salvarContato(Contato c);
    List<Contato> obterContatos();
    List<Contato> procurarContato(String idContato);
    boolean removerContato(Integer idContato);
    void close();
}
