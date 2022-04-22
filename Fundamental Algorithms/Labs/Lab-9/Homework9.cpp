#include <stdlib.h>
#include <string.h>
#include "bfs.h"


int get_neighbors(const Grid* grid, Point p, Point neighb[])
{
   
    int directie[][4] = { 0, 0, -1, 1 ,
                          1,-1,0,0 };
    int nrVecini = 0;
    if (grid->mat[p.row][p.col] == 0) {
        for (int i = 0; i < 4; i++)
        {
            int pozCol = p.col + directie[0][i];
            int pozRand = p.row + directie[1][i];
           
            if (pozRand >= 0 && pozCol >= 0 && pozRand <= grid->rows && pozCol <= grid->cols){
                    if (grid->mat[pozRand][pozCol] == 0){
                    neighb[nrVecini].row = pozRand;
                    neighb[nrVecini++].col = pozCol;

                }
            }
        }
    }
    return nrVecini;
}

void grid_to_graph(const Grid* grid, Graph* graph)
{
    //we need to keep the nodes in a matrix, so we can easily refer to a position in the grid
    Node* nodes[MAX_ROWS][MAX_COLS];
    int i, j, k;
    Point neighb[4];

    //compute how many nodes we have and allocate each node
    graph->nrNodes = 0;
    for (i = 0; i < grid->rows; ++i) {
        for (j = 0; j < grid->cols; ++j) {
            if (grid->mat[i][j] == 0) {
                nodes[i][j] = (Node*)malloc(sizeof(Node));
                memset(nodes[i][j], 0, sizeof(Node)); //initialize all fields with 0/NULL
                nodes[i][j]->position.row = i;
                nodes[i][j]->position.col = j;
                ++graph->nrNodes;
            }
            else {
                nodes[i][j] = NULL;
            }
        }
    }
    graph->v = (Node**)malloc(graph->nrNodes * sizeof(Node*));
    k = 0;
    for (i = 0; i < grid->rows; ++i) {
        for (j = 0; j < grid->cols; ++j) {
            if (nodes[i][j] != NULL) {
                graph->v[k++] = nodes[i][j];
            }
        }
    }

    //compute the adjacency list for each node
    for (i = 0; i < graph->nrNodes; ++i) {
        graph->v[i]->adjSize = get_neighbors(grid, graph->v[i]->position, neighb);
        if (graph->v[i]->adjSize != 0) {
            graph->v[i]->adj = (Node**)malloc(graph->v[i]->adjSize * sizeof(Node*));
            k = 0;
            for (j = 0; j < graph->v[i]->adjSize; ++j) {
                if (neighb[j].row >= 0 && neighb[j].row < grid->rows &&
                    neighb[j].col >= 0 && neighb[j].col < grid->cols &&
                    grid->mat[neighb[j].row][neighb[j].col] == 0) {
                    graph->v[i]->adj[k++] = nodes[neighb[j].row][neighb[j].col];
                }
            }
            if (k < graph->v[i]->adjSize) {
                //get_neighbors returned some invalid neighbors
                graph->v[i]->adjSize = k;
                graph->v[i]->adj = (Node**)realloc(graph->v[i]->adj, k * sizeof(Node*));
            }
        }
    }
}

void free_graph(Graph* graph)
{
    if (graph->v != NULL) {
        for (int i = 0; i < graph->nrNodes; ++i) {
            if (graph->v[i] != NULL) {
                if (graph->v[i]->adj != NULL) {
                    free(graph->v[i]->adj);
                    graph->v[i]->adj = NULL;
                }
                graph->v[i]->adjSize = 0;
                free(graph->v[i]);
                graph->v[i] = NULL;
            }
        }
        free(graph->v);
        graph->v = NULL;
    }
    graph->nrNodes = 0;
}

void enqueue(que** q, Node* s, Operation* op) {
    if (op != NULL)
        op->count(3);
    queN* aux = new queN();
    aux->key = s;
    aux->next = NULL;
    if ((*q)->first == NULL) {
        if (op != NULL)
            op->count(2);
        (*q)->first = aux;
        (*q)->last = aux;
    }
    else {
        if (op != NULL)
            op->count(3);
        aux->next = NULL;
        (*q)->last->next = aux;
        (*q)->last = aux;

    }


}


