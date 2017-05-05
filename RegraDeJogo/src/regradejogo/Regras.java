package regradejogo;

import Excecoes.JogadaInvalidaException;
import Excecoes.PosicaoInvalidaException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import regradejogo.Tabuleiro.Inclinacao;

/**
 * Classe responsável por controlar as regras do jogo.
 */
public class Regras {

    /**
     * Instancia do tabuleiro que vai ser regido pela Regras.
     */
    private Tabuleiro tabuleiro = new Tabuleiro();

    /**
     * Turno atual do jogo.
     */
    private int turnoAtual = 0;

    /**
     * Interface de callback.
     */
    private BoardChangedListener boardChangedListener;

    /**
     * Identificadores dos jogadores.
     */
    public static final int JOGADOR_UM = 1;
    public static final int JOGADOR_DOIS = 2;

    /**
     * Jogador atual do turno.
     */
    private int jogadorAtual = JOGADOR_UM;

    /**
     * Numero de pecas de cada jogador.
     */
    private int nPecasJogador1 = 12;
    private int nPecasJogador2 = 12;

    private boolean jogoFinalizado;

    private List<Jogada> historicoJogador1 = new ArrayList<>();
    private List<Jogada> historicoJogador2 = new ArrayList<>();

    public Regras() {

    }

    /**
     * Inicia o jogo a partir de um arquivo.
     *
     * @param nomeArquivo caminho do arquivo .txt
     * @throws IOException
     */
    public Regras(String nomeArquivo) throws IOException {
        tabuleiro = new Tabuleiro(nomeArquivo);
    }

    public Regras(InputStream inputStream) throws IOException {
        tabuleiro = new Tabuleiro(inputStream);
    }

    public Regras(Tabuleiro tabuleiro, int turnoAtual, int jogadorAtual, int nPecasJogador1, int nPecasJogador2) {
        this.tabuleiro = tabuleiro;
        this.turnoAtual = turnoAtual;
        this.jogadorAtual = jogadorAtual;
        this.nPecasJogador1 = nPecasJogador1;
        this.nPecasJogador2 = nPecasJogador2;
        historicoJogador1 = new ArrayList<>();
        historicoJogador2 = new ArrayList<>();
    }

    /**
     * Move uma peça do tabuleiro a partir das regras.
     *
     * @param posInicial posição que a peça a ser movida está.
     * @param posFinal posição de destino.
     */
    protected void moverPeca(Posicao posInicial, Posicao posFinal) {
        /**
         * Verifico se ambas posições pertencem ao conjunto de posições do
         * tabuleiro.
         */
        if (!tabuleiro.posValida(posInicial)) {
            throw new PosicaoInvalidaException("Posicao invalida, fora da dimensao do tabuleiro, Index:" + posInicial.toString() + ".");
        }

        if (!tabuleiro.posValida(posFinal)) {
            throw new PosicaoInvalidaException("Posicao invalida, fora da dimensao do tabuleiro, Index:" + posFinal.toString() + ".");
        }

        /**
         * Recupero a peça na posição atual.
         */
        Peca peca = getPeca(posInicial);

        if (peca == null) {
            throw new PosicaoInvalidaException("Nao existe nenhuma peca na posicao " + posInicial.toString() + ".");
        }

        List<Jogada> jogadas = peca.isDama() ? jogadasPossiveisDama(peca) : jogadasPossiveis(peca);

        /**
         * Verifico se a posFinal é válida. Ou seja, caso exista alguma jogada
         * com a posFinal.
         */
        Jogada jogada = getJogada(jogadas, posFinal);

        /**
         * Se nenhuma jogada possível da peça atual possui essa posição como
         * final, então essa jogada é inválida.
         */
        if (jogada == null) {
            throw new JogadaInvalidaException("A posicao " + posFinal.toString() + " nao é uma jogada valida para esta peca " + tabuleiro.getPosicao(peca).toString() + ".");
        }

        /*
         * Se chegou até aqui, jogada é válida.
         * Tratar possíveis consequencias da jogada.
         * Ex: Virou dama e etc...
         */
        /**
         * Caso tenha chegado na borda.
         */
        int borda = tabuleiro.bordaSupInf(jogada.getPosFinal());
        if (borda == peca.getTime() && !peca.isDama()) {
            viraDama(peca);
            Posicao pos = tabuleiro.getPosicao(peca);
            int timeAtual = jogadorAtual == JOGADOR_UM ? JOGADOR_DOIS : JOGADOR_UM;
            if (boardChangedListener != null) {
                boardChangedListener.onKing(pos.getI(), pos.getJ(), timeAtual);
            }
        }

        /**
         * Verifico se houve captura na jogada.
         */
        if (jogada.houveCaptura()) {
            //Removo a peça capturada do tabuleiro.
            removerPeca(jogada.getPecaCapturada());

            /**
             * A partir daqui é gambiarra pra captura em sequencia.
             */
            if (!peca.isDama()) {
                List<Jogada> capturasPecaAtual = jogadasPossiveis(peca);
                if (!possuiCaptura(capturasPecaAtual)) {
                    trocaJogadorAtual();
                }
            } else {
                trocaJogadorAtual();
            }

        } else {
            trocaJogadorAtual();
        }

        /**
         * Movo a peça.
         */
        tabuleiro.movePeca(peca, posFinal);

        /**
         * Guardo o histórico de jogadas.
         */
        if (jogadorAtual == JOGADOR_UM) {
            historicoJogador1.add(jogada);
        } else {
            historicoJogador2.add(jogada);
        }

        if (boardChangedListener != null) {
            boardChangedListener.onPieceMoved(posInicial, posFinal);
        }

        incrementaTurno();
        verificaFimDeJogo();
    }

