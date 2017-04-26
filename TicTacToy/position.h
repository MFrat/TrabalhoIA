#include "position.c"

typedef struct DataStructPosition SPosition,*Position;
Position createPosition(void);
void positionSetX(Position, int);
void positionSetY(Position, int);
int positionShowX(Position);
int positionShowY(Position);
void printPosition(Position);
void copyPosition(Position from, Position destiny);
void excludePosition(Position);
