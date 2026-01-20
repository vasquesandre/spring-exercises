package br.andrevasques.spring_exercises.model.entitites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class Client {

    @Id
    private String id;

    private String name;
    private String cpf;

    public Client() {
    }

    public Client(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Client update(String name, String cpf) {
        if(name != null) {
            this.name = name;
        }
        if(cpf != null) {
            this.cpf = cpf;
        }
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
