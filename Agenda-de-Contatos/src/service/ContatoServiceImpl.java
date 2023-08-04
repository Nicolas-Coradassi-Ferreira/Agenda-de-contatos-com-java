package service;


import model.Contato;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.util.Collections.unmodifiableList;
import static util.ContatoConverter.contatoToCsv;
import static util.ContatoConverter.csvToContato;

public final class ContatoServiceImpl implements ContatoService {

    private static ContatoServiceImpl instance;
    private final List<Contato> contatos = new ArrayList<>();

    private ContatoServiceImpl() {
        try (var leitorArquivoContatos = new Scanner(new FileReader("contatos.csv"))) {

            while (leitorArquivoContatos.hasNextLine()) {
                var linhaArquivo = leitorArquivoContatos.nextLine();
                var contato = csvToContato(linhaArquivo);
                contatos.add(contato);
            }

        } catch (IOException e) {
            throw new RuntimeException("Ocorreu alguma exceção ao tentar usar o arquivo contatos.csv");
        }
    }

    @Override
    public boolean salvarContato(Contato c) {
        c.setId(getProximoContatoId());
        return contatos.add(c);
    }

    @Override
    public List<Contato> obterContatos() {
        return unmodifiableList(contatos);
    }

    @Override
    public List<Contato> procurarContato(String idContato) {
        return contatos
                .stream()
                .filter(contato ->
                            idContato.equals(contato.getNomeCompleto()) ||
                            idContato.equals(contato.getTelefone()))
                .toList();
    }

    @Override
    public boolean removerContato(Integer idContato) {
        return contatos.removeIf(contato -> idContato.equals(contato.getId()));
    }

    private Integer getProximoContatoId(){
        int idAtual;

        try(var leitorArquivoIdSequence = new BufferedReader(new FileReader("idSequence.txt"))){
            idAtual = Integer.parseInt(leitorArquivoIdSequence.readLine());
        } catch(IOException e){
            throw new RuntimeException("Não foi possível abrir o arquivo de configuração -> idSequence.txt");
        }

        try(var arquivoIdSequenceWriter = new BufferedWriter(new FileWriter("idSequence.txt"))){
            arquivoIdSequenceWriter.write(String.valueOf(idAtual + 1));
        } catch (IOException e){
            throw new RuntimeException("Não foi possível abrir o arquivo de configuração -> idSequence.txt");
        }

        return idAtual;
    }

    public static ContatoServiceImpl getInstance() {
        if (instance == null) {
            return new ContatoServiceImpl();
        }
        return instance;
    }

    public void close() {
        try (var arquivoContatosWriter = new BufferedWriter(new FileWriter("contatos.csv"))) {

            for (Contato c : contatos) {
                arquivoContatosWriter.write(contatoToCsv(c));
            }

        } catch (IOException e) {
            throw new RuntimeException("Ocorreu alguma exceção ao tentar salvar os contatos no arquivo contatos.csv");
        }
    }
}