    /**
     * Dada uma lista de jogadas, retorna a jogada que possui tal posição final.
     *
     * @param jogadas Lista de jogadas a serem analisadas.
     * @return
     */
    protected Jogada getJogada(List<Jogada> jogadas, Posicao posFinal) {
        for (Jogada jogada : jogadas) {
            if (jogada.getPosFinal().equals(posFinal)) {
                return jogada;
            }
        }

        return null;
    }

    /**
     * Verifica se existem jogadas.
     *
     * @param list lista com posições.
     * @return retorna true se existe(m) jogada(s), false caso contrário.
     */
    public boolean existemJogadas(List<Jogada> list) {
        return list.isEmpty();
    }

    /**
     * Função que verifica se uma lista de posições possui mais de uma peça
     *
     * @param posicoes lista com as posições da diagonal.
     * @return retorna a posição da segunda peça, null caso tenha apenas uma
     * peça.
     */
    private Posicao getLimiteDama(List<Posicao> posicoes, int time) {
        Posicao ultimaPosValida = null;
        int count = 0;

        if (posicoes.size() == 1) {
            boolean possuiPeca = tabuleiro.existePecaPos(posicoes.get(0));

            return possuiPeca ? null : posicoes.get(0);
        }

        for (int i = 0; i < posicoes.size(); i++) {
            Posicao pos = posicoes.get(i);
            if (tabuleiro.existePecaPos(pos)) {
                if (tabuleiro.getPeca(pos).getTime() == time) {
                    return ultimaPosValida;
                }
                count++;
                if (count == 2) {
                    return ultimaPosValida;
                }
            } else {
                ultimaPosValida = pos;
            }
        }

        return ultimaPosValida;
    }

    /**
     * Filtra a diagonal da dama
     *
     * @param peca
     * @return
     */
    private List<Posicao> filtraJogadas(List<Posicao> posicoes, int time) {
        /**
         * Pega até aonde a dama pode jogar. Pode ser em alguma borda do
         * tabuleiro ou alguma peça inimiga ou amiga.
         */
        Posicao limite = getLimiteDama(posicoes, time);

        if (limite == null) {
            return new ArrayList<>();
        }

        List<Posicao> filtrada = new ArrayList<>();

        for (Posicao pos : posicoes) {
            if (pos.equals(limite)) {
                filtrada.add(pos);
                return filtrada;
            }

            filtrada.add(pos);
        }

        return filtrada;
    }

