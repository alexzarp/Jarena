//Integrante: Rafael Gama Fernandes
//javac ./src/br/uffs/cc/jarena/*.java ./src/br/uffs/cc/jarena/renders/simple2d/*.java -d bin/ && cd bin && java br.uffs.cc.jarena.Main && cd ..
package br.uffs.cc.jarena;

public class AgenteR extends Agente
{
    private final int COMPRIMENTO_DO_CAMINHO = 3;
    private final int INFORMACOES_POR_PASSO = 2;
    private final int TOLERANCIA_BORDA_TELA = 5;
    private final int ALCANCE_MSG = 50;

    // registro dos últimos passos tomados.
    // coluna 0 indica orientação no plano e 
    // coluna 1 indica se recebeu cura ou não 
    // no enésimo-1 passo (1 pára recebeu e 0 
    // para não recebeu)
    private int caminho[][]; 

    // quantidade de energia no último frame
    private int energiaAnterior;

    // contador auxiliar para limitar o número de
    // vezes que muda de orientação para seguir
    // uma fonte de energia
    private int tentativasMudancaOrientacao;

    // contador auxiliar para limitar o número de
    // passos dados no mesmo sentido
    private int passosNaMesmaOrientacao;

    // aumenta a aletoriedade entre os padrões de
    // movimento dos agentes
    private int tipo;

    private boolean recebeuEnergia;
    private boolean tomouDano;

    // direções das mensagens recebidas
    int dirX, dirY;

	public AgenteR(Integer x, Integer y, Integer energia) {
		super(x, y, energia);
        setDirecao(geraDirecaoAleatoria());
        caminho = new int[COMPRIMENTO_DO_CAMINHO][INFORMACOES_POR_PASSO];
        caminho[0][0] = getDirecao();
        caminho[0][1] = 0;
        energiaAnterior = getEnergia();
        tentativasMudancaOrientacao = 0;
        passosNaMesmaOrientacao = 0;
        tipo = Math.random() > 0.5 ? 1 : 0;
        recebeuEnergia = false;
        tomouDano = false;
        dirX = dirY = 0;
    }
	
	public void pensa() {

        // aumenta a aleatoriedade do percurso
        if((passosNaMesmaOrientacao > 50 && tipo == 1) || (passosNaMesmaOrientacao > 25 && tipo == 0))
        {
            passosNaMesmaOrientacao = 0;
            moverOrtogonalmente();
        }

        // garante que não vai estar nas extremidades da tela
        // extremidade direita
        if(getX() > Constants.LARGURA_MAPA - Constants.PONTO_ENERGIA_AREA + TOLERANCIA_BORDA_TELA)
        {
            if(getDirecao() == Agente.DIREITA)
                moverSentidoOposto();
            else if(getDirecao() != Agente.ESQUERDA)
                moverOrtogonalmente();
        }
        // extremidade esquerda
        else if(getX() < Constants.PONTO_ENERGIA_AREA - TOLERANCIA_BORDA_TELA)
        {
            if(getDirecao() == Agente.ESQUERDA)
                moverSentidoOposto();
            else if(getDirecao() != Agente.DIREITA)
                moverOrtogonalmente();
        }
        // extremidade inferior
        else if(getY() > Constants.ALTURA_TELA - Constants.PONTO_ENERGIA_AREA + TOLERANCIA_BORDA_TELA)
        {
            if(getDirecao() == Agente.BAIXO)
                moverSentidoOposto();
            else if(getDirecao() != Agente.CIMA)
                moverOrtogonalmente();
        }
        // extremidade superior
        else if(getY() < Constants.PONTO_ENERGIA_AREA - TOLERANCIA_BORDA_TELA)
        {
            if(getDirecao() == Agente.CIMA)
               moverSentidoOposto();
            else if(getDirecao() != Agente.BAIXO)
                moverOrtogonalmente();
        }

         // verifica se recebeu energia no último frame,
         // mas teve cura cortada no frame atual. então
         // muda de sentido
        if(caminho[1][1] == 1 && caminho[0][1] == 0)
        {
            moverSentidoOposto();
            tentativasMudancaOrientacao += 1;
        }

        // se mudar o sentido não funcionou, tenta mudar de direção
        else if(caminho[0][1] == 0 && caminho[1][1] == 0 && caminho[2][1] == 1)
        {
            moverOrtogonalmente();
            tentativasMudancaOrientacao += 1;
        }
       
        // se ele ainda não acertou a orientação, muda de sentido uma última vez
        if(tentativasMudancaOrientacao == 2 && getEnergia() < energiaAnterior)
        {
            moverSentidoOposto();
            tentativasMudancaOrientacao = 0;
        }

		if(!podeMoverPara(getDirecao())) {

            setDirecao(geraDirecaoAleatoria());
		}

        atualizaCaminho();
        energiaAnterior = getEnergia();

        passosNaMesmaOrientacao++;
        
        recebeuEnergia = false;
        tomouDano = false;

        if(dirX != 0)
        {
            if(getX() < dirX)
                setDirecao(Agente.DIREITA);
            else if(getX() > dirX)
                setDirecao(Agente.ESQUERDA);
            else
                dirX = 0;
        }
        if(dirY != 0)
        {
            if(getY() < dirY)
                setDirecao(Agente.BAIXO);
            else if(getY() > dirY)
                setDirecao(Agente.CIMA);
            else
                dirY = 0;
        }

	}

