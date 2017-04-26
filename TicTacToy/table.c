#include "player.h"
#include "position.h"

typedef struct DataStructTable{
	Player tab[3][3];
}STable,*Table;

Table createTable(void){
	return (Table)malloc(sizeof(STable));
}

void initializeTable(Table tab){
	int i,j;
	for(i=0;i<3;i++)for(j=0;j<3;j++)tab->tab[i][j]=emptyPlayer();
}

Player positionTable(Table tab, Position pos){
	return tab->tab[positionShowX(pos)][positionShowY(pos)];
}

void printTable(Table tab){
	int i,j;
	printf("\n");
	printf(" \t \t1\t2\t3\n");
	for(i=0;i<3;i++){
		printf("%d\t[\t",i+1);
		for(j=0;j<3;j++){
			printPlayer(tab->tab[i][j]);
			printf("\t");
		}
		printf("]\n");
	}
	printf("\n");
}

int countEmpty(Table tab){
	int i, j, resp=0;
	for(i=0; i<3; i++)for(j=0; j<3; j++)
			if(tab->tab[i][j] == emptyPlayer())resp++;
	return resp;
}

void copyTable(Table from, Table destiny){
	int i,j;
	for(i=0;i<3;i++)for(j=0;j<3;j++)destiny->tab[i][j]=from->tab[i][j];
}

int play(Table tab, Position pos, Player player){
	if(!tab->tab[positionShowX(pos)][positionShowY(pos)] == emptyPlayer())return 0;
	tab->tab[positionShowX(pos)][positionShowY(pos)] = player;
	return 1;
}

Player tableValue(Table tab){
	int i;
	for(i=0;i<3;i++)
		if(tab->tab[i][0] == tab->tab[i][1] && tab->tab[i][1] == tab->tab[i][2])
									return tab->tab[i][0];
	for(i=0;i<3;i++)
		if(tab->tab[0][i] == tab->tab[1][i] && tab->tab[1][i] == tab->tab[2][i])
									return tab->tab[0][i];
	if(tab->tab[0][0] == tab->tab[1][1] && tab->tab[1][1] == tab->tab[2][2])
									return tab->tab[1][1];
	if(tab->tab[0][2] == tab->tab[1][1] && tab->tab[1][1] == tab->tab[2][0])
									return tab->tab[1][1];
	return emptyPlayer();
}

int isEnd(Table tab){
	int i,j;
	if(tableValue(tab) != emptyPlayer())return 1;
	for(i=0;i<3;i++)for(j=0;j<3;j++)if(tab->tab[i][j] == emptyPlayer()) return 0;
	return 1;
}

void excludeTable(Table tab){
	free(tab);
}
