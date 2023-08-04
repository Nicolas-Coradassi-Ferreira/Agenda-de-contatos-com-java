package util;

import model.Contato;
import model.Endereco;

import java.util.List;

public final class ContatoConverter {

    private ContatoConverter(){}

    public static Contato csvToContato(String linhaCsv){
        var dadosContato = List.of(linhaCsv.split(";"));
        return new Contato(
                Integer.valueOf(dadosContato.get(0)),
                dadosContato.get(1),
                dadosContato.get(2),
                dadosContato.get(3),
                new Endereco(
                        dadosContato.get(4),
                        dadosContato.get(5),
                        dadosContato.get(6),
                        dadosContato.get(7),
                        dadosContato.get(8),
                        dadosContato.get(9),
                        dadosContato.get(10)
                )
        );
    }

    public static String contatoToCsv(Contato c){
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                c.getId(),
                c.getNomeCompleto(),
                c.getTelefone(),
                c.getEmail(),
                c.getEndereco().getLogradouro(),
                c.getEndereco().getNumero(),
                c.getEndereco().getComplemento(),
                c.getEndereco().getBairro(),
                c.getEndereco().getCidade(),
                c.getEndereco().getUf(),
                c.getEndereco().getCep());
    }
}
