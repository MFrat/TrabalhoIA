#include "table.c"

typedef struct DataStructTable STable,*Table;
Table createTable(void);
void initializeTable(Table);
Player positionTable(Table, Position);
void printTable(Table);
int countEmpty(Table);
void copyTable(Table from, Table destiny);
int play(Table, Position, Player);
Player tableValue(Table);
int isEnd(Table);
void excludeTable(Table);
