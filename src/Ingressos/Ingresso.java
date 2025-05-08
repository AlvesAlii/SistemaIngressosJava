// Classe base abstrata
package Ingressos;

import java.sql.Date;

public abstract class Ingresso {
    protected int id;
    protected String evento;
    protected Date data;
    protected double valorBase;
    protected String detalhe;

    public Ingresso(int id, String evento, Date data, double valorBase, String detalhe) {
        this.id = id;
        this.evento = evento;
        this.data = data;
        this.valorBase = valorBase;
        this.detalhe = detalhe;
    }

    // Método abstrato que vai ser implementado pelas subclasses
    public abstract double calcularValor();

    // Getters
    public String getEvento() { return evento; }
    public Date getData() { return data; }
    public String getDetalhe() { return detalhe; }
}