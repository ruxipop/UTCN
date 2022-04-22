//Complexitatea celor 2 transformari este O(n)
//Pentru R1-- AVEM UN ARRAY DE PARINTI
//Pentru R2-- FOLOSIM O STRUCTURA CARE CONTINE CHEIA ,NR DE COPII,SI O LISTA CU ADRESA COPIILOR
//Pentru R3-- FOLOSIM O STRUCTURA CARE CONTINE CHEIA,PRIMUL COPIL,SI URM FRATE

#include <iostream>
#define N 3
using namespace std;
typedef struct Node{
    int key;
    int nrCopii;
    struct Node *copii[N];

}NodeR2;

typedef struct Node1{
    int key;
    struct Node1* primCopil;
    struct Node1* urmFrate;
}NodeR3;


void PrettyPrint1(int array[],int root,int nivel,int n)
{for(int i=0;i<nivel;i++){
        printf("   ");
}
    printf("%d",root);
    printf("\n");
    for(int j=1;j<n;j++){
        if(array[j]==root)
            PrettyPrint1(array,j,nivel+1,n);
    }
}

NodeR2 * CreateNodeR2(int key){
    NodeR2 *p=(NodeR2*)malloc(sizeof(NodeR2));
    p->key=key;
    p->nrCopii=0;
    return p;
}

NodeR2* transformareT1(int parent[],int n){
    NodeR2* list[n];
    NodeR2* rootR2=(NodeR2*)malloc(sizeof(NodeR2));
    for(int i=1;i<n;i++){
        NodeR2 * a=CreateNodeR2(i);
        list[i]=a;
    }

    for(int i=1;i<n;i++){
        if(parent[i]!=-1) {
            list[parent[i]]->copii[list[parent[i]]->nrCopii] = list[i];
            list[parent[i]]->nrCopii++;
        }
        else
            rootR2=list[i];
    }
    return rootR2;
    free(list);
    free(rootR2);

}

void PrettyPrint2(NodeR2 *root,int nivel){
    if(root!=NULL){
        for(int i=0;i<nivel;i++){
            printf("   ");
        }
        printf("%d",root->key);
        printf("\n");
        for(int j=0;j<root->nrCopii;j++)
            PrettyPrint2(root->copii[j],nivel+1);
    }

}

NodeR3* transformareT2(NodeR2 * rootR2){
    NodeR3 * rootR3=(NodeR3*)malloc(sizeof(NodeR3));
    NodeR3 * nodPrec=(NodeR3*)malloc(sizeof(NodeR3));
    rootR3->key=rootR2->key;
    rootR3->urmFrate=NULL;

    if(rootR2->nrCopii==0)
        rootR3->primCopil=NULL;
    else {
        rootR3->primCopil = transformareT2(rootR2->copii[0]);
         nodPrec=rootR3->primCopil;
         for(int i=1;i<rootR2->nrCopii;i++){
             nodPrec->urmFrate=transformareT2(rootR2->copii[i]);
             nodPrec=nodPrec->urmFrate;
         }
    }
    return rootR3;
    free(nodPrec);
    free(rootR3);
}

void PrettyPrint3(NodeR3* root,int nivel){
    if(root!=NULL){
        for(int i=0;i<nivel;i++){
            printf("   ");
        }
        printf("%d",root->key);
        printf("\n");
        PrettyPrint3(root->primCopil,nivel+1);
        PrettyPrint3(root->urmFrate,nivel);
    }

}

void demo(){

    int parent[] = {0,2,7,5,2,7,7, -1,5,2};
    int n =sizeof(parent)/sizeof(parent[0]);
    int root1;
    for(int i=0;i<n;i++){
        if(parent[i]==-1)
            root1=i;
    }
    printf("PrettyPrint 1:");
    printf("\n");
    PrettyPrint1(parent,root1,0,n);
    NodeR2 *root2= transformareT1(parent,n);
    printf("PrettyPrint 2:");
    printf("\n");
    PrettyPrint2(root2,0);
    NodeR3 *root3= transformareT2(root2);
    printf("PrettyPrint 3:");
    printf("\n");
    PrettyPrint3(root3,0);
}

int main()
{
   demo();
   return 0;
} 