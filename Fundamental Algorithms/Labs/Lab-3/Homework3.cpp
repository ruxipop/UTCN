/*
MergeList-K1--O(nlogk)
MergeList-K2--O(nlogk)
MergeList-K3--O(nlogk)
MergeList-N--O(logk)
 */
#include <iostream>
#include "Profiler.h"
using namespace std;
Profiler p("raport");
#define K 4
#define K1 5
#define K2 10
#define K3 100
#define N 10000
#define N1 20
#define  MAX_SIZE 10000


struct node {
    int key;
    struct node* next;

};
    node *first[MAX_SIZE];
    node *last[MAX_SIZE];


int left(int i) {
    return 2 * i + 1;
}
int right(int i) {
    return 2 * i + 2;
}

void Heapify(node* array[], int i, int heapsize, int mk, const char* comparasions) {
    int smallest = i; //root
    int child_l = left(i);
    int child_r = right(i);
    if (comparasions != NULL)
    {
        p.countOperation(comparasions, mk);
    }
    if (child_l < heapsize) {
        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk);
        }
        if (array[child_l]->key < array[smallest]->key) {
            smallest = child_l;
        }
    }
    if (comparasions != NULL)
    {
        p.countOperation(comparasions, mk);
    }
    if (child_r < heapsize) {
        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk);
        }
        if (array[child_r]->key < array[smallest]->key) {
            smallest = child_r;
        }
    }

    if (smallest != i) {
        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk, 3);
        }
        swap(array[i],array[smallest]);
        Heapify(array, smallest, heapsize, mk, comparasions);
    }
}

void BuildMinHeap(node* list[], int k, int mk, const char* comparasions) {
    for (int i = (k / 2) - 1; i >= 0; i--)
    {
        Heapify(list, i, k, mk, comparasions);
    }
}

void insertLast(node* init, int key, int m, int mk, const char* comparasions) {
    init = (node*)malloc(sizeof(node));
    init->key = key;
    init->next = NULL;
    if (comparasions != NULL)
    {
        p.countOperation(comparasions, mk);
    }
    if (first[m]== NULL)
    {
        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk, 2);
        }
        first[m] = init;
        last[m] = init;
    }
    else {
        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk);
        }
        if (last[m] != NULL) {
            if (comparasions != NULL)
            {
                p.countOperation(comparasions, mk, 2);
            }
            init->next = NULL;
            last[m]->next = init;
            last[m] = init;
        }
    }
}
void insertLast1(node* init, int key, int m, int mk) {
    init = (node*)malloc(sizeof(node));
    init->key = key;
    init->next = NULL;

    if (first[m] == NULL)
    {
        first[m] = init;
        last[m] = init;
    }
    else {
        if (last[m] != NULL) {
            init->next = NULL;
            last[m]->next = init;
            last[m] = init;
        }
    }
}
void mergeKLists(node* list[], int k, int heapsize, int mk, const char* comparasions) {
    node* init = NULL;
    BuildMinHeap(list, k, mk, comparasions);
    if (comparasions != NULL)
    {
        p.countOperation(comparasions, mk);
    }
    while (heapsize > 0) {
        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk);
        }
        init = list[0];
        insertLast(init, init->key, k + 1, mk, comparasions);
        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk,2);
        }
        list[0] = (list[0])->next;
        if ((list[0]) == NULL) {
            if (comparasions != NULL)
            {
                p.countOperation(comparasions, mk);
            }
            list[0] = list[heapsize-1];
            heapsize = heapsize - 1;

        }
        Heapify(list, 0, heapsize, mk, comparasions);

        if (comparasions != NULL)
        {
            p.countOperation(comparasions, mk);
        }

    }
}

