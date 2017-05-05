#include <stdlib.h>

typedef struct DataStructPosition{
	int pos[2];
}SPosition,*Position;

Position createPosition(void){
	return (Position)malloc(sizeof(SPosition));
}

void positionSetX(Position pos, int x){
	pos->pos[0]=x;
}

void positionSetY(Position pos, int y){
	pos->pos[1]=y;
}

int positionShowX(Position pos){
	return pos->pos[0];
}

int positionShowY(Position pos){
	return pos->pos[1];
}

void printPosition(Position pos){
	printf("( %d, %d )", pos->pos[0], pos->pos[1]);
}

void copyPosition(Position from, Position destiny){
	destiny->pos[0]=from->pos[0];
	destiny->pos[1]=from->pos[1];
}

void excludePosition(Position pos){
	free(pos);
}
