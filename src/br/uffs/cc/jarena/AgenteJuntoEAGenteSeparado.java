/**
 * Somos a equipe "Agente junto e a gente separado", formados pelos integrantes Alex Sandro Zarpelon e Bruna Gabriela Disner
 */

package br.uffs.cc.jarena;

import javax.net.ssl.TrustManagerFactorySpi;

public class AgenteJuntoEAGenteSeparado extends Agente
{
    int contador;
    int time = 20;
    boolean teste;

    public AgenteJuntoEAGenteSeparado(Integer x, Integer y, Integer energia) {
        super(400, 500, energia);
        setDirecao(geraDirecaoAleatoria());
        contador = 0;
	}
	
	public void pensa() {
        contador++;

		if(!podeMoverPara(getDirecao()) || (isParado() && !teste)) {
            setDirecao(geraDirecaoAleatoria());
            teste = false;
        }

        if ((contador % time) == 0) {
            setDirecao(geraDirecaoAleatoria());
        }

        /*if ((getEnergia() >= 800) && Math.random() > 0.85){
            divide();
        }*/

	}
	
	public void recebeuEnergia() {
        enviaMensagem("Estou no cogumelo");
        teste = true;
        para();
        
	}
	
	public void tomouDano(int energiaRestanteInimigo) {
        setDirecao(geraDirecaoAleatoria());

        if (getEnergia() < 10) {
            morre();
        }

	}
	
	public void ganhouCombate() {
        
        setDirecao(geraDirecaoAleatoria());
	}
	
	public void recebeuMensagem(String msg) {

        if(msg.equals("Estou no cogumelo")) {
            if ((contador % time) == 0) {
                setDirecao(geraDirecaoAleatoria());
            }
        }

        if (msg.equals("Help")) {
            setDirecao(getDirecao());
        }
        System.out.println("recebi uma mensagem: " + msg);
        
	}
    
    










	public String getEquipe() {
		return "AgenteJuntoEAGenteSeparado";
	}
}
