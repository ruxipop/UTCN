/*Complexitati
 *creare_tree --O(n) ->am parcurs array ul o singura data/trec prin toate cheile o sg data
 * os_select --O(logn)->fiecare apel recursiv coboara un nivel in arbore.Inaltimea arborelui este logn de unde rezulta si complexitatea pt Os_select
 * os_delete-- O (logn)->fiecare linie ia timp constant ,inafara de apelul functiei succesor a carei complexitate este O(h),iar fiind un bst h=logn
 * */

#include <iostream>
#include "Profiler.h"
#define MAX_SIZE 10000
#define STEP_SIZE 100
#define NR_TESTS 5

Profiler profiler("raport");
typedef struct Node{
    int key;
    int size;
    struct Node *left;
    struct Node *right;
}Nod;

/*****************BUILD TREE*******************/
Nod* creare_node(int key,int size,const char*  m_Build,int n){
    if(m_Build!=NULL){

        profiler.countOperation(m_Build,n,3);

    }
    Nod* p = (Nod*)malloc(sizeof(Nod));

    if (p == NULL) {
        printf("Alocare nereusita!\n");
        exit(1);
    }
    p->key=key;
    p->size=size;
    return p;
}
Nod* creare_tree(int *array,int p,int r,const char*  m_Build,int n){

    if(p<=r){
        if(m_Build!=NULL){
            profiler.countOperation(m_Build,n,2);
        }
        int mij=(p+r)/2;
        Nod *root=creare_node(array[mij],r-p+1,m_Build,n);
        root->left = creare_tree(array,p, mij - 1, m_Build,n);
        root->right = creare_tree(array,mij + 1, r, m_Build,n);
       /* if (root->left && root->right)
        {
            root->size = root->left->size + root->right->size + 1;
        }
        else
        {   if (root->left == NULL && root->right == NULL)
                root->size = 1;
            else {
              if (root->left != NULL && root->right == NULL)
                    root->size = root->left->size + 1;
                else {

                    if (root->left == NULL && root->right != NULL)
                    root->size = root->right->size + 1;
                }
            }
       }*/

        return root;
    }
    else {
        return NULL;
    }

}

/*****************OS-SELECT*******************/
Nod* Os_Select(Nod *x, int p,const char*  m_Select ,int n){

    int r;
    if(m_Select!=NULL){
        profiler.countOperation(m_Select,n);
    }
    if (x->left == NULL){
        r = 1;}
    else{
        if(m_Select!=NULL){
            profiler.countOperation(m_Select,n);
        }
    r= x->left->size + 1;}
    if(m_Select!=NULL){
        profiler.countOperation(m_Select,n);
    }
    if (p == r){
        return x;
    }
    else {
        if(m_Select!=NULL){
            profiler.countOperation(m_Select,n);
        }
        if (p<r){
            return Os_Select(x->left, p, m_Select,n);
        }
        else return Os_Select(x->right, p - r, m_Select,n);
    }

}

/*****************OS-DELETE*******************/
Nod* succesor(Nod *node,const char*  m_Delete,int n) {
    if(m_Delete!=NULL){
        profiler.countOperation(m_Delete,n);
    }
    while (node->left != NULL) {
        node = node->left;
        if(m_Delete!=NULL){
            profiler.countOperation(m_Delete,n,2);
        }
    }
    return node;
}

