/*
 * Topologic_sort->O(V+E)
 * DFS->O(V+E)
 * Tarjan->O(V+E)
 */

#include <iostream>
#include <search.h>
#include<stack>
#include "Profiler.h"

using namespace std;

typedef struct nodeLink
{
    int data;
    nodeLink * next;
}link;

link *first, *last;
int timp;
int index1=0;
bool onStack[100];
enum Color {
	COLOR_WHITE,
	COLOR_GRAY,
	COLOR_BLACK
};
typedef struct node{
    int timpul_d;
    int timpul_f;
    int adjSize;
    int key;
    int id;
    int lowlink;
    struct node **adj;
    struct node * parent;
    Color color;

}node;

 struct graph{
     int nrNodes;
     struct node **v;
 };

//-------------------------DFS------------------------------------------
 void DFS_Visit(graph * g,node * u,Operation * op){
     if (op != NULL)
       op->count(3);
     timp=timp+1;
   u->timpul_d=timp;
   u->color=COLOR_GRAY;
      for (int j = 0; j < u->adjSize; j++) {
          if (op != NULL)
              op->count();
          if (u->adj[j]->color == COLOR_WHITE) {
              if (op != NULL)
                  op->count();
              u->adj[j]->parent = u;
              DFS_Visit(g, u->adj[j],op);
          }
  }
     if (op != NULL)
        op->count(3);
     u->color = COLOR_BLACK;
     timp = timp + 1;
     u->timpul_f = timp;
    // insert_first(&first,&last,u->key);->Se face sortare


 }

void dfs(graph *g,Operation * op){
    for(int i=0;i<g->nrNodes;i++){
        if (op != NULL)
          op->count(2);
        g->v[i]->color=COLOR_WHITE;
        g->v[i]->parent=NULL;
    }
    timp=0;

    for(int i=0;i<g->nrNodes;i++){
        if (op != NULL)
            op->count();
        if(g->v[i]->color==COLOR_WHITE){
            DFS_Visit(g,g->v[i],op);
        }
    }
}


//-------------------------Topologic-Sort------------------------------------------
void print_link(link *first)
{
    printf("Sortarea Topologica: ");
    link *p;
    if (first == 0)
        printf("eroare");
    else
    {
        p = first;

        while (p)
        {
            printf(" %d ",p->data);

            p = p->next;
        }

    }
}
void insert_first(link **first, link **last, int p)
{
    link *l;
    l = (link*)malloc(sizeof(link));
    l->data = p;
    l->next = 0;

    if (*first == 0)
    {
        *first= l;
        *last = l;
        (*last)->next= 0;
    }
    else
    {
        l->next= *first;
        *first = l;
    }
}

int partition(node** array, int start, int end) {
    int x = array[end]->timpul_f;
    int i = start - 1;
    for (int j = start; j <=  end - 1; j++) {
        if (array[j]->timpul_f <= x) {
            i++;
            swap(array[i], array[j]);
        }
    }
    swap(array[i + 1], array[end]);
    return i + 1;
}
void quicksort(node**array, int start, int end) {
    if (start < end) {
        int  q = partition(array, start, end);
        quicksort(array, start, q - 1);
        quicksort(array, q + 1, end);
    }
}
nodeLink* Topological_sort(graph * g){
    dfs(g,NULL);
    quicksort(g->v,0,g->nrNodes-1);
    for(int i=0;i<g->nrNodes;i++){
    insert_first(&first, &last, g->v[i]->key);}
    print_link(first);
}



//-------------------------Tarjan------------------------------------------

stack<node*> stiva;
void Tarjan(node *u) {
    u->id = index1;
    u->lowlink = index1;
    index1++;
    stiva.push(u);
    u->color = COLOR_GRAY;
    onStack[u->key] = true;
    for (int j = 0; j < u->adjSize; j++) {
        if (u->adj[j]->color == COLOR_WHITE) {
            Tarjan(u->adj[j]);
            u->lowlink = min(u->lowlink, u->adj[j]->lowlink);
        } else {
            if (onStack[u->adj[j]->key]) {
                u->lowlink = min( u->lowlink,u->adj[j]->id);
            }
        }

    }
    u->color = COLOR_BLACK;
    if (u->lowlink == u->id) {
        node *v;
        do {
            v = stiva.top();
            onStack[v->key] = false;
            stiva.pop();
            printf("%d ", v->key);

        } while (u->key != v->key);
        printf("\n");
    }
}