Node* dequeue(que** q, Operation* op) {
    if (op != NULL)
        op->count();
    queN* sters = NULL;
    Node* aux = NULL;
    if ((*q)->first != NULL) {
        if (op != NULL)
            op->count(4);
        aux = (*q)->first->key;
        sters = (*q)->first;
        (*q)->first = (*q)->first->next;
        free(sters);
        if ((*q)->first == NULL) {
            if (op != NULL)
                op->count();
            (*q)->last = NULL;
        }
    }
    return aux;




}
void bfs(Graph* graph, Node* s, Operation* op)
{
    for (int i = 0; i < graph->nrNodes; i++) {
        if (op != NULL)
            op->count();
        if (graph->v[i] != s) {
            if (op != NULL)
                op->count(3);
            graph->v[i]->color = COLOR_WHITE;
            graph->v[i]->dist = 0;
            graph->v[i]->parent = NULL;
        }
    }
    if (op != NULL)
        op->count(4);
    s->color = COLOR_GRAY;
    s->dist = 0;
    s->parent = NULL;
    que* q = new que();
    enqueue(&q, s,op);
    while (q->first) {
        Node* u = dequeue(&q,op);
        for (int j = 0; j < u->adjSize; j++) {
            if (op != NULL)
                op->count();
            if (u->adj[j]->color == 0) {
                if (op != NULL)
                    op->count(3);
                u->adj[j]->color = COLOR_GRAY;
                u->adj[j]->dist = u->dist + 1;
                u->adj[j]->parent = u;
                enqueue(&q, u->adj[j],op);

            }

        }
        if (op != NULL)
            op->count(2);
        u->color = COLOR_BLACK;

    }





}
void PrettyPrint(int array[], Point repr[], int root, int nivel, int n)
{
    for (int i = 0; i < nivel; i++) {
        printf("    ");
    }
    printf("(%d,%d) ", repr[root].row, repr[root].col);
    printf("\n");
    for (int j = 0; j < n; j++) {
        if (array[j] == root)
            PrettyPrint(array, repr, j, nivel + 1, n);
    }
}

