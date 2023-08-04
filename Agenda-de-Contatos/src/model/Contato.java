package model;

import exception.CampoInvalidoException;

public final class Contato implements Comparable<Contato> {

    private Integer id;
    private String nomeCompleto;
    private String telefone;
    private String email;
    private Endereco endereco;


    public Contato() {
        this.nomeCompleto = "UNDEFINED";
        this.telefone = "UNDEFINED";
        this.email = "UNDEFINED";
        this.endereco = new Endereco();
    }

    public Contato(Integer id, String nomeCompleto, String telefone, String email, Endereco endereco) {
        setId(id);
        setNomeCompleto(nomeCompleto);
        setTelefone(telefone);
        setEmail(email);
        setEndereco(endereco);
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setNomeCompleto(String nomeCompleto) {
        if (nomeCompleto == null) {
            throw new NullPointerException("Referência nula para o nome completo do contato!");

        } else if (nomeCompleto.isBlank()){
            throw new CampoInvalidoException("Nome completo do contato não informado!");
        }
        this.nomeCompleto = nomeCompleto;
    }

    public void setTelefone(String telefone) {
        String regexTelefone = "^[0-9]{9,13}$";
        if (telefone == null) {
            throw new NullPointerException("Referência nula para o telefone do contato!");

        } else if (!telefone.matches(regexTelefone)) {
            throw new CampoInvalidoException("Telefone informado fora do padrão, apenas dígitos númericos entre 9 a 13 dígitos!");
        }
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = (email != null) ? email : "";
    }

    public void setEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new NullPointerException("Referência nula para o endereço do contato!");
        }
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    @Override
    public int compareTo(Contato c) {
        return this.nomeCompleto.compareTo(c.nomeCompleto);
    }
}
