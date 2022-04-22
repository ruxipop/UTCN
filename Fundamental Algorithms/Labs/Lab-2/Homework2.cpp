//Pop Ruxandra Maria grupa 30226
//Bottom-up ->average case: O(nlogn)
//Top-down  ->average case: O(nlogn)
//size-ul array-ului este de 10000
//sirul creste cu un increment de 100
//realizam 5 teste pentru cazul mediu

#include <iostream>
using namespace std;
#define MAX_SIZE 10000
#define STEP_SIZE 100
#define NR_TESTS 5
#include "Profiler.h"
Profiler p("raport");
int heap_size;
int left(int i){
    return 2 * i + 1;
}
int right(int i){
    return 2 * i + 2;
}
int parent(int i){
    return (i-1)/2;
}

void Heapify(int myArray[],int n,int i,const char* val_bottom_up){
    int largest=i; //root
    int child_l=left(i);
    int child_r=right(i);
    if(child_l<n ){
        if (val_bottom_up!= NULL)
        {
            p.countOperation(val_bottom_up, n);
        }
        if(myArray[child_l]>myArray[largest])
        {
            largest=child_l;
        }
    }

    if(child_r<n ){
        if (val_bottom_up!= NULL)
        {
            p.countOperation(val_bottom_up, n);
        }
        if(myArray[child_r]>myArray[largest])
        {
            largest=child_r;
        }
    }

    if(largest!=i) {
        if (val_bottom_up!= NULL)
        {
            p.countOperation(val_bottom_up, n,3);
        }
        swap(myArray[i],myArray[largest]);
        Heapify(myArray, n, largest,val_bottom_up);
    }
}

 void build_heap_bottom_up(int myArray[],int n,const char* val_bottom_up){
    for(int i=(n/2)-1;i>=0;i--)
    {
        Heapify(myArray,n,i,val_bottom_up);
    }
}

void HeapSort(int myArray[],int n,const char* val_bottom_up){
  build_heap_bottom_up(myArray,n,val_bottom_up);
  for(int i=n-1;i>0;i--){
      swap(myArray[0],myArray[i]);
      Heapify(myArray,i,0,val_bottom_up);
  }
}

void heap_increase_key(int myArray[],int i,int key,int n,const char* val_up_down) {
    if (i > 0) {
        if (val_up_down != NULL) {
            p.countOperation(val_up_down, n);
        }
        while  ( myArray[parent(i)] < myArray[i])
        {
            if (val_up_down != NULL)
            {
                p.countOperation(val_up_down, n, 3);
            }
            swap(myArray[i], myArray[parent(i)]);
            i = parent(i);
            if(i<=0)
                break;
            if (val_up_down != NULL) {
                p.countOperation(val_up_down, n);
            }
        }

    }
}

void build_insert_heap(int myArray[],int key,int *heap_size,int n,const char* val_top_down){
    heap_increase_key(myArray,*heap_size,key,n,val_top_down);
    if (val_top_down!= NULL)
    {
        p.countOperation(val_top_down, n);
    }
    (*heap_size)++;
}

 void build_heap_top_down(int myArray[],int n,int heap_size,const char* val_top_down){
    for(int i=0;i<n;i++)
    {
        build_insert_heap(myArray,myArray[i],&heap_size,n,val_top_down);
    }
}
void measurementAverage(int myArray[],const char* val_bottom_up,const char* val_top_down){
    for (int n = 0; n <= MAX_SIZE; n = n + STEP_SIZE)
    {
        p.countOperation(val_bottom_up, n, 0);
        p.countOperation(val_top_down, n, 0);
        for (int test = 0; test < NR_TESTS; test++)
        {
            FillRandomArray(myArray, n);
            int myArray_b[MAX_SIZE], myArray_u[MAX_SIZE];
            for (int i = 0; i < n; i++)
            {
                myArray_b[i] =myArray[i];
                myArray_u[i] =myArray[i];
            }
            build_heap_top_down(myArray_u, n, heap_size, val_top_down);
            build_heap_bottom_up(myArray_b, n, val_bottom_up);
        }
    }
    p.divideValues(val_top_down, NR_TESTS);
    p.divideValues(val_bottom_up, NR_TESTS);
}

void measurementWorst_B(int arrayB[],const char* val_bottom_up1){
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(arrayB, n, 0, 50000, true, ASCENDING);
        p.countOperation(val_bottom_up1, n, 0);
        build_heap_bottom_up(arrayB,n,val_bottom_up1);
    }
}
void measurementWorst_T(int arrayB[],const char* val_top_down1){
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE)
    {
        FillRandomArray(arrayB, n, 0, 50000, true, ASCENDING);
        p.countOperation(val_top_down1, n, 0);
        build_heap_top_down(arrayB, n,heap_size, val_top_down1);
    }
}
void measurements(int myArray[]){

    const char* val_bottom_up = "Average_Bottom_Up";
    const char* val_top_down= "Average-Top-Down";
    const char* val_bottom_up1 = "Worst_Bottom_Up";
    const char* val_top_down1= "Worst-Top-Down";

    measurementAverage(myArray, val_bottom_up,val_top_down);
    measurementWorst_B(myArray, val_bottom_up1);
    measurementWorst_T(myArray, val_top_down1);

    p.createGroup("Average",val_bottom_up,val_top_down);
    p.createGroup("Worst",val_bottom_up1,val_top_down1);
    p.showReport();
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
    HeapSort(array, n , NULL);
    for (int i = 0; i < n; i++)
    {
        printf("%d ", array[i]);
    }
    printf("\n");
    printf("Done!\n");
}

void test_array(int array[], int n, int* pass, int* fails)
{
    bool v = true;
    for (int i=(n/2)-1;i>=0;i--)
    {
            if (array[left(i)]>array[i]|| array[right(i)]>array[i])
            {
                v = false;
                break;
            }


        }

    if (v == true)
        (*pass)++;
    else
        (*fails)++;
}


void demo1() {
    int n = rand() % MAX_SIZE;
    int array[MAX_SIZE];
    int pass = 0, fails = 0;
    for (int test = 0; test < NR_TESTS; test++)
    {
        if (test == 0)
            for (int i = 0; i < n; i++)
                array[i] = i;
        else if (test == n - 1)
            for (int i = 0; i < n; i++)
                array[i] = n - i;
        else
            for (int i = 0; i < n; i++)
                array[i] = rand() % MAX_INPUT;
        //ALEGEM CE ALGORITM DORIM SA VEIRIFCAM
       build_heap_bottom_up(array,n,NULL);
        test_array(array, n, &pass, &fails);
    }
    printf("Pass: %d \nFails: %d\n", pass, fails);
}


int main() {
     int array[MAX_SIZE];
    measurements(array);
     //demo();
     //demo1();
}