void print_bfs_tree(Graph* graph)
{
    //first, we will represent the BFS tree as a parent array
    int n = 0; //the number of nodes
    int* p = NULL; //the parent array
    Point* repr = NULL; //the representation for each element in p

    //some of the nodes in graph->v may not have been reached by BFS
    //p and repr will contain only the reachable nodes
    int* transf = (int*)malloc(graph->nrNodes * sizeof(int));
    for (int i = 0; i < graph->nrNodes; ++i) {
        if (graph->v[i]->color == COLOR_BLACK) {
            transf[i] = n;
            ++n;
        }
        else {
            transf[i] = -1;
        }
    }
    if (n == 0) {
        //no BFS tree
        free(transf);
        return;
    }

    int err = 0;
    p = (int*)malloc(n * sizeof(int));
    repr = (Point*)malloc(n * sizeof(Node));
    for (int i = 0; i < graph->nrNodes && !err; ++i) {
        if (graph->v[i]->color == COLOR_BLACK) {
            if (transf[i] < 0 || transf[i] >= n) {
                err = 1;
            }
            else {
                repr[transf[i]] = graph->v[i]->position;
                if (graph->v[i]->parent == NULL) {
                    p[transf[i]] = -1;
                }
                else {
                    err = 1;
                    for (int j = 0; j < graph->nrNodes; ++j) {
                        if (graph->v[i]->parent == graph->v[j]) {
                            if (transf[j] >= 0 && transf[j] < n) {
                                p[transf[i]] = transf[j];
                                err = 0;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    free(transf);
    transf = NULL;

    if (!err) {
        int  root;
        for (int k = 0; k < n; k++) {
            if (p[k] == -1)
                root = k;
        }
        PrettyPrint(p, repr, root, 0, n);


    }

    if (p != NULL) {
        free(p);
        p = NULL;
    }
    if (repr != NULL) {
        free(repr);
        repr = NULL;
    }
}

int shortest_path(Graph* graph, Node* start, Node* end, Node* path[])
{
    // TODO: compute the shortest path between the nodes start and end in the given graph
    // the nodes from the path, should be filled, in order, in the array path
    // the number of nodes filled in the path array should be returned
    // if end is not reachable from start, return -1
    // note: the size of the array path is guaranteed to be at least 1000
    int nr = 0;
    bfs(graph, start, NULL);
    if (end->color ==COLOR_BLACK) {
        path[0] = end;
       
  }
    else
    {
      
        return -1;
    }
 
 while (path[nr] != start) {
        path[nr+1] = path[nr]->parent;
        nr++;
        
    }
    for (int j = 0; j < nr / 2; j++) {
        Node* aux = path[j];
        path[j] = path[nr - 1 - j];
        path[nr - 1 - j] = aux;
   }
    return nr;
}




void performance()
{
    srand(time(NULL));
    int n, i;
    Profiler p("bfs");

    // vary the number of edges
    for (n = 1000; n <= 4500; n += 100) {
        Operation op = p.createOperation("bfs-edges", n);
        Graph graph;
        graph.nrNodes = 100;


        graph.v = (Node**)malloc(graph.nrNodes * sizeof(Node*));
        for (i = 0; i < graph.nrNodes; ++i) {
            graph.v[i] = (Node*)malloc(sizeof(Node));
            memset(graph.v[i], 0, sizeof(Node));
        }
        // TODO: generate n random edges
        // make sure the generated graph is connected
        for (int i = 0; i < graph.nrNodes; ++i) {
            graph.v[i]->adjSize = 0;
            graph.v[i]->adj = (Node**)malloc(4501 * sizeof(Node*));

        }


        for (int i = 0; i < graph.nrNodes - 1; ++i) {
            graph.v[i]->adj[graph.v[i]->adjSize++] = graph.v[i + 1];
            graph.v[i+1]->adj[graph.v[i+1]->adjSize++] = graph.v[i];

        }


        for (int i = graph.nrNodes - 1; i < n; ++i) {
            int indexA = rand() % graph.nrNodes;
            int indexB = rand() % graph.nrNodes;

            while (indexA == indexB) {
                 indexA = rand() % graph.nrNodes;
                 indexB = rand() % graph.nrNodes;

            }

            graph.v[indexA]->adj[graph.v[indexA]->adjSize++] = graph.v[indexB];
            graph.v[indexB]->adj[graph.v[indexB]->adjSize++] = graph.v[indexA];
           

        }
        bfs(&graph, graph.v[0], &op);
        free_graph(&graph);

    }


    // vary the number of vertices
    for (n = 100; n <= 200; n += 10) {

        Operation op = p.createOperation("bfs-vertices", n);
        Graph graph;
        graph.nrNodes = n;
        //initialize the nodes of the graph
        graph.v = (Node**)malloc(graph.nrNodes * sizeof(Node*));
        for (i = 0; i < graph.nrNodes; ++i) {
            graph.v[i] = (Node*)malloc(sizeof(Node));
            memset(graph.v[i], 0, sizeof(Node));
        }
        // TODO: generate 4500 random edges
        // make sure the generated graph is connected
        for (int i = 0; i < graph.nrNodes; ++i) {
            graph.v[i]->adjSize = 0;
            graph.v[i]->adj = (Node**)malloc(4501 * sizeof(Node*));

        }

        for (int i = 0; i < graph.nrNodes - 1; ++i) {
            graph.v[i]->adj[graph.v[i]->adjSize++] = graph.v[i + 1];
            graph.v[i+1]->adj[graph.v[i+1]->adjSize++] = graph.v[i];
          
        }


        for (int i = graph.nrNodes - 1; i < 4500; ++i) {
            int indexA = rand() % graph.nrNodes;
            int indexB = rand() % graph.nrNodes;

            while (indexA == indexB) {
                indexA = rand() % graph.nrNodes;
                indexB = rand() % graph.nrNodes;

            }

            graph.v[indexA]->adj[graph.v[indexA]->adjSize++] = graph.v[indexB];
            graph.v[indexB]->adj[graph.v[indexB]->adjSize++] = graph.v[indexA];
          

        }

        bfs(&graph, graph.v[0], &op);
        free_graph(&graph);
    }

    p.showReport();
}
