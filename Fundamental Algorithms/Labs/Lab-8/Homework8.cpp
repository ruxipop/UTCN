/** Complexitate
 * Make_Set->O(1)
 *Union-Set->O(log n)-- datorita uniri prin rank(parcurgand astfel inaltimea )
 * Find-Set->O(log n)--datorita path compressions(merge in sus pana gaseste radacina)
 * Cele 2 tehnici se completeaza reciproc
 */

#include <iostream>
#include "Profiler.h"
using namespace std;
Profiler profiler("Disjoint Sets");

int j;
struct Node
{   int key;
    int rank;//height
    Node *parent;

};
//structura pentru a reprezenta o muchie ponderata in graf
struct Edge{
    Node* src;
   Node *dest;
    int weight;

};
//structura  pentru a reprezenta un graf neorientat;
 struct Graph {
    int V;
    int E;
    struct Edge* edges;
};

 //costruim un graf cu V varfuri si E muchii
Graph* createGraph(int V, int E)
{
    Graph* graph = new Graph;
    graph->V = V;
    graph->E = E;
    graph->edges = new Edge[E];
    return graph;
}


Node *make_set(int v,int n,const char*m_Make){
    if(m_Make!=NULL) {
        profiler.countOperation(m_Make, n, 4);
    }
   Node *x=(Node*)malloc(sizeof(Node));
    x->key=v;
    x->parent=x;
    x->rank=0;
    return x;
}

void link_set(Node *x,Node *y,int n,const char* m_Union){
    if(m_Union!=NULL){
        profiler.countOperation(m_Union,n);}
  if(x->rank>y->rank){

      if(m_Union!=NULL){
          profiler.countOperation(m_Union,n);}
     y->parent=x;
  }

  else
      {

          if(m_Union!=NULL){
              profiler.countOperation(m_Union,n,2);}
      x->parent=y;
      if(x->rank==y->rank){

          if(m_Union!=NULL){
              profiler.countOperation(m_Union,n);}
          y->rank=y->rank+1;}
  }

}


Node * find_set(Node *x,int n,const char* m_Find){

    if(m_Find!=NULL){
        profiler.countOperation(m_Find,n);}
    if (x!=x->parent){
        if(m_Find!=NULL){
            profiler.countOperation(m_Find,n);}
        x->parent=find_set(x->parent,n,m_Find);
    }
    return x->parent;

}

void union_set(Node *x,Node *y,int n,const char* m_Union){
     link_set(find_set(x,n,m_Union),find_set(y,n,m_Union),n,m_Union);



}
int partition(Edge * array, int start, int end) {
    int x = array[end].weight;
    int i = start - 1;
    for (int j = start; j <=  end - 1; j++) {
        if (array[j].weight <= x) {
            i++;
            swap(array[i], array[j]);
        }
    }
    swap(array[i + 1], array[end]);
    return i + 1;
}
void quicksort(Edge *array, int start, int end) {
    if (start < end) {
       int  q = partition(array, start, end);
        quicksort(array, start, q - 1);
        quicksort(array, q + 1, end);
    }
}
bool comparare(Node *u, Node *v,int n,const char *m_Find)
{
    if(find_set(u,n,m_Find) != find_set(v,n,m_Find))
        return true;
    else return false;
}


Edge*  MST_Kruskal(Graph* graph,int n,const char* m_Union,const char* m_Find){
    int E=graph->E;
    int V=graph->V;
    j=0;
  /* for(int v=0;v<V;v++)
       make_set(v,n)*/
    Edge*  A = (Edge*)malloc((E) * sizeof(Edge));
    quicksort(graph->edges, 0, graph->E - 1);
    for (int i = 0; i <E && j<(V - 1); i++) {
        Edge tempEdge = graph->edges[i];

        if (comparare(tempEdge.src,tempEdge.dest,n,m_Find)) {

           A[j++] = tempEdge;
            union_set(tempEdge.src, tempEdge.dest,n,m_Union);
        }
    }

return A;
}