    /**
     * Retorna todas as posições pertencentes a uma diagonal da dama.
     *
     * @param i inclinação i
     * @param j inclinação j
     * @param posDama posicao da dama
     * @param time time da dama
     * @return retorna uma lista com todas as posicoes da diagonal.
     */
    private List<Posicao> getDiagonal(int i, int j, Posicao posDama, int time) {
        List<Posicao> diagonal = new ArrayList<>();

        Posicao pos = new Posicao(posDama.getI() + i, posDama.getJ() + j);
        while (tabuleiro.posValida(pos)) {
            diagonal.add(pos);
            pos = new Posicao(pos.getI() + i, pos.getJ() + j);
        }

        return diagonal;
    }

    private List<Jogada> jogadasSeguintesDama(List<Jogada> capturasDireita) {
        List<Jogada> listAux = new ArrayList<>();
        for (Jogada jogada : capturasDireita) {
            listAux.add(jogada);
            boolean aux = jogada.getPosInicial().getI() > jogada.getPosFinal().getI();
            List<Posicao> p;
            if (aux) {
                p = tabuleiro.getDiagonal(jogada.getPosFinal(), 1, -1);
            } else {
                p = tabuleiro.getDiagonal(jogada.getPosFinal(), -1, 1);
            }

            for (Posicao pAux : p) {
                listAux.add(new Jogada(jogada.getPecaCapturada(), jogada.getPecaMovida(), jogada.getPosInicial(), pAux));
            }

            //break;
        }

        return listAux;
    }

    /**
     * Retorna todas as jogadas possíveis da dama.
     *
     * @param peca
     * @return
     */
    protected List<Jogada> jogadasPossiveisDama(Peca peca) {
        if (peca.getTime() != jogadorAtual) {
            return new ArrayList<>();
        }

        Posicao posDama = tabuleiro.getPosicao(peca);

        List<Posicao> posicoes = new ArrayList<>();
        List<Posicao> diagonalEsquerda = new ArrayList<>();
        List<Posicao> diagonalDireita = new ArrayList<>();

        //Esquerda Superior
        List<Posicao> diagEsqSup = filtraJogadas(getDiagonal(-1, -1, posDama, peca.getTime()), peca.getTime());

        //Esquerda Inferior
        List<Posicao> diagEsqInf = filtraJogadas(getDiagonal(+1, +1, posDama, peca.getTime()), peca.getTime());

        //Direita Superior
        List<Posicao> diagDirSup = filtraJogadas(getDiagonal(-1, +1, posDama, peca.getTime()), peca.getTime());

        //Direita Inferior
        List<Posicao> diagDirInf = filtraJogadas(getDiagonal(+1, -1, posDama, peca.getTime()), peca.getTime());

        //Fazer função que verifica se existem mais de uma peça na diagonal.
        //Definir range da dama.
        //Se tiver, rodar o filtro.
        diagonalEsquerda.addAll(diagEsqInf);
        diagonalEsquerda.addAll(diagEsqSup);
        diagonalDireita.addAll(diagDirInf);
        diagonalDireita.addAll(diagDirSup);

        List<Jogada> capturasDireita = capturasPossiveis(diagonalDireita, peca);
        List<Jogada> capturasEsquerda = capturasPossiveis(diagonalEsquerda, peca);

        List<Jogada> listAux = new ArrayList<>();

        for (Jogada jogada : capturasDireita) {
            listAux.add(jogada);
            boolean aux = jogada.getPosInicial().getI() > jogada.getPosFinal().getI();
            List<Posicao> p;
            if (aux) {
                p = tabuleiro.getDiagonal(jogada.getPosFinal(), 1, -1);
            } else {
                p = tabuleiro.getDiagonal(jogada.getPosFinal(), -1, 1);
            }

            for (Posicao pAux : p) {
                listAux.add(new Jogada(jogada.getPecaCapturada(), jogada.getPecaMovida(), jogada.getPosInicial(), pAux));
            }

            //break;
        }

        for (Jogada jogada : capturasEsquerda) {
            listAux.add(jogada);
            boolean aux = jogada.getPosInicial().getI() > jogada.getPosFinal().getI();
            List<Posicao> p;
            if (aux) {
                p = tabuleiro.getDiagonal(jogada.getPosFinal(), -1, -1);
            } else {
                p = tabuleiro.getDiagonal(jogada.getPosFinal(), 1, 1);
            }

            for (Posicao pAux : p) {
                listAux.add(new Jogada(jogada.getPecaCapturada(), jogada.getPecaMovida(), jogada.getPosInicial(), pAux));
            }

            //break;
        }

        List<Jogada> jogadas = new ArrayList<>();
        posicoes.addAll(diagonalDireita);
        posicoes.addAll(diagonalEsquerda);
        if (listAux.isEmpty()) {
            for (Posicao p : posicoes) {
                jogadas.add(new Jogada(null, peca, posDama, p));
            }
            return jogadas;
        }

        return listAux;
    }