void dfsTarjan(graph *g){
    index1=0;

    for(int i=0;i<g->nrNodes;i++){

        g->v[i]->color=COLOR_WHITE;
        g->v[i]->id=0;
        g->v[i]->lowlink=0;
        onStack[g->v[i]->key] =false;

    }
    for(int i=0;i<g->nrNodes;i++){

        if(g->v[i]->color==COLOR_WHITE){
            Tarjan(g->v[i]);

        }

    }

}


void demo() {
    srand(time(NULL));
    int n, i;
    int V;
    printf("Introduceti nr de varfuri:");
    scanf("%d",&V);
        graph graph;
        graph.nrNodes = V;
    printf("Introduceti nr de muchii:");
    scanf("%d",&n);
    printf("Muchiile sunt:\n");
        graph.v = (node**)malloc(graph.nrNodes * sizeof(node*));
        for (i = 0; i < graph.nrNodes; ++i) {
            graph.v[i] = (node*)malloc(sizeof(node));
            memset(graph.v[i], 0, sizeof(node));
            graph.v[i]->adj = (node**)malloc(4501 * sizeof(node*));
            graph.v[i]->adjSize = 0;
            graph.v[i]->key=i;

        }


        for (int j = 0; j < n; ++j) {
            bool yes;
            int indexA ,indexB;
            do {
                yes = true;
                indexA= rand() % graph.nrNodes;
               indexB = rand() % graph.nrNodes;
                for (int i = 0; i < graph.v[indexA]->adjSize; i++) {
                    if (graph.v[indexA]->adj[i] == graph.v[indexB]) {
                        yes = false;
                        break;
                    }
                }

            } while (!yes);


            graph.v[indexA]->adj[graph.v[indexA]->adjSize++] = graph.v[indexB];
            printf("(%d,%d)\n",indexA,indexB);


        }

        dfs(&graph,NULL);
        for(int i=0;i<graph.nrNodes;i++) {
            printf("Pentru nodul %d timpul  de descoperire este %d, timpul de finalizare este %d\n", graph.v[i]->key, graph.v[i]->timpul_d, graph.v[i]->timpul_f);
        }
        Topological_sort(&graph);
        printf("\nComponentele puternic conexe sunt:\n");
        dfsTarjan(&graph);
    }



void performance()
{
    srand(time(NULL));
    int n, i;
    Profiler p("dfs");
    for (n = 1000; n <= 4500; n += 100) {
        Operation op = p.createOperation("dfs-edges", n);
        graph graph;
        graph.nrNodes = 100;


        graph.v = (node**)malloc(graph.nrNodes * sizeof(node*));
        for (i = 0; i < graph.nrNodes; ++i) {
            graph.v[i] = (node*)malloc(sizeof(node));
            memset(graph.v[i], 0, sizeof(node));
            graph.v[i]->adjSize = 0;
            graph.v[i]->adj = (node**)malloc(4501 * sizeof(node*));

        }
        for (int i = 0; i < n; ++i) {
            bool yes;
            int indexA ,indexB;
            do {
                yes = true;
                indexA= rand() % graph.nrNodes;
                indexB = rand() % graph.nrNodes;
                for (int k = 0; k < graph.v[indexA]->adjSize; k++) {
                    if (graph.v[indexA]->adj[k] == graph.v[indexB]) {
                        yes = false;
                        break;
                    }
                }

            } while (!yes);

            graph.v[indexA]->adj[graph.v[indexA]->adjSize++] = graph.v[indexB];

        }
    dfs(&graph,&op);
    }

    for (n = 100; n <= 200; n += 10) {

        Operation op = p.createOperation("dfs-vertices", n);
        graph graph;
        graph.nrNodes = n;

        graph.v = (node**)malloc(graph.nrNodes * sizeof(node*));
        for (i = 0; i < graph.nrNodes; ++i) {
            graph.v[i] = (node*)malloc(sizeof(node));
            memset(graph.v[i], 0, sizeof(node));
            graph.v[i]->adjSize = 0;
            graph.v[i]->adj = (node**)malloc(4501 * sizeof(node*));

        }

        for (int i = 0; i < 4500; ++i) {
            bool yes;
            int indexA ,indexB;
            do {
                yes = true;
                indexA= rand() % graph.nrNodes;
                indexB = rand() % graph.nrNodes;
                for (int k = 0; k < graph.v[indexA]->adjSize; k++) {
                    if (graph.v[indexA]->adj[k] == graph.v[indexB]) {
                        yes = false;
                        break;
                    }
                }

            } while (!yes);

            graph.v[indexA]->adj[graph.v[indexA]->adjSize++] = graph.v[indexB];

        }
       dfs(&graph,&op);
    }
    p.showReport();
}

int main(){
//performance();
demo();
    return 0;
};
