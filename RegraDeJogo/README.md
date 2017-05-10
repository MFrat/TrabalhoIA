## Máquina de regras de um jogo de damas.

<p align="center">
  <img src="http://i.imgur.com/cPAzuyS.png" width="1000"/>
</p>

## Classe Regras
Controla toda a lógica do jogo.

## Classe Tabuleiro
Efetivamente movimenta as peças do tabuleiro e controla o acesso à ele.

## Classe Jogada
Encapsula todas as informações de uma jogada. Peças capturadas, posição final e inicial.

## Classe Jogador
Classe abstrata que implementa alguns métodos comuns aos jogadores, além de server como facade para o acesso a classe Regras.

## Classe Humano
Representa um jogador humano.

## Classe Bot
Representar um jogo robô. Implementa o algorítmo MinMax para realizar a jogada.
