/* Heapsort O(nlogn)-average
 * QuickSort average-O(nlogn)
 *           worst-O(n^2)
 *  In average case Quicksort is the best as you can see in the graph(between Heapsort and Quicksort)
 *  Pentru a evita worst case pentru QuickSort ar trebui sa alegem pivotul in diferite moduri.
 *  Primul mod ar fi: alegerea pivotului ca mijlocul array-ului, altfel am impartit sirul in 2 subsiruri cu un numar aproape egal de elemente
 *  Al doilea mod: folosind algoritmul randomized QuickSort
 *
 * */

#include <iostream>
using namespace std;
#define MAX_SIZE 10000
#define STEP_SIZE 100
#define NR_TESTS 5
#define DEMO_QUICK 3
#include "Profiler.h"
Profiler MyProfiler("raport");

int left(int i){
    return 2 * i + 1;
}
int right(int i){
    return 2 * i + 2;
}
int parent(int i){
    return (i-1)/2;
}

void Heapify(int myArray[],int n,int i,int lungime, const char* HeapSort_avg){
    int largest=i; //root
    int child_l=left(i);
    int child_r=right(i);
    if(child_l<n ){
        if ( HeapSort_avg!= NULL)
        {
            MyProfiler.countOperation(HeapSort_avg, lungime);
        }
        if(myArray[child_l]>myArray[largest])
        {
            largest=child_l;
        }
    }

    if(child_r<n){
        if (HeapSort_avg!= NULL)
        {
            MyProfiler.countOperation(HeapSort_avg, lungime);
        }
        if(myArray[child_r]>myArray[largest])
        {
            largest=child_r;
        }
    }

    if(largest!=i) {
        if (HeapSort_avg!= NULL)
        {
            MyProfiler.countOperation(HeapSort_avg, lungime,3);
        }
        swap(myArray[i],myArray[largest]);
        Heapify(myArray, n, largest,lungime,HeapSort_avg);
    }
}

void HeapSort(int myArray[],int n,int lungime,const char* HeapSort_avg){
    for(int i=(lungime/2)-1;i>=0;i--)
    {
        Heapify(myArray,n,i,lungime,HeapSort_avg);
    }
    for(int i=n-1;i>0;i--){
        if (HeapSort_avg!= NULL)
        {
            MyProfiler.countOperation(HeapSort_avg, lungime,3);
        }
        swap(myArray[0],myArray[i]);
        Heapify(myArray,i,0,lungime ,HeapSort_avg);
    }
}
int Partition(int myArray[],int p,int r,int n,const char* comp){

    if (comp!= NULL)
    {
        MyProfiler.countOperation(comp,n);
    }
    int pivot=myArray[r];
    int i=p-1;
    for(int j=p;j<r;j++){
        if ( comp!= NULL)
        {
            MyProfiler.countOperation(comp, n);
        }
        if(myArray[j]<=pivot){
            i++;
            if ( comp!= NULL)
            {
                MyProfiler.countOperation(comp,n,3);
            }
            swap(myArray[i],myArray[j]);
        }
    }
    if ( comp!= NULL)
    {
        MyProfiler.countOperation(comp,n, 3);
    }
    swap(myArray[i+1],myArray[r]);
    return (i+1);}

void QuickSort(int myArray[],int p,int r,int n,const char* comp){
    int q;
    if(p<r){
        q=Partition(myArray,p,r,n,comp);// ar trebui sa apelam randomized_partition ca sa fie mai eficient alg "Lomuto Partition"
        QuickSort(myArray,p,q-1,n,comp);
        QuickSort(myArray,q+1,r,n,comp);
    }
}
int random(int p, int r) {
    int i;
    while (true)
    {
        i = rand();
        if (i <= r && i >= p)
            return i;
    }
}

int randomized_partition(int myArray[],int p,int r){
    int i=random(p,r);
    swap(myArray[r],myArray[i]);
    return Partition(myArray,p,r,r+1,NULL);

}