Nod* Os_Delete(Nod *x, int p,const char*  m_Delete,int n){
    int r;
    if(m_Delete!=NULL){
        profiler.countOperation(m_Delete,n); }
    if (x->left == NULL){
        r = 1;}
    else{
        if(m_Delete!=NULL){
            profiler.countOperation(m_Delete,n);
        }
        r= x->left->size + 1;}
    if(m_Delete!=NULL){
        profiler.countOperation(m_Delete,n);}
    if(p==r)
    {   if(m_Delete!=NULL){
        profiler.countOperation(m_Delete,n);
        }
        if(x->left==NULL && x->right==NULL){
            free(x);
            return NULL;}
        if(m_Delete!=NULL){
            profiler.countOperation(m_Delete,n);
        }
        if(x->left==NULL){
            free(x);
            return x->right;}
        if(m_Delete!=NULL){
            profiler.countOperation(m_Delete,n);
        }
        if(x->right==NULL){
            free(x);
            return x->left;}
        Nod * NodSuccesor=succesor(x->right,m_Delete,n);
        if(m_Delete!=NULL){
            profiler.countOperation(m_Delete,n,2);
        }
        x->key=NodSuccesor->key;
        x->right=Os_Delete(x->right,1,m_Delete,n);
    }
    else {
        if(m_Delete!=NULL){
            profiler.countOperation(m_Delete,n);
        }
        if (p < r) {
            if(m_Delete!=NULL){
                profiler.countOperation(m_Delete,n);
            }
            x->left = Os_Delete(x->left, p, m_Delete, n);
        } else {
            if(m_Delete!=NULL){
                profiler.countOperation(m_Delete,n);
            }
            x->right = Os_Delete(x->right, p - r, m_Delete, n);
        }
    }
    if(m_Delete!=NULL){
        profiler.countOperation(m_Delete,n);
    }
    x->size--;
    return x;
}

/*****************PRINT*******************/
void prettyPrint( Nod *root, int nivel)
{
    if (root == NULL)
    {
        nivel--;
        return;
    }
    nivel++;
    prettyPrint(root->right, nivel);
    for (int j = 0; j < nivel - 1; j++)
    {
        printf("     ");

    }
    printf("%d|%d", root->key, root->size);
    printf("\n");
    prettyPrint( root->left, nivel);
}


/*****************MASURATORI*******************/
void masuratori(const char*  m_Select,const char*  m_Delete,    const char* m_Build){
    int * array=new int[10001];
    Nod * selectedNode;
    Nod * deletNode;
    for (int n =STEP_SIZE; n <= MAX_SIZE; n += STEP_SIZE){
        profiler.countOperation(m_Build, n, 0);
        profiler.countOperation(m_Select, n, 0);
        profiler.countOperation(m_Delete, n, 0);
        for (int t = 0; t < NR_TESTS; t++){
             int lungime=n;
            for(int i=0;i<n;i++)
            {
                array[i]=i+1;
            }
           Nod	*root = creare_tree(array,0, n-1,m_Build,lungime);
           // prettyPrint(root,0);
            for (int i = 0; i < n; i++) {
                int randomIndex = rand() % (n-i) +1;
               selectedNode=Os_Select(root, randomIndex, m_Select,lungime);
              root=Os_Delete(root,randomIndex,m_Delete,lungime);
                // prettyPrint(root,0);

            }
        }
    }
    profiler.divideValues(m_Select, NR_TESTS);
    profiler.divideValues(m_Delete, NR_TESTS);
    profiler.divideValues(m_Build, NR_TESTS);
}

void measurements(){
    const char*  m_Select= "Select";
    const char* m_Delete = "Delete";
    const char* m_Build = "Build";
    masuratori(m_Select,m_Delete,m_Build );
    profiler.createGroup("Masuratori", m_Select,m_Delete,m_Build );
    profiler.showReport();
}

/*****************DEMO*******************/
void demo(){
    time_t t;
    srand((unsigned) time(&t));
    int n=11;

    int * array=new int[n];

    for(int i=0;i<n;i++)
    {
        array[i]=i+1;
    }
    Nod * rootDemo= creare_tree(array,0, n-1,NULL,n);

    prettyPrint(rootDemo,0);
  for(int i=0;i<3;i++){

      int randomIndexDemo=rand()%(n-i)+1;
        printf("Indexul este %d",randomIndexDemo);
        printf("\n");
        Nod* s= Os_Select(rootDemo,randomIndexDemo,NULL,n);
        printf("Nodul cu cheia %d va fi sters",s->key);
        printf("\n");
        rootDemo=Os_Delete(rootDemo,randomIndexDemo,NULL,n);
        prettyPrint(rootDemo,0);
      n--;
    }
    free(array);
}

int main() {
   measurements();
  //demo();
    return 0;
}
