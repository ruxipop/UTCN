/*
* @author Pop Ruxandra Maria
* @group 30226

Bubble_sort  -> average and worst case complexity:O(n^2)(assignments=comparisons=total=0(n^2))
            ->best case complexity:O(n) (assignments=0(1) ,comparisons=0(n),total=0(n))
            -> stable (two objects with equal keys appear in the same order in sorted output as they
               appear in the input  array to be sorted)
            ->not a practical sorting when n is large because is very slow
            ->adaptive  0(n)-> O(n^2)

 Insertion_sort -> average and worst case complexity:O(n^2)(assignments=total=0(n^2),comparisons=0(n))
                ->best case complexity:O(n) (assignments=comparisons=total=0(n))
                -> stable (two objects with equal keys appear in the same order in sorted output as they
                appear in the input  array to be sorted)
                ->adaptive 0(n)-> O(n^2)

Selection_sort ->best,average and worst case complexity:O(n^2) (independent of distribution of data)
               ->best (assignments=0(1),comparisons=O(n^2))
               ->average (assignments=0(n),comparisons=O(n^2))
               ->worst (assignments=0(n),comparisons=O(n^2))
               -> stable (two objects with equal keys appear in the same order in sorted output as they
                  appear in the input  array to be sorted)---(min!=i)
               ->non-adaptive,because the order of the elements in the input array doesn't matter,time complexity
                 will be always O(n^2)

As can be seen from the graphs ->in case worst the selection_sort is  better
                               ->in case average the insertion_sort is better
                               ->in case best the bubble_sort is better
 */

#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <iostream>
#include "Profiler.h"
Profiler p("raport");

#define MAX_SIZE 10001
#define MAX_INPUT 10000
#define STEP_SIZE 100
#define NR_TESTS 5

int binarySearch (int myArray[],int length,int l,int r,int el,const char * compar)
{
    if(r>l)
    {
        int mid=(r+l)/2;
        if(myArray[mid]==el)
        {
            if (compar != NULL)
            {
                p.countOperation(compar, length);
            }
            return mid;
        }

        if(myArray[mid]>el)
        {
            if (compar != NULL)
            {
                p.countOperation(compar, length);
            }
            return binarySearch(myArray,length,l,mid-1,el,compar);
        }
        if(myArray[mid]<el)
        {
            if (*compar != NULL)
            {
                p.countOperation(compar, length);
            }
            return binarySearch(myArray,length,mid+1,r,el,compar);
        }
    }

    if (el>myArray[l])
    {
        if (*compar != NULL)
        {
            p.countOperation(compar, length);
        }
        return l+1;
    }
    else{
        if (*compar != NULL)
        {
            p.countOperation(compar, length);
        }

        return l;
    }
}

void insertionSort(int myArray[],int n, const char* comp_I, const char* attr_I){
    int i, x,j,sit;
    for( i=1;i<n;i++)
    {
        if (attr_I!= NULL)
        {
            p.countOperation(attr_I, n);
        }
        x=myArray[i];
        j=i-1;
        sit=binarySearch(myArray,n,0,j,x,comp_I);
        while(j>=sit)
        {
            if (attr_I!= NULL)
            {
                p.countOperation(attr_I, n);
            }
            myArray[j+1]=myArray[j];
            j--;
        }
        if (attr_I!= NULL)
        {
            p.countOperation(attr_I, n);
        }
        myArray[j+1]=x;
    }

}
void bubbleSort(int myArray[], int n,const char * comp_B, const char *attr_B)
{
    bool swapped;
    do {
        swapped = false;
        for (int i = 0; i < n - 1; i++)
        {
            if (comp_B != NULL)
            {
                p.countOperation(comp_B, n);
            }
            if (myArray[i] > myArray[i + 1])
            {
                if (attr_B != NULL)
                {
                    p.countOperation(attr_B, n, 3);
                }
                std::swap(myArray[i], myArray[i + 1]);
                swapped = true;
            }
        }
    } while (swapped == true);
}

void selectionSort(int myArray[],int n,const char * comp_S, const char *attr_S)
{
    for (int i = 0; i < n - 1; i++) 
    {
        int min = i;
        for (int j = i + 1; j < n; j++)
        {
            if (comp_S != NULL)
            {
                p.countOperation(comp_S, n);
            }
            if (myArray[min] > myArray[j])
                min = j;
        }
        if(min!=i)
        {
            if (attr_S != NULL)
            {
                p.countOperation(attr_S, n, 3);
            }
            std::swap(myArray[i], myArray[min]);
        }
    }
}


void measurementBest_B(int myArray[], const char* comparisonsBest_B, const char* assignmentsBest_B)
{
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(myArray, n, 0, 5000, false, ASCENDING);
        p.countOperation(comparisonsBest_B, n, 0);
        p.countOperation(assignmentsBest_B, n, 0);
        bubbleSort(myArray, n, comparisonsBest_B, assignmentsBest_B);
    }
}

