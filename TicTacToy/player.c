#include <stdio.h>

typedef enum enumPlayer{
	E,
	X,
	O
}Player;

void printPlayer(Player player){
	switch(player){
		case E:
			printf(" ");
			break;
		case X:
			printf("X");
			break;
		case O:
			printf("O");
			break;
	}
}

Player emptyPlayer(void){
	return E;
}

Player firstPlayer(void){
	return X;
}

Player nextPlayer(Player player){
	return player == X ? O : X;
}