    /**
     * Essa função simula uma peça em uma posição qualquer e retorna as jogadas
     * possiveis.
     *
     * @param posicao posição a ser analisada
     * @return lista com as posições possiveis.
     */
    private List<Posicao> getPosicoesPossiveisPos(Posicao posicao, int time) {
        /**
         * A lista posições guarda todas as posições na quais são oriundas de
         * jogadas "normais", apenas andar pra frente.
         */
        List<Posicao> posicoes = new ArrayList<>();

        /**
         * Váriavel que ajuda a decidir se vai descer o subir na matriz. Jogador
         * 1 sempre fica em baixo.
         */
        int varJogador = (time == 1) ? -1 : 1;

        Posicao pos1 = new Posicao(posicao.getI() + varJogador, posicao.getJ() - 1);
        Posicao pos2 = new Posicao(posicao.getI() + varJogador, posicao.getJ() + 1);

        //Posições que podem conter inimigos e que estão no sentido contrário ao "normal" da peça.
        Posicao pos3 = new Posicao(posicao.getI() - varJogador, posicao.getJ() - 1);
        Posicao pos4 = new Posicao(posicao.getI() - varJogador, posicao.getJ() + 1);

        //Verifica se as posições são válidas.
        if (posicaoValida(pos1, time)) {
            posicoes.add(pos1);
        }

        if (posicaoValida(pos2, time)) {
            posicoes.add(pos2);
        }

        if (posicaoValida(pos3, time)) {
            posicoes.add(pos3);
        }

        if (posicaoValida(pos4, time)) {
            posicoes.add(pos4);
        }

        return posicoes;
    }

    /**
     * Dada uma peça, retorna todo seu entorno.
     *
     * @param peca
     * @return
     */
    protected List<Posicao> getPosicoesPossiveisPeca(Peca peca) {
        if (peca.getTime() != jogadorAtual) {
            return new ArrayList<>();
        }

        //Pega a posição da peça.
        Posicao poiscaoPeca = tabuleiro.getPosicao(peca);

        /**
         * A lista posições guarda todas as posições na quais são oriundas de
         * jogadas "normais", apenas andar pra frente.
         */
        List<Posicao> posicoes = new ArrayList<>();

        /**
         * Váriavel que ajuda a decidir se vai descer o subir na matriz. Jogador
         * 1 sempre fica em baixo.
         */
        int varJogador = (peca.getTime() == 1) ? -1 : 1;

        Posicao pos1 = new Posicao(poiscaoPeca.getI() + varJogador, poiscaoPeca.getJ() - 1);
        Posicao pos2 = new Posicao(poiscaoPeca.getI() + varJogador, poiscaoPeca.getJ() + 1);

        //Posições que podem conter inimigos e que estão no sentido contrário ao "normal" da peça.
        Posicao pos3 = new Posicao(poiscaoPeca.getI() - varJogador, poiscaoPeca.getJ() - 1);
        Posicao pos4 = new Posicao(poiscaoPeca.getI() - varJogador, poiscaoPeca.getJ() + 1);

        //Verifica se as posições são válidas.
        if (posicaoValida(pos1, peca.getTime())) {
            posicoes.add(pos1);
        }

        if (posicaoValida(pos2, peca.getTime())) {
            posicoes.add(pos2);
        }

        /**
         * Jogadas não-normais. Captura para "trás". No mínimo vai ser uma
         * captura, logo não posso adiciona-la como uma candidata.
         */
        if (posicaoValida(pos3, peca.getTime())) {
            posicoes.add(pos3);
        }

        if (posicaoValida(pos4, peca.getTime())) {
            posicoes.add(pos4);
        }

        return posicoes;
    }

