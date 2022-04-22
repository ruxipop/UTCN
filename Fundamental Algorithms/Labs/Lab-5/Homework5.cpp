#include <iostream>
#include <fstream>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>

#include "Profiler.h"

#define N 10007
#define NR_TEST 5
#define c1 1
#define c2 1

int nrCelule;

void afisare(int array [],int n){
    int i;
    printf("\nTabela este:\n");
    for(i=0;i<n;i++){
        if(array[i]!=-1)
            printf("T[%d]=%d\n",i,array[i]);
        else
            printf("T[%d]=--\n",i);
    } }


int h_prime(int k,int n){
    return k%n;
}

int quadratic_probing(int k,int i,int n){

    return( h_prime( k, n)+c1*i+c2*i*i)%n;
}


int inserare_Hash(int array[], int key, int n)
{
    int i;
    for (i = 0; i < n; i++) {
        int poz = quadratic_probing(key, i, n);
        if (array[poz] == -1) {
            return poz;
        }
    }
    return -1;
}

void initiere_Hash(int array [],int n) {

    for (int i = 0; i < n; i++) {
        array[i] = -1;
    }
}

void creare_Hash(int array[], float alfa, int n, int aj[], int *nAj)
{
    initiere_Hash(array,n);
    srand (time(NULL));
    for (int i = 0; i < (ceil)(alfa * n); i++) {
        int  key = rand() % 100001+ 1;
        int poz = inserare_Hash(array, key, n);
        if (poz != -1) {
            array[poz] = key;
            aj[*nAj] = key;
            (*nAj)++;

        }
    }
}

int cautare_Hash(int array[], int key, int n) {
    int i = 0;
    int j;
    do {
        nrCelule++;
        j = quadratic_probing(key, i, n);
        if (array[j] == key) {
            return j;
        }
        else {
            i++;
        }
    } while (i < n && array[j] != -1);
    return -1;

}


void generare( float alfa, FILE* pf) {

    int* hash = (int*)malloc(sizeof(int) * N);
    int* aj = (int*)malloc(sizeof(int) * N);
    int* v= (int*)malloc(sizeof(int) * 1500);


        int maxNrGasite = 0;
        int maxNrNegasite = 0;
        double numarGasite = 0;
       double  numarNegasite = 0;
        for (int test = 0; test < NR_TEST; test++) {
            int nAj = 0;
            creare_Hash(hash, alfa, N, aj, &nAj);

            FillRandomArray(v,1500, 0, nAj - 1, true, 0);

            for (int i = 0; i < 1500; i++) {
                nrCelule = 0;
                cautare_Hash(hash, aj[v[i]], N);
                numarGasite += nrCelule;
                if(nrCelule>maxNrGasite)
                    maxNrGasite=nrCelule;

            }


            FillRandomArray(v, 1500,    100001,    1000001, true, 0);

            for (int i = 0; i < 1500; i++) {
               nrCelule = 0;
                cautare_Hash(hash, v[i], N);
                numarNegasite += nrCelule;
                if(nrCelule>maxNrNegasite)
                    maxNrNegasite=nrCelule;

            }

        }

        fprintf(pf, "%.2f,%d,%.2f,%d\n", numarGasite / (NR_TEST*1500), maxNrGasite, numarNegasite /( NR_TEST*1500), maxNrNegasite);
        free(hash);
        free(v);
        free(aj);

}


void masuratori() {
    FILE* pf = fopen("hash_obs.csv", "w+");
    if (pf) {
        fprintf(pf, "Factor de Umplere,Efort mediu gasite,Efort maxim gasite,Efort mediu negasite,Efort maxim negasite \n");

        fprintf(pf,"0.8, ");
        generare( 0.8, pf);

        fprintf(pf,"0.85, ");
        generare( 0.85, pf);

        fprintf(pf,"0.9, ");
        generare( 0.9, pf);

        fprintf(pf,"0.95, ");
        generare( 0.95, pf);

        fprintf(pf,"0.99, ");
        generare(0.99, pf);

    }
    else {
        printf("Nu s-a putut deschide fisierul!");
    }
}


void demo() {


    float alfa = 0.95;


    int *array = (int *) malloc(sizeof(int) * N);
    int *dummy = (int *) malloc(sizeof(int) * N);
    int Ndummy=0;
    int contor = 0;
    creare_Hash(array,alfa,N,dummy,&Ndummy);
    for (int i = 0; i < N; i++) {
        if (array[i] != -1) {
            printf("%d  ", array[i]);

        }

    }

    int poz=cautare_Hash(array, 3, N);
    printf("\n");
    if (poz!=-1)
        printf("Gasit pe pozitia %d\n",poz);
    else
        printf("Nu exista!\n");




}

int main()
{

//  demo();
masuratori();

    return 0;
}
