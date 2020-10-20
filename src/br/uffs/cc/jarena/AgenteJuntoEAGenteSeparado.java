/**
 * Somos a equipe "Agente junto e a gente separado", formados pelos integrantes Alex Sandro Zarpelon e Bruna Gabriela Disner
 */

package br.uffs.cc.jarena;

import javax.net.ssl.TrustManagerFactorySpi;

public class AgenteJuntoEAGenteSeparado extends Agente
{
    int contador;
    int time = 20;
    int tempo = 0;
    boolean teste;
    int recebeX = getX();
    int recebeY = getY();

    public AgenteJuntoEAGenteSeparado(Integer x, Integer y, Integer energia) {
        super(400, 500, energia);
        setDirecao(geraDirecaoAleatoria());
        contador = 0;
    }
   
    public void DirecaoContraria() {

        if ((contador % time) == 0) {
            if (getX() > recebeX) {
                podeMoverPara(ESQUERDA);
                
            } else {
                podeMoverPara(DIREITA);
            }
        }

        if ((contador % time) == 1) {
            if (getY() > recebeY) {
                podeMoverPara(BAIXO);
            } else {
                podeMoverPara(CIMA);
            }
        }

        System.out.println("FUNCIONNAAAAAA");

        
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

        

	}
	
	public void recebeuEnergia() {
        enviaMensagem("Estou no cogumelo");
        tempo++;
        
        teste = true;
        para();
    
        tempo++;
        if((podeDividir() && getEnergia() >= 700) && (tempo > 35)) {
                divide();
                tempo = 0;
                System.out.println("me dividi");
            }
        }
	
	
	public void tomouDano(int energiaRestanteInimigo) {

        if (getEnergia() < 10) {
            morre();
        }

        enviaMensagem("Estou tomando dano aqui");


        if (getEnergia() < 300) {
            podeMoverPara(ESQUERDA);

            if (getEnergia() < 280) {
                podeMoverPara(CIMA);
            }
        }
        
        
    }
	
	public void ganhouCombate() {
        setDirecao(geraDirecaoAleatoria());
	}
	
	public void recebeuMensagem(String msg) {

        if(msg.equals("Estou no cogumelo")) {
            DirecaoContraria();
            
        }
            

        if(msg.equals("Estou tomando dano aqui")) {
            DirecaoContraria();
        }

            
    
    }
    
    
    










	    public String getEquipe() {
		    return "AgenteJuntoEAGenteSeparado";
	        }
    
}