int QuickSelect(int myArray[], int p, int r, int i) {
    if (p == r)
        return myArray[p];
    int q = randomized_partition(myArray, p, r);
    int k = q - p + 1;
    if (i == k)
        return myArray[q];
    else if (i < k)
        return QuickSelect(myArray, p, q - 1, i);
    else return QuickSelect(myArray, q + 1, r, i - k);
}


void measurementAverage(int myArray[],const char* QuickSort_avg,const char* HeapSort_avg){
        for (int n = 0; n <= MAX_SIZE; n = n + STEP_SIZE)
        {
            MyProfiler.countOperation(QuickSort_avg, n, 0);
            MyProfiler.countOperation(HeapSort_avg, n, 0);
            for (int test = 0; test < NR_TESTS; test++)
            {
                FillRandomArray(myArray, n);
                int myArray_b[MAX_SIZE];
                for (int i = 0; i < n; i++)
                {
                    myArray_b[i] =myArray[i];

                }
              HeapSort(myArray_b,n,n,HeapSort_avg);
                QuickSort(myArray,0,n-1,n,QuickSort_avg);
            }
        }
        MyProfiler.divideValues(QuickSort_avg, NR_TESTS);
        MyProfiler.divideValues(HeapSort_avg, NR_TESTS);
    }

void measurementAverage_Q(int myArray[],const char* QuickSort_avg1){
    for (int n = 0; n <= MAX_SIZE; n = n + STEP_SIZE)
    {
        MyProfiler.countOperation(QuickSort_avg1, n, 0);
        for (int test = 0; test < NR_TESTS; test++) {
            FillRandomArray(myArray, n);
            QuickSort(myArray, 0, n - 1, n, QuickSort_avg1);
        }
    }
    MyProfiler.divideValues(QuickSort_avg1, NR_TESTS);
}

void measurementWorst(int myArray[], const char* QuickSort_worst)
{
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(myArray, n, 0, 5000, false, ASCENDING);
        MyProfiler.countOperation(QuickSort_worst, n, 0);
        QuickSort(myArray,0,n-1,n,QuickSort_worst);
    }
}


void measurements(int myArray[]){

        const char* QuickSort_avg= "Average_QuickSort";
        const char* HeapSort_avg= "Average_HeapSort";
        const char* QuickSort_worst= "Worst_QuickSort";
        const char* QuickSort_avg1= "Average_QuickSort.";

        measurementAverage(myArray,QuickSort_avg,HeapSort_avg);

        measurementWorst(myArray,QuickSort_worst);
    measurementAverage_Q(myArray,QuickSort_avg1);


        MyProfiler.createGroup("Average_QuickSort_Heapsort", QuickSort_avg,HeapSort_avg);
        MyProfiler.createGroup("Worst_Average_Quicksort",QuickSort_worst,QuickSort_avg1);
        MyProfiler.showReport();
    }

void demo(){
    int n=6;
    int array[MAX_SIZE];
    FillRandomArray(array, n, 0, 5000, false, UNSORTED);
    for (int i = 0; i < n; i++)
    {
        printf("%d ", array[i]);
    }
    printf("\n");
    QuickSort(array,0,n-1,n,NULL);

 for (int i = 0; i < n; i++)
   {
       printf("%d ", array[i]);
  }
    printf("\n");
    printf("Done!\n");
}

void demoQuickSelect(){
     int n=6;
     int array[MAX_SIZE];
        FillRandomArray(array, n, 0, 5000, false,UNSORTED );
        for (int i = 0; i < n; i++)
        {
            printf("%d ", array[i]);
        }
        printf("\n");
        printf("%d",QuickSelect(array,0,n-1,DEMO_QUICK));


}
int main() {
    int array[MAX_SIZE];
     //demo();
       demoQuickSelect();
     // measurements(array);

    return 0;
}
