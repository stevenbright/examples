int isPrime( int num ) {
        int count = 0;
        int i = 1;
        for( ; i <= num ; ++i ) {
                if( num % i == 0 ) ++count;
        }
        return 2 == count;
}

#define COUNT 5000

#include <stdio.h>

int main( void ) {
        int count = 0;
        int num = 1;
        int sum = 0;
        for( ; count < COUNT ; ++num ) {
                if( isPrime( num ) ) {
                        sum += num;
                        ++count;
                }
        }
        printf( "%d\n" , sum );
        return 0;
}