    /**
     * Analisa todo o campo da peça(sem ser dama) e retorna as posições válidas.
     *
     * @param peca peça a ter as jogadas analisadas.
     * @return retorna uma lista com todas as posições válidas para jogada.
     */
    protected List<Jogada> jogadasPossiveis(Peca peca) {
        //Pega o entorno da peça.
        List<Posicao> posicoes = getPosicoesPossiveisPeca(peca);
        /**
         * A lista jogadas é fruto da analise das posições e interpretação do
         * que pode acontecer das jogadas da lista posições.
         */
        List<Jogada> jogadas = new ArrayList<>();

        /**
         * Nesse jogo de damas, captura é prioridade. Se existe, pelo menos uma,
         * jogada na qual seja uma captura ela será retornada. Aquela jogada ali
         * em cima que era candidata a ser uma jogada normal, pode cair aqui
         * dentro de capturas mas não tem problema.
         */
        List<Jogada> capturas = capturasPossiveis(posicoes, peca);

        //Caso exista alguma jogada de captura.
        if (!capturas.isEmpty()) {
            return capturas;
        }

        //Caso não tenha havido nenhuma captura, adicionar as jogadas normais.
        for (Posicao posicao : posicoes) {
            boolean teste2 = peca.getTime() == 1 ? posicao.getI() < tabuleiro.getPosicao(peca).getI() : posicao.getI() > tabuleiro.getPosicao(peca).getI();
            if (!tabuleiro.existePecaPos(posicao) && teste2) {
                jogadas.add(new Jogada(null, peca, tabuleiro.getPosicao(peca), posicao));
            }
        }

        /**
         * Se chegou até aqui quer dizer que as duas jogadas candidatas ali em
         * cima eram efetivamente jogadas normais.
         */
        return jogadas;
    }

    /**
     * Retorna todas as peças que estão áptas a jogar nesse turno.
     *
     * @return lista de todas as peças que possuem captura no turno
     */
    protected List<Peca> getPecasCaptura2() {
        List<Peca> pecasJogador = tabuleiro.getPecasJogador(jogadorAtual);
        List<Peca> pecasAptas = new ArrayList<>();

        for (Peca peca : pecasJogador) {
            if (Peca.possuiCaptura(peca, this)) {
                pecasAptas.add(peca);
            }
        }

        return pecasAptas;
    }

    /**
     * Dada uma peça e uma posição futura, verifica se é uma posição válida. Uma
     * posição é dita válida caso esteja vazia ou tenha alguma peça inimiga.
     *
     * @param pos posição a ser analisada.
     * @param time time da peça.
     * @return true caso seja valida, false caso não.
     */
    protected boolean posicaoValida(Posicao pos, int time) {
        if (!tabuleiro.posValida(pos)) {
            return false;
        }

        Peca peca2 = tabuleiro.getPeca(pos);

        //Se não tem nenhuma peça na posição, jogada é válida.
        if (peca2 == null) {
            return true;
        }

        //Se a peça na posição for do seu time, jogada inválida.
        if (peca2.getTime() == time) {
            return false;
        }

        //Caso a peça esteja na borda do tabuleiro.
        if ((pos.getI() == Tabuleiro.DIMEN - 1) || (pos.getI() == 0)) {
            return false;
        }

        if ((pos.getJ() == Tabuleiro.DIMEN - 1) || (pos.getJ() == 0)) {
            return false;
        }

        return true;
    }

    /**
     * Dada uma lista de posições, retorna todas as capturas possíveis.
     *
     * @param pos lista de posições a serem analisadas.
     * @param peca time da peça que quer caputrar.
     * @return jogadas.
     */
    protected List<Jogada> capturasPossiveis(List<Posicao> posicoes, Posicao pos, int time) {
        List<Jogada> jogadas = new ArrayList<>();

        for (Posicao posicao : posicoes) {
            Peca p = tabuleiro.getPeca(posicao);

            //Se existe alguma peça nessa posição.
            if (p != null) {
                //Se não for do time da peça que quer capturar, então é do time inimigo.
                if (p.getTime() != time) {
                    Posicao posFinal = podeComer(pos, tabuleiro.getPosicao(p));
                    if (posFinal != null) {
                        //TODO: Função que verifica capturas seguidas.
                        jogadas.add(new Jogada(p, null, pos, posFinal));
                    }
                }
            }
        }

        return jogadas;
    }