void measurementBest_S(int myArray[], const char* comparisonsBest_S, const char* assignmentsBest_S)
{
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(myArray, n, 0, 5000, false, ASCENDING);
        p.countOperation(comparisonsBest_S, n, 0);
        p.countOperation(assignmentsBest_S, n, 0);
        selectionSort(myArray, n, comparisonsBest_S, assignmentsBest_S);
    }
}

void measurementBest_I(int myArray[], const char* comparisonsBest_I, const char* assignmentsBest_I)
{
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(myArray, n, 0, 5000, false, ASCENDING);
        p.countOperation(comparisonsBest_I, n, 0);
        p.countOperation(assignmentsBest_I, n, 0);
        insertionSort(myArray, n, comparisonsBest_I, assignmentsBest_I);
    }
}

void measurementAverage(int myArray_b[], const char* comparisonsAverage_B, const char* assignmentsAverage_B,
                        const char* comparisonsAverage_S, const char* assignmentsAverage_S,
                        const char* comparisonsAverage_I, const char* assignmentsAverage_I){

    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        p.countOperation(comparisonsAverage_B, n, 0);
        p.countOperation(assignmentsAverage_B, n, 0);
        p.countOperation(comparisonsAverage_S, n, 0);
        p.countOperation(assignmentsAverage_S, n, 0);
        p.countOperation(comparisonsAverage_I, n, 0);
        p.countOperation(assignmentsAverage_I, n, 0);
        for (int test = 0; test < NR_TESTS; test++)
        {
            FillRandomArray(myArray_b, n);
            int  myArray_s[MAX_INPUT], myArray_in[MAX_INPUT];
            for(int i=0;i<n;i++)
            {
                myArray_in[i]=myArray_b[i];
                myArray_s[i]=myArray_b[i];
            }
            bubbleSort(myArray_b, n, comparisonsAverage_B, assignmentsAverage_B);
            selectionSort(myArray_s, n, comparisonsAverage_S, assignmentsAverage_S);
            insertionSort(myArray_in, n, comparisonsAverage_I, assignmentsAverage_I);
        }
    }

    p.divideValues(comparisonsAverage_B, NR_TESTS);
    p.divideValues(assignmentsAverage_B, NR_TESTS);
    p.divideValues(comparisonsAverage_S, NR_TESTS);
    p.divideValues(assignmentsAverage_S, NR_TESTS);
    p.divideValues(comparisonsAverage_I, NR_TESTS);
    p.divideValues(assignmentsAverage_I, NR_TESTS);
}


void measurementWorst_B(int arrayB[], const char* comparisonsWorst_B, const char* assignmentsWorst_B)
{
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(arrayB, n, 0, 5000, false, DESCENDING);
        p.countOperation(comparisonsWorst_B, n, 0);
        p.countOperation(assignmentsWorst_B, n, 0);
        bubbleSort(arrayB, n, comparisonsWorst_B, assignmentsWorst_B);
    }
}

void measurementWorst_S(int arrayB[], const char* comparisonsWorst_S, const char* assignmentsWorst_S)
{
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(arrayB, n, 0, 5000, false, DESCENDING);
        p.countOperation(comparisonsWorst_S, n, 0);
        p.countOperation(assignmentsWorst_S, n, 0);
        selectionSort(arrayB, n, comparisonsWorst_S, assignmentsWorst_S);
    }
}
void measurementWorst_I(int arrayB[], const char* comparisonsWorst_I, const char* assignmentsWorst_I)
{
    for (int n = 0; n <= MAX_INPUT; n = n + STEP_SIZE) {
        FillRandomArray(arrayB, n, 0, 5000, false, DESCENDING);
        p.countOperation(comparisonsWorst_I, n, 0);
        p.countOperation(assignmentsWorst_I, n, 0);
        insertionSort(arrayB, n, comparisonsWorst_I, assignmentsWorst_I);
    }
}

