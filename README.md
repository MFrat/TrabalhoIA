# RegraDamas

```java

Regras regras = new Regras();
        
regras.setOnBoardChangedListener(new Regras.BoardChangedListener() {
    @Override
    public void onPieceMoved(Posicao posInicial, Posicao posFinal) {

    }

    @Override
    public void onGameFinished(int vencedor, int causa) {
        System.out.println("Jogo acabou!");
    }

    @Override
    public void onPieceRemoved(Posicao posicao) {

    }

    @Override
    public void onKing(int i, int j, int time) {

    }
});

```
