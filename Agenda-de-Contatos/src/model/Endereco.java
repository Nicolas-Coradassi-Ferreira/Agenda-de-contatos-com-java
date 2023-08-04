package model;

import exception.CampoInvalidoException;

public final class Endereco {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;


    public Endereco(){
        this.logradouro = "UNDEFINED";
        this.numero = "UNDEFINED";
        this.complemento = "UNDEFINED";
        this.bairro = "UNDEFINED";
        this.cidade = "UNDEFINED";
        this.uf = "UNDEFINED";
        this.cep = "UNDEFINED";
    }

    public Endereco(String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) {
        this.setLogradouro(logradouro);
        this.setNumero(numero);
        this.setComplemento(complemento);
        this.setBairro(bairro);
        this.setCidade(cidade);
        this.setUf(uf);
        this.setCep(cep);
    }


    public void setLogradouro(String logradouro) {
        if (logradouro == null) {
            throw new NullPointerException("Referência nula para o logradouro do endereco!");

        } else if (logradouro.isBlank()){
            throw new CampoInvalidoException("Logradouro não informado!");
        }
        this.logradouro = logradouro;
    }

    public void setNumero(String numero) {
        String regexNumero = "^[0-9]{1,5}$";
        if (numero == null) {
            throw new NullPointerException("Referência nula para o número do endereço!");

        } else if (!numero.matches(regexNumero)) {
            throw new CampoInvalidoException("Número de endereço inválido, apenas dígitos numéricos entre 1 a 5 dígitos!");
        }
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = (complemento != null) ? complemento : "";
    }

    public void setBairro(String bairro) {
        if (bairro == null) {
            throw new NullPointerException("Referência nula para o bairro do endereço!");

        } else if (bairro.isBlank()){
            throw new CampoInvalidoException("Bairro não informado!");
        }
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        if (cidade == null) {
            throw new NullPointerException("Referência nula para a cidade do endereço!");

        } else if (cidade.isBlank()){
            throw new CampoInvalidoException("Cidade não informada!");
        }
        this.cidade = cidade;
    }

    public void setUf(String uf) {
        String regexUf = "^[A-Z]{2}$";
        if (uf == null) {
            throw new NullPointerException("Referência nula para a UF do endereço!");

        } else if (!uf.matches(regexUf)) {
            throw new CampoInvalidoException("UF informada fora do padrão, apenas 2 dígitos alfabéticos em caixa alta!");
        }
        this.uf = uf;
    }

    public void setCep(String cep) {
        String regexCep = "^[0-9]{8}$";
        if (cep == null) {
            throw new NullPointerException("Referência nula para o CEP do endereço!");

        } else if (!cep.matches(regexCep)) {
            throw new CampoInvalidoException("CEP informado fora do padrão, deve ser 8 dígitos numéricos!");
        }
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }
}
