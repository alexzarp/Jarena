/**
 * Um exemplo de agente que anda aleatoriamente na arena. Esse agente pode ser usado como base
 * para a criação de um agente mais esperto. Para mais informações sobre métodos que podem
 * ser utilizados, veja a classe Agente.java.
 * 
 * Fernando Bevilacqua <fernando.bevilacqua@uffs.edu.br>
 */

package br.uffs.cc.jarena;

public class AgenteTeamWarrenBuffett extends Agente
{
    int contador;

    public AgenteTeamWarrenBuffett(Integer x, Integer y, Integer energia) {
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
        
        if(contador == 10) {
            //divide();
            //setDirecao(geraDirecaoAleatoria());
        }

        if(contador >= 20) {
            contador = 0;
        }
        
        if(Math.random() < 0.2) {
            setDirecao(geraDirecaoAleatoria());
        }

		// Se o agente conseguie se dividir (tem energia) e se o total de energia
		// do agente é maior que 400, nos dividimos. O agente filho terá a metade
		// da nossa energia atual.
		if(podeDividir() && getEnergia() >= 800) {
			//divide();
		}
	}
	
	public void recebeuEnergia() {
        // Invocado sempre que o agente recebe energia.
        System.out.println(getId() + " -> ESTOU NO COGUMELO, x= " + getX() + " y = " + getY());

        // "10|30"
        enviaMensagem("f");
        enviaMensagem("a");
	}
	
	public void tomouDano(int energiaRestanteInimigo) {
		// Invocado quando o agente está na mesma posição que um agente inimigo
        // e eles estão batalhando (ambos tomam dano).
        System.out.println(getId() + " -> estou tomando pipoco aqui, x= " + getX() + " y = " + getY() + ", ini = " + energiaRestanteInimigo);

        para();
	}
	
	public void ganhouCombate() {
		// Invocado se estamos batalhando e nosso inimigo morreu.
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
		// Definimos que o nome da equipe do agente é "Fernando".
		return "Fernando";
	}
}
