#include "table.h"

Position* listPlays(Table tab){
	int size = countEmpty(tab);
	Position* resp = (Position*)malloc((size+1)*sizeof(Position));
	int n=0;
	resp[n]=NULL;
	int i,j;
	Position pos;
	for(i=0;i<3;i++){
		for(j=0;j<3;j++){
			pos = createPosition();
			positionSetX(pos,i);
			positionSetY(pos,j);
			if(positionTable(tab, pos) == emptyPlayer()){
				resp[n++]=pos;
				resp[n]=NULL;
			}
			else excludePosition(pos);
		}
	}
	return resp;
}

void printPlayList(Position* vet){
	int i=0;
	while(vet[i]){
		printPosition(vet[i]);
		printf("\t");
		i++;
	}
	printf("\n");
	return;
}

int countPlayList(Position* vet){
	int n=0,i=0;
	while(vet[i++])n++;
	return n;
}

void excludePlayList(Position* vet){
	int i=0;
	while(vet[i])excludePosition(vet[i++]);
	free(vet);
	return;
}