void measurements(int myArray[]) {

    //Bubble_sort
    const char* comparisonsBest_B = "comparisonsBest_B";
    const char* assignmentsBest_B = "assignmentsBest_B";
    const char* comparisonsAverage_B = "comparisonsAverage_B";
    const char* assignmentsAverage_B = "assignmentsAverage_B";
    const char* comparisonsWorst_B = "comparisonsWorst_B";
    const char* assignmentsWorst_B = "assignmentsWorst_B";
    const char* bestTotal_B = "bestTotal_B";
    const char* averageTotal_B = "averageTotal_B";
    const char* worstTotal_B = "worstTotal_B";

    //Selection_sort
    const char* comparisonsBest_S = "comparisonsBest_S";
    const char* assignmentsBest_S = "assignmentsBest_S";
    const char* comparisonsAverage_S = "comparisonsAverage_S";
    const char* assignmentsAverage_S = "assignmentsAverage_S";
    const char* comparisonsWorst_S = "comparisonsWorst_S";
    const char* assignmentsWorst_S = "assignmentsWorst_S";
    const char* bestTotal_S = "bestTotal_S";
    const char* averageTotal_S = "averageTotal_S";
    const char* worstTotal_S = "worstTotal_S";

    //Insection_sort
    const char* comparisonsBest_I = "comparisonsBest_I";
    const char* assignmentsBest_I= "assignmentsBest_I";
    const char* comparisonsAverage_I = "comparisonsAverage_I";
    const char* assignmentsAverage_I = "assignmentsAverage_I";
    const char* comparisonsWorst_I = "comparisonsWorst_I";
    const char* assignmentsWorst_I= "assignmentsWorst_I";
    const char* bestTotal_I = "bestTotal_I";
    const char* averageTotal_I = "averageTotal_I";
    const char* worstTotal_I = "worstTotal_I";

    //FOR BEST
    p.reset("BEST");
    measurementBest_B(myArray, comparisonsBest_B, assignmentsBest_B);
    measurementBest_S(myArray, comparisonsBest_S, assignmentsBest_S);
    measurementBest_I(myArray, comparisonsBest_I, assignmentsBest_I);

    p.addSeries(bestTotal_B, assignmentsBest_B, comparisonsBest_B);
    p.addSeries(bestTotal_S, assignmentsBest_S, comparisonsBest_S);
    p.addSeries(bestTotal_I, assignmentsBest_I, comparisonsBest_I);

    p.createGroup("COMPARISONS", comparisonsBest_B, comparisonsBest_S, comparisonsBest_I);
    p.createGroup("ASSIGNMENTS", assignmentsBest_B, assignmentsBest_S, assignmentsBest_I);
    p.createGroup("TOTAL", bestTotal_B, bestTotal_S,bestTotal_I);
    p.createGroup("COMP-INSERTION-BUBBLE",comparisonsBest_B,comparisonsBest_I);
    p.createGroup("TOTAL-INSERTION-BUBBLE",bestTotal_B,bestTotal_I);
    p.showReport();


    //FOR AVERAGE
    p.reset("AVERAGE");
    measurementAverage(myArray, comparisonsAverage_B, assignmentsAverage_B,comparisonsAverage_S, assignmentsAverage_S,
                       comparisonsAverage_I, assignmentsAverage_I);

    p.addSeries(averageTotal_B, assignmentsAverage_B, comparisonsAverage_B);
    p.addSeries(averageTotal_S, assignmentsAverage_S, comparisonsAverage_S);
    p.addSeries(averageTotal_I, assignmentsAverage_I, comparisonsAverage_I);

    p.createGroup("COMPARISONS",comparisonsAverage_B,comparisonsAverage_S,comparisonsAverage_I);
    p.createGroup("ASSIGNMENTS",assignmentsAverage_B,assignmentsAverage_S,assignmentsAverage_I);
    p.createGroup("TOTAL",averageTotal_B,averageTotal_S,averageTotal_I);
    p.createGroup("ASSIGNMENTS-SELECTION",assignmentsAverage_S);
    p.createGroup("COMPARASIONS-INSERTION",comparisonsAverage_I);
    p.showReport();


    //FOR WORST
    p.reset("WORST");
    measurementWorst_B(myArray, comparisonsWorst_B, assignmentsWorst_B);
    measurementWorst_S(myArray, comparisonsWorst_S, assignmentsWorst_S);
    measurementWorst_I(myArray, comparisonsWorst_I, assignmentsWorst_I);

    p.addSeries(worstTotal_B, assignmentsWorst_B, comparisonsWorst_B);
    p.addSeries(worstTotal_S, assignmentsWorst_S, comparisonsWorst_S);
    p.addSeries(worstTotal_I, assignmentsWorst_I, comparisonsWorst_I);

    p.createGroup("COMPARISONS",comparisonsWorst_B,comparisonsWorst_S,comparisonsWorst_I);
    p.createGroup("ASSIGNMENTS",assignmentsWorst_B,assignmentsWorst_S,assignmentsWorst_I);
    p.createGroup("TOTAL",worstTotal_B,worstTotal_S,worstTotal_I);
    p.createGroup("ASSIGNMENTS-SELECTION",assignmentsWorst_S);
    p.createGroup("COMPARISONS-INSERTION",comparisonsWorst_I);
    p.showReport();

}


void test_array(int array[], int n, int* pass, int* fails) {
    bool v = true;
    for (int i = 0; i <= n - 2; i++)
    {
        if (array[i] > array[i + 1])
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

void demo(){
    int n=10;
    int array[MAX_SIZE];
    //generam un vector oarecare
    FillRandomArray(array, n, 0, 5000, false, UNSORTED);
    for (int i = 0; i < n; i++)
    {
        printf("%d ", array[i]);
    }
    printf("\n");
    //ALEGEM CE ALGORITM DORIM SA-L VERIFICAM
    insertionSort(array, n, NULL, NULL);
    for (int i = 0; i < n; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");
    printf("Done!\n");
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
        selectionSort(array, n,NULL,NULL);
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