    /**
     * Dada uma lista de posições, retorna todas as capturas possíveis.
     *
     * @param pos lista de posições a serem analisadas.
     * @param peca time da peça que quer caputrar.
     * @return jogadas.
     */
    protected List<Jogada> capturasPossiveis(List<Posicao> pos, Peca peca) {
        List<Jogada> jogadas = new ArrayList<>();

        for (Posicao posicao : pos) {
            Peca p = tabuleiro.getPeca(posicao);

            //Se existe alguma peça nessa posição.
            if (p != null) {
                //Se não for do time da peça que quer capturar, então é do time inimigo.
                if (p.getTime() != peca.getTime()) {
                    Posicao posFinal = podeComer(peca, p);
                    if (posFinal != null) {
                        //TODO: Função que verifica capturas seguidas.
                        jogadas.add(new Jogada(p, peca, tabuleiro.getPosicao(peca), posFinal));
                    }
                }
            }
        }

        return jogadas;
    }

    /**
     * Função recursiva que retorna todas as capturas em sequencia.
     *
     * @param jogadas
     * @param jogada
     * @param time
     * @param rastro
     * @return
     */
    protected List<Jogada> capturasSeguidas(List<Jogada> jogadas, Jogada jogada, int time, List<Posicao> rastro) {
        List<Posicao> posicoes = getPosicoesPossiveisPos(jogada.getPosFinal(), time);
        List<Jogada> capturas = capturasPossiveis(posicoes, jogada.getPosFinal(), time);

        if (!possuiCapturaValida(capturas, rastro)) {
            return jogadas;
        }

        for (Jogada captura : capturas) {
            if (!inPosList(rastro, captura.getPosFinal())) {
                jogadas.add(jogada);
                rastro.add(jogada.getPosInicial());
                capturasSeguidas(jogadas, captura, time, rastro);
            }
        }

        return jogadas;
    }

