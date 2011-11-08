//Newtons Method implemented in c
//Christopher Hoffman - Nov 6th, 2011

//compile with:
//gcc newtonsMethod.c -lrt -03
//(link to realtime timer library, gcc optimization level 3)

#include <math.h>

double f(double x)
{
    return pow(x, 2.0) - 1.5;
}

double fp(double x)
{
    return 2*x;
}

int newtonsMethod( double tol, double x0)
{
    unsigned int iter=0;
    while( fabs(f(x0)) >= tol )
    {
        ++iter;
        x0 = x0 - f(x0)/fp(x0);
    }
    return iter;
}

#include <time.h>
#include <stdio.h>

#define NUMVALS 10000

int main()
{
    printf("newtonsMethod\n");

    //get timing stuff
    struct timespec t0, t1;
    clock_gettime(CLOCK_REALTIME, &t0);

    //do newtons method for x0 = 1 - 1000
    int i;
    int iters[NUMVALS];
    for(i=i; i<NUMVALS+1; ++i)
        iters[i-1] = newtonsMethod(pow(10.0, -14.0), (double)i);

    //print timing stuff
    clock_gettime(CLOCK_REALTIME, &t1);
    unsigned long ns = t1.tv_nsec-t0.tv_nsec;
    printf("nsec elapsed: %lu\n", ns);
    printf("Time elapsed: %f miliseconds\n", (double)ns*pow(10.0, -6.0));

    //print number of iterations each x0 took
    //for(i=0; i<NUMVALS; ++i)
    //{
    //    printf("%i ", iters[i]);
    //    if( i % 20 == 0)
    //        printf("\n");
    //}
    //printf("\n");

    return 0;
}
