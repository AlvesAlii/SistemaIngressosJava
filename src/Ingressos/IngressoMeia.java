
package Ingressos;

import java.sql.Date;

public class IngressoMeia extends Ingresso {
    public IngressoMeia(int id, String evento, Date data, double valorBase, String detalhe) {
        super(id, evento, data, valorBase, detalhe);
    }

    @Override
    public double calcularValor() {
        return valorBase * 0.5; // 50% de desconto
    }
}