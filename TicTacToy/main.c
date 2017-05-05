#include "play.h"

int main(void){
	Table tab = createTable();
	initializeTable(tab);
	char bot;
	do{
		printf("Play with bot? <s|n>");
		scanf(" %c", &bot);
	}while(bot != 's' && bot != 'n');
	Player human = firstPlayer();
	Player player = human;
	while(1){
		printTable(tab);
		if(bot == 's' && player != human)bot1Play(tab, player);
		else humanPlay(tab, player);
		if(isEnd(tab))break;
		player = nextPlayer(player);
	}
	Player winner=tableValue(tab);
	excludeTable(tab);
	if(winner == emptyPlayer()){
		printf("It's a draw\n");
		return 1;
	}
	char *auxWinner = ((winner == firstPlayer()) ? "one" : "two");
	printf("Player %s win the game\n",auxWinner);
	return 1;
}
