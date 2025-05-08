
package Ingressos;

import java.sql.Date;

public class IngressoNormal extends Ingresso {
    public IngressoNormal(int id, String evento, Date data, double valorBase, String detalhe) {
        super(id, evento, data, valorBase, detalhe);
    }

    @Override
    public double calcularValor() {
        return valorBase; // Valor sem alterações
    }
}