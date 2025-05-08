
package Ingressos;

import java.sql.Date;

public class IngressoVIP extends Ingresso {
    public IngressoVIP(int id, String evento, Date data, double valorBase, String detalhe) {
        super(id, evento, data, valorBase, detalhe);
    }

    @Override
    public double calcularValor() {
        return valorBase * 1.5; // 50% de acréscimo
    }
}