void measurementK1(int array[], const char* val_K1) {
    node* measuredArray[MAX_SIZE];
    for (int i = 0; i < K1; i++) {
        measuredArray[i] = NULL;
    }
    for (int n = 100; n <= MAX_SIZE; n += 100) {
        for (int i = 0; i < K1; i++)
        {
            int mk=0;
            int lmk=0;
            first[i] = NULL;
            last[i] = NULL;
            if (n % K1 == 0) {
                mk = n/ K1;
                lmk = n / K1;

            }
            else {
                mk = n/ K1;
                lmk = n - (n / K1) * (K1 - 1);
            }
                if (i == K1 - 1)
                {
                    first[i] = NULL;
                    last[i] = NULL;
                    FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                    for (int j = 0; j < lmk; j++) {
                        insertLast1(measuredArray[j], array[j], i, lmk);
                    }
                }
                else {
                    first[i] = NULL;
                    last[i] = NULL;
                    FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                    for (int j = 0; j < mk; j++) {
                        insertLast1(measuredArray[j], array[j], i, mk);
                    }
                }

            }
        int heapsize=K1;
        p.countOperation(val_K1,n,0);
        mergeKLists(first, K1, heapsize, n, val_K1);
        first[K1 + 1] = NULL;

    }

}
void measurementK2(int array[], const char* val_K2) {
    node* measuredArray[MAX_SIZE];
    for (int i = 0; i < K2; i++) {
        measuredArray[i] = NULL;
    }
    for (int n = 100; n <= MAX_SIZE; n += 100) {
        for (int i = 0; i < K2; i++) {
            int mk=0;
            int lmk=0;
            first[i] = NULL;
            last[i] = NULL;
            if (n % K2 == 0) {
                mk = n/ K2;
                lmk = n / K2;
            }
            else {
                mk = n/ K2;
                lmk = n - (n / K2) * (K2 - 1);
            }
            if (i == K2 - 1) {
                first[i] = NULL;
                last[i] = NULL;
                FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                for (int j = 0; j < lmk; j++) {
                    insertLast1(measuredArray[j], array[j], i, lmk);
                }
            }
            else {
                first[i] = NULL;
                last[i] = NULL;
                FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                for (int j = 0; j < mk; j++) {
                    insertLast1(measuredArray[j], array[j], i, mk);
                }
            }

        }
        int heapsize=K2;
        p.countOperation(val_K2,n,0);
        mergeKLists(first, K2, heapsize, n, val_K2);
        first[K2 + 1] = NULL;

    }

}
void measurementK3(int array[], const char* val_K3) {
    node* measuredArray[MAX_SIZE];
    for (int i = 0; i < K3; i++) {
        measuredArray[i] = NULL;
    }
    for (int n = 100; n <= MAX_SIZE; n += 100) {
        for (int i = 0; i < K3; i++) {
            int mk=0;
            int lmk=0;
            first[i] = NULL;
            last[i] = NULL;
            if (n % K3 == 0) {
                mk = n/ K3;
                lmk = n / K3;

            }
            else {
                mk = n/ K3;
                lmk = n - (n / K3) * (K3 - 1);
            }
            if (i == K3 - 1) {
                first[i] = NULL;
                last[i] = NULL;
                FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                for (int j = 0; j < lmk; j++) {
                    insertLast1(measuredArray[j], array[j], i, lmk);
                }
            }
            else {
                first[i] = NULL;
                last[i] = NULL;
                FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                for (int j = 0; j < mk; j++) {
                    insertLast1(measuredArray[j], array[j], i, mk);
                }
            }

        }
        int heapsize=K3;
        p.countOperation(val_K3,n,0);
        mergeKLists(first, K3, heapsize, n, val_K3);
        first[K3 + 1] = NULL;
    }

}
void measurementN(int array[], const char* operationN) {
    node* measuredArray[MAX_SIZE];
    for (int i = 0; i < K3; i++) {
        measuredArray[i] = NULL;
    }
    for (int k = 10; k <= 500; k += 10) {
        p.countOperation(operationN, k, 0);
        int n = N;
        int mk = 0, lmk = 0;
        if (N % k == 0) {
            mk = N / k;
            lmk = N / k;

        }
        else {
            mk = N / k;
            lmk = N - (N / k) * (k - 1);
        }
        for (int i = 0; i < k; i++) {
            if (i == k - 1) {
                first[i] = NULL;
                last[i] = NULL;
                FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                for (int j = 0; j < lmk; j++) {
                    insertLast1(measuredArray[j], array[j], i, lmk);
                }
            }
            else {
                first[i] = NULL;
                last[i] = NULL;
                FillRandomArray(array, lmk, 0, 50000, false, ASCENDING);
                for (int j = 0; j < mk; j++) {
                    insertLast1(measuredArray[j], array[j], i, mk);
                }
            }

        }
        int heapsize = k;
        mergeKLists(first, k, heapsize, k, operationN);
        first[k + 1] = NULL;
    }
}
void printListInitial(node* Nod)
{
    while (Nod != NULL) {
        cout << Nod->key << "->";
        Nod = Nod->next;
    }
    cout << "NULL";
}

void printListFinal(node * init,int i){
   init=first[i];
    while(init){
        cout<<init->key<< "->";
        init=init->next;

    }
    cout<<"NULL";
}

void demo(int array[]) {
    node *measuredArray[MAX_SIZE];
    int n = N1;
    for (int i = 0; i < K; i++) {
        measuredArray[i] = NULL;
    }
       for (int i = 0; i < K; i++) {
            first[i] = NULL;
            last[i] = NULL;
            FillRandomArray(array, n ,0, 50000, false, ASCENDING);
            for (int j = 0; j < n; j++) {
                insertLast(measuredArray[j], array[j], i, n, NULL);
            }

        }
    printf("Initial lists:");
    printf("\n");
    for (int i = 0; i < K; i++) {
        printf("List[%d]->",i);
        printListInitial(first[i]);
        printf("\n");
    }

      mergeKLists(first, K, K, n, NULL);

    printf("Sorted  array:");
    printf("\n");
    node* init=NULL;
     printListFinal(init,K+1);
}

void measurements(int myArray[]) {

    const char* val_K1 = "MergeList-K1";
    const char* val_K2 = "MergeList-K2";
    const char* val_K3 = "MergeList-K3";
    const char* operationN = "MergeList-N";


    measurementK1(myArray, val_K1);
    measurementK2(myArray, val_K2);
    measurementK3(myArray, val_K3);
    measurementN(myArray, operationN);

    p.createGroup("MergeKList", val_K1,val_K2,val_K3);
    p.createGroup("MergeKList-K2-K3",val_K2,val_K3);
    p.createGroup("MergeList-N", operationN);

    p.showReport();
}

int main() {

    int array[MAX_SIZE];
    measurements(array);
   // demo(array);
    return 0;
}