    private boolean possuiCapturaValida(List<Jogada> capturas, List<Posicao> rastros) {
        if (rastros.isEmpty()) {
            return true;
        }

        for (Posicao rastro : rastros) {
            for (Jogada captura : capturas) {
                Posicao pos = captura.getPosFinal();
                if (!pos.equals(rastro)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean inPosList(List<Posicao> list, Posicao pos) {
        for (Posicao posicao : list) {
            if (posicao.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica se a peca1 pode comer a peca2.
     *
     * @param peca1 Peça que irá tentar capturar.
     * @param peca2 Peça a ser capturada.
     * @return caso seja possível a captura, retorna a posição final.
     */
    protected Posicao podeComer(Peca peca1, Peca peca2) {
        Posicao pos1 = tabuleiro.getPosicao(peca1);
        Posicao pos2 = tabuleiro.getPosicao(peca2);
        //Pego a inclinação relativa entre duas pecas.
        int inclinacao = tabuleiro.inclinacaoRelativa(pos1, pos2);
        //Variável que auxilia no cálculo da posição final.
        int altura = tabuleiro.ehMaisAlta(pos1, pos2) ? -1 : 1;

        Posicao posFinal;

        Posicao pos = tabuleiro.getPosicao(peca2);
        if (inclinacao == Inclinacao.ESQUERDA) {
            posFinal = new Posicao(pos.getI() + altura, pos.getJ() - 1);
        } else {
            posFinal = new Posicao(pos.getI() + altura, pos.getJ() + 1);
        }

        //Verifico se a posição é válida.
        if (!tabuleiro.posValida(posFinal)) {
            return null;
        }
        //Verifico se existe alguma peça nessa aposição.
        if (tabuleiro.getPeca(posFinal) != null) {
            return null;
        }

        return posFinal;
    }

    /**
     * Dada duas posições verifica se uma hipotética peça pode comer a outra.
     *
     * @param pos1 posição da peça que quer capturar.
     * @param pos2 posição da peça a ser capturada.
     * @return retorna a posição final caso possa comer, null caso não possa.
     */
    protected Posicao podeComer(Posicao pos1, Posicao pos2) {
        //Pego a inclinação relativa entre duas pecas.
        int inclinacao = tabuleiro.inclinacaoRelativa(pos1, pos2);
        //Variável que auxilia no cálculo da posição final.
        int altura = tabuleiro.ehMaisAlta(pos1, pos2) ? -1 : 1;

        Posicao posFinal;

        //Posição pos = tabuleiro.getPosição(peca2);
        if (inclinacao == Inclinacao.ESQUERDA) {
            posFinal = new Posicao(pos2.getI() + altura, pos2.getJ() - 1);
        } else {
            posFinal = new Posicao(pos2.getI() + altura, pos2.getJ() + 1);
        }

        //Verifico se a posição é válida.
        if (!tabuleiro.posValida(posFinal)) {
            return null;
        }
        //Verifico se existe alguma peça nessa aposição.
        if (tabuleiro.getPeca(posFinal) != null) {
            return null;
        }

        return posFinal;
    }

    /**
     * Função que verifica se o jogo terminou.
     *
     * @return true se o jogo acabou, false caso não.
     */
    private boolean verificaFimDeJogo() {
        //TODO: Verifica se ambos jogadores ainda tem peças com jogadas possíveis.
        if (nPecasJogador1 == 0){
            if(boardChangedListener != null){
                boardChangedListener.onGameFinished(JOGADOR_DOIS, turnoAtual);
            }
            
            return true;
        }
        
        if (nPecasJogador2 == 0){
            if(boardChangedListener != null){
                boardChangedListener.onGameFinished(JOGADOR_UM, turnoAtual);
            }
            
            return true;
        }
        
        return false;
    }

    /**
     * Verifica todas as peças que estão aptas a se moverem no turno. Peças que
     * podem capturar são prioredade.
     *
     * @return Lista de peças válidas para o turno.
     */
    protected List<Peca> getPecasCaptura() {
        List<Peca> pecasAptasCaptura = new ArrayList<>();
        List<Peca> pecasJogadaNormal = new ArrayList<>();

        //Pega todas as peças que estão no tabuleiro.
        List<Peca> pecasTabuleiro = tabuleiro.getPecas();

        for (Peca peca : pecasTabuleiro) {
            List<Jogada> jogadas = jogadasPossiveis(peca);
            if (possuiCaptura(jogadas)) {
                pecasAptasCaptura.add(peca);
            } else if (!jogadas.isEmpty()) {
                pecasJogadaNormal.add(peca);
            }
        }

        return pecasAptasCaptura.isEmpty() ? pecasJogadaNormal : pecasAptasCaptura;
    }

    /**
     * Verifica se uma lista de jogadas possui uma jogada com captura.
     *
     * @param jogadas lista de jogadas a ser verificada.
     * @return tre caso exista jogada de captura, false caso contrário.
     */
    protected boolean possuiCaptura(List<Jogada> jogadas) {
        for (Jogada jogada : jogadas) {
            if (jogada.houveCaptura()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Função que trata o incremento de turno. Verifica possível numero máximo
     * de turnos e etc.
     */
    private void incrementaTurno() {
        turnoAtual++;
    }

    /**
     * Transforma uma peça em dama.
     *
     * @param peca peça que vai se transformar em dama
     */
    private void viraDama(Peca peca) {
        peca.setDama(true);
    }

    /**
     * Retorna o turno atual do jogo.
     *
     * @return
     */
    protected int getTurno() {
        return turnoAtual;
    }

    /**
     * Remove peça do tabuleiro.
     *
     * @param peca peça a ser removida.
     */
    private void removerPeca(Peca peca) {
        if (peca == null) {
            return;
        }

        if (boardChangedListener != null) {
            boardChangedListener.onPieceRemoved(tabuleiro.getPosicao(peca));
        }

        tabuleiro.removePeca(peca);

        if (peca.getTime() == JOGADOR_UM) {
            nPecasJogador1--;
        } else {
            nPecasJogador2--;
        }
    }

    /**
     * retorna uma peça dada a posição.
     *
     * @param pos
     * @return retorna uma peça caso exista nessa posição, null caso contrário.
     */
    protected Peca getPeca(Posicao pos) {
        return tabuleiro.getPeca(pos);
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public int getJogadorAtual() {
        return jogadorAtual;
    }

    /**
     * Histórico de jogadas da partida.
     *
     * @return
     */
    protected List<Jogada> getHistorico() {
        List<Jogada> jogadas = new ArrayList<>();
        jogadas.addAll(historicoJogador1);
        jogadas.addAll(historicoJogador2);
        return jogadas;
    }

    /**
     * Historico de jogadas da partida do jogador1.
     *
     * @return
     */
    protected List<Jogada> getHistJogador1() {
        return historicoJogador1;
    }

    /**
     * Historico de jogadas da partida do jogador2.
     *
     * @return
     */
    protected List<Jogada> getHistJogador2() {
        return historicoJogador2;
    }

    /**
     * Interface de callback.
     */
    public interface BoardChangedListener {

        /**
         * Sempre que uma peça realizar um movimento essa função vai ser
         * executada.
         *
         * @param posInicial posição do inicio do movimento.
         * @param posFinal posição de término do movimento.
         */
        public void onPieceMoved(Posicao posInicial, Posicao posFinal);

        /**
         * Sempre que o jogo terminar essa função será chamada.
         *
         * @param vencedor Jogador que ganhou.
         * @param causa Causa do término do jogo.
         */
        public void onGameFinished(int vencedor, int causa);

        /**
         * Sempre que uma peça for removida essa função será chamada.
         *
         * @param posicao posição na qual a peça foi removida.
         */
        public void onPieceRemoved(Posicao posicao);

        /**
         * Sempre que uma peça virar dama essa função será chamada.
         *
         * @param i posição i da peça na matriz.
         * @param j posição j da peça na matriz.
         */
        public void onKing(int i, int j, int time);
    }

    public void setOnBoardChangedListener(BoardChangedListener boardChangedListener) {
        this.boardChangedListener = boardChangedListener;
    }

    private class FimDeJogo {

        public static final int TERMINO_DE_PECAS = 0;
        public static final int MAXIMO_DE_TURNOS = 1;
        public static final int TRAVADO = 2;
    }

    public int getnPecasJogador1() {
        return nPecasJogador1;
    }

    public int getnPecasJogador2() {
        return nPecasJogador2;
    }

    public boolean isJogoFinalizado() {
        return jogoFinalizado;
    }

    protected String getStringTabuleiro() {
        return tabuleiro.toString();
    }

    public void setJogadorAtual(int jogadorAtual) {
        /*if(jogadorAtual != JOGADOR_UM || jogadorAtual != JOGADOR_DOIS){
            throw new IllegalArgumentException("Numero do time invalido.");
        }*/

        this.jogadorAtual = jogadorAtual;
    }

    public Regras copia() {
        return new Regras(tabuleiro.copia(), turnoAtual, jogadorAtual, nPecasJogador1, nPecasJogador2);
    }

    /**
     * Retorna todas as peças do jogador atual que possuem captura dentro das
     * jogadas válidas.
     *
     * @return
     */
    public List<Peca> getPecasAptasDoJogadorAtual() {
        List<Peca> pecas = getPecasCaptura();
        List<Peca> returnList = new ArrayList<>();
        for (Peca peca : pecas) {
            if (peca.getTime() == jogadorAtual) {
                returnList.add(peca);
            }
        }
        return returnList;
    }

    public List<Peca> getPecasAptasParaCapturaDoJogadorAtual() {
        List<Peca> pecasAptasCaptura = new ArrayList<>();

        //Pega todas as peças que estão no tabuleiro.
        List<Peca> pecasTabuleiro = tabuleiro.getPecas();

        for (Peca peca : pecasTabuleiro) {
            List<Jogada> jogadas = jogadasPossiveis(peca);
            if (possuiCaptura(jogadas) && peca.getTime() == jogadorAtual) {
                pecasAptasCaptura.add(peca);
            }
        }

        return pecasAptasCaptura;
    }

    private void trocaJogadorAtual() {
        if (jogadorAtual == JOGADOR_UM) {
            jogadorAtual = JOGADOR_DOIS;
        } else {
            jogadorAtual = JOGADOR_UM;
        }
    }
}
