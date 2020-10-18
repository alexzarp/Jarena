/**
 * Somos a equipe "Agente junto e a gente separado", formados pelos integrantes Alex Sandro Zarpelon e Bruna Gabriela Disner
 */

package br.uffs.cc.jarena;

public class AgenteJuntoEAGenteSeparado extends Agente
{
    int contador;

    public AgenteJuntoEAGenteSeparado(Integer x, Integer y, Integer energia) {
        super(x, y, energia);
        setDirecao(geraDirecaoAleatoria());
        contador = 0;
	}
	
	public void pensa() {
        contador++;

		// Se não conseguimos nos mover para a direção atual, quer dizer
		// que chegamos no final do mapa ou existe algo bloqueando nosso
		// caminho.
		if(!podeMoverPara(getDirecao())) {
			// Como não conseguimos nos mover, vamos escolher uma direção
			// nova.
			setDirecao(geraDirecaoAleatoria());
        }
        
        if(contador%5 == 0) {
            divide();
            setDirecao(geraDirecaoAleatoria());
        }

        if(contador >= 50) {
            contador = 0;
        }
        
        /*if(Math.random() < 0.2) {
            setDirecao(geraDirecaoAleatoria());
        }*/

		// Se o agente conseguie se dividir (tem energia) e se o total de energia
		// do agente é maior que 400, nos dividimos. O agente filho terá a metade
		// da nossa energia atual.
		if(podeDividir() && getEnergia() >= 800) {
			divide();
        }
        
        
        if (isParado()) {
            setDirecao(geraDirecaoAleatoria());
        }

        

	}
	
	public void recebeuEnergia() {
        // Invocado sempre que o agente recebe energia.
        System.out.println(getId() + " -> ESTOU NO COGUMELO, x= " + getX() + " y = " + getY());
        para();
        // "10|30"
        enviaMensagem("f");
        enviaMensagem("a");
	}
	
	public void tomouDano(int energiaRestanteInimigo) {
		// Invocado quando o agente está na mesma posição que um agente inimigo
        // e eles estão batalhando (ambos tomam dano).
        System.out.println(getId() + " -> estou tomando pipoco aqui, x= " + getX() + " y = " + getY() + ", ini = " + energiaRestanteInimigo);

        para();
        setDirecao(geraDirecaoAleatoria());
	}
	
	public void ganhouCombate() {
        // Invocado se estamos batalhando e nosso inimigo morreu.
        setDirecao(geraDirecaoAleatoria());
	}
	
	public void recebeuMensagem(String msg) {
        // Invocado sempre que um agente aliado próximo envia uma mensagem.
        // msg = "44444|43949494"

        if(msg.equals("f")) {
            setDirecao(geraDirecaoAleatoria());
        }

        if(msg.equals("a")) {
            setDirecao(geraDirecaoAleatoria());
        }
        System.out.println("recebi uma mensagem: " + msg);
	}
	
	public String getEquipe() {
		return "AgenteJuntoEAGenteSeparado";
	}
}