    public void moverSentidoOposto()
    {
        switch (getDirecao()) {
            case Agente.BAIXO:
                if(podeMoverPara(Agente.CIMA))
                    setDirecao(Agente.CIMA);
                break;

                case Agente.CIMA:
                if(podeMoverPara(Agente.BAIXO))
                    setDirecao(Agente.BAIXO);
                break;

                case Agente.ESQUERDA:
                if(podeMoverPara(Agente.DIREITA))
                    setDirecao(Agente.DIREITA);
                break;

            default:
                if(podeMoverPara(Agente.ESQUERDA))
                    setDirecao(Agente.ESQUERDA);
                break;
        }
    }

    public void moverOrtogonalmente()
    {
        if(getDirecao() == Agente.BAIXO || getDirecao() == Agente.CIMA)
            setDirecao(Math.random() >= .5 && podeMoverPara(Agente.ESQUERDA) ? Agente.ESQUERDA : podeMoverPara(Agente.DIREITA) ? Agente.DIREITA : getDirecao());
        else
            setDirecao(Math.random() >= 0.5 && podeMoverPara(Agente.ESQUERDA) ? Agente.CIMA : podeMoverPara(Agente.BAIXO) ? Agente.BAIXO : getDirecao());
    }

    // atualiza array de passos e insere o atual
    public void atualizaCaminho()
    {
        for(int i = COMPRIMENTO_DO_CAMINHO - 1; i > 0; i--)
        {
            caminho[i][0] = caminho[i - 1][0];
            caminho[i][1] = caminho[i - 1][1];
        }

        caminho[0][0] = getDirecao();
        caminho[0][1] = getEnergia() > energiaAnterior ? 1 : 0;
    }

    public void imprimeCaminho()
    {
        for(int i = COMPRIMENTO_DO_CAMINHO - 1; i >= 0; i--)
            System.out.println(getId() + " was moving to " + caminho[i][0] + " and was" + (caminho[i][1] == 0 ? "n't" : "") + " getting healed at step " + i + "\n");
    }

	public void recebeuEnergia() {
        if(podeDividir() && getEnergia() >= 1000) {
			divide();
        }
        recebeuEnergia = true;
        enviaMensagem(getX() + "," + getY() + ",2");
	}
	
	public void tomouDano(int energiaRestanteInimigo) {
        if(recebeuEnergia || getEnergia() > energiaRestanteInimigo)
            para();
        else
            setDirecao(geraDirecaoAleatoria());
        enviaMensagem(getX() + "," + getY() + ",1");
        tomouDano = true;
	}
	
	public void ganhouCombate() {
        if(!recebeuEnergia)
        {
            setDirecao(geraDirecaoAleatoria());
        }
	}
	
	public void recebeuMensagem(String msg) {
        String m[] = msg.split(",");
        int s = Integer.parseInt(m[2]);

        //  vai ajudar o colega      vai atras de fonte de Energia
        if(((s == 1 && !tomouDano) || (s == 2 && !recebeuEnergia))
            && Math.abs(getX() - Integer.parseInt(m[0])) < ALCANCE_MSG
            && Math.abs(getY() - Integer.parseInt(m[1])) < ALCANCE_MSG)
        {
            dirX = Integer.parseInt(m[0]);
            dirY = Integer.parseInt(m[1]);
        }

	}
	
	public String getEquipe() {
		return "Rafael";
	}
}
