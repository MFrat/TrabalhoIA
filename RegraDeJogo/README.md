## Máquina de regras de um jogo de damas.

<p align="center">
  <img src="http://i.imgur.com/cPAzuyS.png" width="1000"/>
</p>

### Classe Regras
Controla toda a lógica do jogo.

### Classe Tabuleiro
Efetivamente movimenta as peças do tabuleiro e controla o acesso à ele.

### Classe Jogada
Encapsula todas as informações de uma jogada. Peças capturadas, posição final e inicial.

### Classe Jogador
Classe abstrata que implementa alguns métodos comuns aos jogadores, além de server como facade para o acesso a classe Regras.

### Classe Humano
Representa um jogador humano.

### Classe Bot
Representar um jogodor robô. Implementa o algorítmo MinMax para realizar a jogada.

## Callbacks

### Regras

```java
regras.setOnBoardChangedListener(new Regras.BoardChangedListener() {
            @Override
            public void onPieceMoved(Posicao posInicial, Posicao posFinal) {
               
            }

            @Override
            public void onGameFinished(int vencedor, int causa) {
            
            }

            @Override
            public void onPieceRemoved(Posicao posicao) {
                
            }

            @Override
            public void onKing(int i, int j, int time) {
                
            }
        });
```

### Jogador

```java
jogador.setJogadorListener(new Jogador.JogadorListener() {
            @Override
            public void jogadaFinalizada() {
                
            }
        });
```

