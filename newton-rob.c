//Newtons Method implemented in c
//Christopher Hoffman - Nov 6th, 2011

//compile with:
//gcc newtonsMethod.c -lrt -03
//(link to realtime timer library, gcc optimization level 3)

#include <math.h>
#include <time.h>
#include <stdio.h>

#define NUMVALS 100000

double f(double x)
{
    return pow(x, 2.0) - 1.5;
}

double fp(double x)
{
    return 2*x;
}

int newtonsMethod(double tol, double x0)
{
    unsigned int iter=0;
    while( fabs(f(x0)) >= tol )
    {
        ++iter;
        x0 = x0 - f(x0)/fp(x0);
    }
    return iter;
}

int main()
{
    printf("Newton's method\n");

    int j;
    for(j=0; j<10; j++) {
        struct timespec start, end;
        clock_gettime(CLOCK_REALTIME, &start);

        int i;
        int iters[NUMVALS];
        for(i=1; i<NUMVALS+1; ++i)
            iters[i-1] = newtonsMethod(pow(10.0, -14.0), (double)i);

        clock_gettime(CLOCK_REALTIME, &end);
        unsigned long ns = end.tv_nsec - start.tv_nsec;
        printf("Time elapsed: %f milliseconds\n", (double)ns*pow(10.0, -6.0));
    }

    return 0;
}
