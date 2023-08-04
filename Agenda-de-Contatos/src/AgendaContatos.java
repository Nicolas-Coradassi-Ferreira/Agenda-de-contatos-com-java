import model.Contato;
import model.Endereco;
import model.EstadoUsuario;
import service.ContatoService;
import service.ContatoServiceImpl;

import java.util.ArrayList;
import java.util.Scanner;

import static java.util.Collections.sort;
import static model.EstadoUsuario.*;


public final class AgendaContatos {

    private static EstadoUsuario estadoUsuario = MENU;
    private static final Scanner consoleEntrada = new Scanner(System.in);
    private static final ContatoService service = ContatoServiceImpl.getInstance();


    public static void main(String[] args) {
        do {
            try {
                switch (estadoUsuario) {
                    case MENU -> menu();
                    case INSERINDO_CONTATO -> inserirContato();
                    case LISTANDO_CONTATOS -> listarContatos();
                    case PROCURANDO_CONTATO -> procurarContato();
                    case REMOVENDO_CONTATO -> removerContato();
                }
            } catch (Exception e) {
                System.out.println("\nOcorreu um erro inesperado na aplicação!");
                System.out.println("->> " + e);
                System.out.println("\nAperte ENTER para voltar para o menu!");
                var str = consoleEntrada.nextLine();
                System.out.println("\nReiniciando a aplicação...");
                estadoUsuario = MENU;
            }
        } while (estadoUsuario != SAINDO);

        System.out.println("\nEncerrando a aplicação...");
        service.close();
        consoleEntrada.close();
    }

    private static void menu() {
        System.out.println("\n\n************* MENU *************\n");
        System.out.println("1 - Inserir Contato");
        System.out.println("2 - Listar Contatos");
        System.out.println("3 - Procurar Contato");
        System.out.println("4 - Remover Contato");
        System.out.println("5 - Sair\n");
        System.out.print("->> Qual sua escolha: ");

        var opcaoInformada = consoleEntrada.nextLine();

        if (opcaoInformada.length() < 1) {
            throw new NullPointerException("Nenhuma opção válida informada!");
        }

        switch (opcaoInformada.substring(0, 1)) {
            case "1" -> estadoUsuario = INSERINDO_CONTATO;
            case "2" -> estadoUsuario = LISTANDO_CONTATOS;
            case "3" -> estadoUsuario = PROCURANDO_CONTATO;
            case "4" -> estadoUsuario = REMOVENDO_CONTATO;
            case "5" -> estadoUsuario = SAINDO;
            default -> {
                System.out.println("\nOpção escolhida inválida!");
                System.out.println("Aperte ENTER para voltar para o menu!");
                var str = consoleEntrada.nextLine();
            }
        }

    }

    private static void inserirContato() {
        System.out.println("\n\n************* INSERINDO CONTATO *************\n");
        System.out.println("->> Informe os dados do contato:\n");

        var novoContato = new Contato();

        System.out.print("> Nome completo: ");
        novoContato.setNomeCompleto(consoleEntrada.nextLine());

        System.out.print("\n> Telefone: ");
        novoContato.setTelefone(consoleEntrada.nextLine());

        System.out.print("\n> Email: ");
        novoContato.setEmail(consoleEntrada.nextLine());

        var endereco = new Endereco();
        novoContato.setEndereco(endereco);

        System.out.println("\n\n->> Agora os dados de endereço: \n");

        System.out.print("\n> Logradouro: ");
        endereco.setLogradouro(consoleEntrada.nextLine());

        System.out.print("\n> Número: ");
        endereco.setNumero(consoleEntrada.nextLine());

        System.out.print("\n> Complemento: ");
        endereco.setComplemento(consoleEntrada.nextLine());

        System.out.print("\n> Bairro: ");
        endereco.setBairro(consoleEntrada.nextLine());

        System.out.print("\n> Cidade: ");
        endereco.setCidade(consoleEntrada.nextLine());

        System.out.print("\n> UF: ");
        endereco.setUf(consoleEntrada.nextLine());

        System.out.print("\n> CEP: ");
        endereco.setCep(consoleEntrada.nextLine());

        var contatoFoiSalvo = service.salvarContato(novoContato);
        if (contatoFoiSalvo) {
            System.out.println("\nContato salvo com sucesso!");
        } else {
            System.out.println("Não foi possível salvar o contato!");
        }

        System.out.println("Aperte ENTER para voltar para o menu!");
        var str = consoleEntrada.nextLine();
        estadoUsuario = MENU;
    }

