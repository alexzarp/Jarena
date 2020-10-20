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
    int contaVitoria = 0;
    boolean lutando = false;
    boolean anda = false;

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
        
    }
	

    
    
    public void pensa() {
        contador++;

		if(!podeMoverPara(getDirecao()) || (isParado() && !teste)) {
            setDirecao(geraDirecaoAleatoria());
            
        }

        if ((contador % time) == 0) {
            setDirecao(geraDirecaoAleatoria());
        }

        if ((getEnergia() < 170) && !lutando) {
            para();
                        
        } else {
            lutando = false;
        }
        
        anda = false;
	}
	
	public void recebeuEnergia() {
        enviaMensagem("Estou no cogumelo");
        tempo++;
        
        teste = true;
        para();
    
        tempo++;
        if((podeDividir() && getEnergia() >= 800) && (tempo > 35)) {
                divide();
                tempo = 0;
                System.out.println("me dividi");
            }
        }
	
    
        

	public void tomouDano(int energiaRestanteInimigo) {

        if (getEnergia() < 10) {
            morre();
        }
        lutando = true;
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
        enviaMensagem("matei esse bagaceira");
        System.out.print("Matamos um total de " + contaVitoria + " inimigos");
	}
	
	public void recebeuMensagem(String msg) {

        if(msg.equals("Estou no cogumelo")) {
            DirecaoContraria();
            anda = true;
        }

        if(msg.equals("Estou tomando dano aqui")) {
            DirecaoContraria();
        }

        if(msg.equals("matei esse bagaceira")) {
            contaVitoria++;
        }
    
    }
    
    
    










	    public String getEquipe() {
		    return "AgenteJuntoEAGenteSeparado";
	        }
    
}