void demoOperatii() {
    
    srand(time(NULL));
    int *array = new int[10]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    Node **nodesArray=(Node**)malloc(10 * sizeof(Node));
    cout << "BEFORE\n";

    for (int i = 0; i < 10; i++) {
        nodesArray[i] = make_set(array[i],10,NULL);
        cout << nodesArray[i]->key << " ->  " << find_set(nodesArray[i],10,NULL)->key << "\n";
    }
    cout << "\nAFTER\n";
    for(int i=0;i<5;i++){

        int randomElement=rand()%10;

        union_set(nodesArray[i], nodesArray[randomElement],10,NULL);
        cout << nodesArray[i]->key << " union cu " << nodesArray[randomElement]->key << "\n";
    }
    for(int i=0;i<5;i++){
        int randomElement=rand()%10;
        cout << nodesArray[randomElement]->key << " face parte din setul cu parintele " << find_set(nodesArray[randomElement],10,NULL)->key<< "\n";
    }
}
void masuratori(const char*  m_Make,const char*  m_Union,   const char* m_Find){
        int edgesWeights[40001];
        for (int n = 100; n <= 10000; n=n+100) {
            FillRandomArray(edgesWeights, (4*n), 0, 100, false, 0);
             int lungime=n;
              Node** vertices = (Node**)malloc((n) * sizeof(Node*));
             Graph* graph = createGraph(n, (n * 4));
        for (int i = 0; i <n; i++)
             vertices[i] = make_set(i,lungime,m_Make);
        for (int i = 0; i <n-1; i++) {
            graph->edges[i].src = vertices[i];
            graph->edges[i].dest = vertices[i+1];
            graph->edges[i].weight = edgesWeights[i];
        }
        for (int i = n-1; i <4 * n; i++) {
            int indexA = rand() % n;
            int indexB = rand() % n;

            while (indexA == indexB) {
               indexA = rand() % n;
               indexB = rand() % n;
            }
            graph->edges[i].src = vertices[indexA];
            graph->edges[i].dest = vertices[indexB];
            graph->edges[i].weight = edgesWeights[i];
        }
        Edge*m= MST_Kruskal(graph,lungime,m_Union,m_Find);
        }

}
void measurements(){
    const char*  m_Make= "MakeSet";
    const char* m_Union = "UnionSet";
    const char* m_Find = "FindSet";
    masuratori( m_Make,m_Union,m_Find);
    profiler.createGroup("Disjoint Sets Operations", m_Make,m_Union,m_Find);
    profiler.createGroup("Make",m_Make);
    profiler.createGroup("Union",m_Union);
    profiler.createGroup("Find",m_Find);
    profiler.showReport();
}
void demoKruskal(){
    int V,E;
    cout<<"Intrudu V=";
    cin>>V;
    cout<<"Intrudu E=";
    cin>>E;
    cout<<"\n";
    Node** vertices = (Node**)malloc((V) * sizeof(Node*));
    Graph* graph = createGraph(V, (E));
    for (int i = 0; i <V; i++)
        vertices[i] = make_set(i,V,NULL);
    for (int i = 0; i <V-1; i++) {
        int weight = rand() % 100 + 1;
        graph->edges[i].src = vertices[i];
        graph->edges[i].dest = vertices[i+1];
        graph->edges[i].weight = weight;
    }
    for (int i = V-1; i <E; i++) {
        int indexA = rand() % V;
        int indexB = rand() % V;

        while (indexA == indexB) {
            indexA = rand() % V;
            indexB = rand() % V;
        }
        int weight = rand() % 100 + 1;
        graph->edges[i].src = vertices[indexA];
        graph->edges[i].dest = vertices[indexB];
        graph->edges[i].weight = weight;
    }
    Edge*m= MST_Kruskal(graph,V,NULL,NULL);
    cout<<"Before:"<<"\n";
   for (int i = 0; i < E; i++)
    cout <<"("<< graph->edges[i].src->key << " , " << graph->edges[i].dest->key<<")" << "->weight: " << graph->edges[i].weight<< "\n";
   cout<<"After:"<<"\n";
    for (int i = 0; i < j; i++)
        cout << "("<<m[i].src->key << " ," << m[i].dest->key << ")"<<"->weight: " << m[i].weight << "\n";
}



int main() {
   //demoOperatii();
   //measurements();
 demoKruskal();
    return 0;
}