    private static void listarContatos() {
        System.out.println("\n\n************* LISTANDO CONTATOS *************\n");

        var contatos = new ArrayList<>(service.obterContatos());
        sort(contatos);

        if (contatos.isEmpty()) {
            System.out.println("->> Você ainda não possui contatos salvos!");
        } else {
            var sequencialContato = 1;
            for (Contato contato : contatos) {
                System.out.println("->> " + sequencialContato + "° Contato:\n");
                System.out.println("> Nome completo: " + contato.getNomeCompleto());
                System.out.println("> Telefone: " + contato.getTelefone());
                System.out.println("> Email: " + contato.getEmail());
                System.out.println("> Logradouro: " + contato.getEndereco().getLogradouro());
                System.out.println("> Numero: " + contato.getEndereco().getNumero());
                System.out.println("> Complemento: " + contato.getEndereco().getComplemento());
                System.out.println("> Bairro: " + contato.getEndereco().getBairro());
                System.out.println("> Cidade: " + contato.getEndereco().getCidade());
                System.out.println("> UF: " + contato.getEndereco().getUf());
                System.out.println("> CEP: " + contato.getEndereco().getCep());
                System.out.println("\n\n***************************\n");
                sequencialContato++;
            }
        }

        System.out.println("Aperte ENTER para voltar ao menu!");
        var str = consoleEntrada.nextLine();
        estadoUsuario = MENU;
    }

    private static void procurarContato() {
        System.out.println("\n\n************* PROCURANDO CONTATO *************\n");
        System.out.print("->> Informe o identificador do contato (Nome completo ou Telefone): ");

        var identificadorContato = consoleEntrada.nextLine();
        var possiveisContatosEncontrados = new ArrayList<>(service.procurarContato(identificadorContato));
        sort(possiveisContatosEncontrados);

        if (possiveisContatosEncontrados.isEmpty()) {
            System.out.println("\n\n> Nenhum contato com o identificador " + identificadorContato + " foi encontrado!");
        } else {

            System.out.println("\n\n> Estes foram os contatos encontrados:\n");
            var sequencialContato = 1;
            for (Contato contato : possiveisContatosEncontrados) {
                System.out.println("->> " + sequencialContato + "° Contato:\n");
                System.out.println("> Nome completo: " + contato.getNomeCompleto());
                System.out.println("> Telefone: " + contato.getTelefone());
                System.out.println("> Email: " + contato.getEmail());
                System.out.println("> Logradouro: " + contato.getEndereco().getLogradouro());
                System.out.println("> Numero: " + contato.getEndereco().getNumero());
                System.out.println("> Complemento: " + contato.getEndereco().getComplemento());
                System.out.println("> Bairro: " + contato.getEndereco().getBairro());
                System.out.println("> Cidade: " + contato.getEndereco().getCidade());
                System.out.println("> UF: " + contato.getEndereco().getUf());
                System.out.println("> CEP: " + contato.getEndereco().getCep());
                System.out.println("\n\n***************************\n");
                sequencialContato++;
            }
        }

        System.out.println("\n\nAperte ENTER para voltar para o menu!");
        var str = consoleEntrada.nextLine();
        estadoUsuario = MENU;
    }

    private static void removerContato() {
        System.out.println("\n\n************* REMOVENDO CONTATO *************\n");
        System.out.print("->> Informe o identificador do contato (Nome completo ou Telefone): ");

        var identificadorContato = consoleEntrada.nextLine();
        var possiveisContatosToRemove = new ArrayList<>(service.procurarContato(identificadorContato));
        sort(possiveisContatosToRemove);

        if (possiveisContatosToRemove.isEmpty()) {
            System.out.println("\n\n>Nenhum contato com o identificador " + identificadorContato + " foi encontrado!");
        } else {

            System.out.println("\n\n> Estes são os possíveis contatos a serem removidos:\n");
            for (Contato c : possiveisContatosToRemove) {
                System.out.println("> ID: " + c.getId());
                System.out.println("> Nome completo: " + c.getNomeCompleto());
                System.out.println("> Telefone: " + c.getTelefone());
                System.out.println("> Email: " + c.getEmail());
                System.out.println("> Logradouro: " + c.getEndereco().getLogradouro());
                System.out.println("> Numero: " + c.getEndereco().getNumero());
                System.out.println("> Complemento: " + c.getEndereco().getComplemento());
                System.out.println("> Bairro: " + c.getEndereco().getBairro());
                System.out.println("> Cidade: " + c.getEndereco().getCidade());
                System.out.println("> UF: " + c.getEndereco().getUf());
                System.out.println("> CEP: " + c.getEndereco().getCep());
                System.out.println("\n\n***************************\n");
            }

            System.out.println("->> Informe o ID do contato a ser removido: ");
            var idContatoStr = consoleEntrada.nextLine();
            if (!idContatoStr.matches("^[0-9]{1,6}$")) {
                throw new RuntimeException("ID informado inválido!");
            }

            var idContato = Integer.valueOf(idContatoStr);
            var possivelContato = possiveisContatosToRemove
                    .stream()
                    .filter(contato -> idContato.equals(contato.getId()))
                    .findFirst();

            if (possivelContato.isEmpty()) {
                throw new RuntimeException("ID informado não condiz com nenhum contato mostrado!");
            }

            var contatoFoiRemovido = service.removerContato(idContato);
            if (contatoFoiRemovido) {
                System.out.println("\n> Contato removido com sucesso!");
            } else {
                System.out.println("\n> Não foi possível remover o contato com identificador " + identificadorContato);
            }
        }

        System.out.println("\n\nAperte ENTER para voltar para o menu!");
        var str = consoleEntrada.nextLine();
        estadoUsuario = MENU;
    }
}
