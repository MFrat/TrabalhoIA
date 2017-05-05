#include "play_list.h"

void humanPlay(Table tab, Player player){
	int aux;	
	Position pos = createPosition();
	do{
		do{
			printf("Choose a line: ");
			scanf("%d",&aux);
		}while(aux<1 || aux>3);
		positionSetX(pos, aux-1);
		do{
			printf("Choose a columm: ");
			scanf("%d",&aux);
		}while(aux<1 || aux>3);
		positionSetY(pos, aux-1);
	}while(!play(tab, pos, player));
	excludePosition(pos);
}

static Position minMax(Table, Player, Player);
static int max(Table, Player, Player);
static int maxValue(int*, int);
static int min(Table, Player, Player);
static int minValue(int*, int);
static Position choosePosition(Position*, int*);

void bot1Play(Table tab, Player player){
	Position pos = minMax(tab, player, player);
	play(tab, pos, player);
	excludePosition(pos);
}

void bot2Play(Table tab, Player player){
	return;
}

static Position minMax(Table tab, Player player, Player bot){
	Position *validPlays = listPlays(tab);
	int i=0, n=countPlayList(validPlays);
	int *playValues = (int*)malloc(n*sizeof(int));
	Position pos=validPlays[i];
	Table auxTab = createTable();
	while(pos){
		copyTable(tab, auxTab);
		play(auxTab, pos, player);
		playValues[i] = min(auxTab, nextPlayer(player), bot);
		pos=validPlays[++i];
	}
	excludeTable(auxTab);	
	pos = choosePosition(validPlays, playValues);
	excludePlayList(validPlays);
	free(playValues);
	return pos;
}

static int min(Table tab, Player player, Player bot){
	if(isEnd(tab)){
		Player winner = tableValue(tab);
		if(winner == bot)return 1;
		if(winner == emptyPlayer())return 0;
		return -1;
	}
	Position *validPlays = listPlays(tab);
	int i=0, n=countPlayList(validPlays);
	int *playValues = (int*)malloc(n*sizeof(int));
	Position pos=validPlays[i];
	Table auxTab = createTable();
	while(pos){
		copyTable(tab, auxTab);
		play(auxTab, pos, player);
		playValues[i] = max(auxTab, nextPlayer(player), bot);
		pos=validPlays[++i];
	}
	excludeTable(auxTab);
	int auxMinValue = minValue(playValues, n);
	free(playValues);
	return auxMinValue;
}

static int max(Table tab, Player player, Player bot){
	if(isEnd(tab)){
		Player winner = tableValue(tab);
		if(winner == bot)return 1;
		if(winner == emptyPlayer())return 0;
		return -1;
	}
	Position *validPlays = listPlays(tab);
	int i=0, n=countPlayList(validPlays);
	int *playValues = (int*)malloc(n*sizeof(int));
	Position pos=validPlays[i];
	Table auxTab = createTable();
	while(pos){
		copyTable(tab, auxTab);
		play(auxTab, pos, player);
		playValues[i] = min(auxTab, nextPlayer(player), bot);
		pos=validPlays[++i];
	}
	excludeTable(auxTab);
	int auxMaxValue = maxValue(playValues, n);
	free(playValues);
	return auxMaxValue;
}

static int minValue(int *vet, int n){
	int i, min=vet[0];
	for(i=1;i<n;i++)if(vet[i]<min)min=vet[i];
	return min;
}

static int maxValue(int *vet, int n){
	int i, max=vet[0];
	for(i=1;i<n;i++)if(vet[i]>max)max=vet[i];
	return max;
}

static Position choosePosition(Position *plays, int *values){
	Position pos = createPosition();
	int auxMaxValue = maxValue(values, countPlayList(plays));
	int i=0;
	while(plays[i]){
		if(values[i]==auxMaxValue){
			copyPosition(plays[i],pos);
			break;
		}
		i++;
	}
	return pos;
}
