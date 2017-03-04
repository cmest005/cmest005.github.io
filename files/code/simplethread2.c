/*------------------------------
Carlos Mestre
1335543
cmest005@fiu.edu
Lab 2, part 1.2
------------------------------*/


/* Header files */
#include <string.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdio.h>
#include <pthread.h>


/* global variables */
int SharedVariable = 0;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_barrier_t barrier;


/* valid is a helper method from getValid() which tests to make sure the input passed is a number between 0 and 9. */
int valid(char *string){
	int i; int length = strlen(string);

	for(i = 0; i < length; i++){
		if((string[i] <48) || (string[i] > 57))
		return 0;
	}
	return 1;
}

/* getValid is method which asks the user for a valid number input and keeps asking as long as the user does not enter valid data */
int getValid(){
	int n;
	char input[200];

	do{
		printf("\nEnter valid number: ");
		scanf("%s", &input);
	}while(!valid(input));

	n = atoi(input);
	return n;
}

/* runSimpleThread2 is a method called from the creation of a pthread which converts the input into an int and sends it to the Simplethread2 method for processing */
void *runSimpleThread2( void *n){
	int num = (intptr_t)n;
	SimpleThread2(num);
}

/* simpleThread2 is a method which acts as a thread and uses the global variable SharedVariable to increment 20 times and show a final total per thread*/
void SimpleThread2(int w){
	int num, value;

	for(num = 0; num < 20 ; num++){
		if(random() > RAND_MAX / 2)
			usleep(10);
	
//	#ifdef PTHREAD_SYNC
	pthread_mutex_lock(&mutex);
//	#endif
		value = SharedVariable;
		printf("*** thread %d sees value %d\n", w, value);
		SharedVariable = value + 1;
//	#ifdef PTHREAD_SYNC
	pthread_mutex_unlock(&mutex);
//	#endif
	}
//	#ifdef PTHREAD_SYNC
	pthread_barrier_wait(&barrier);
	pthread_mutex_lock(&mutex);
//	#endif	
		value = SharedVariable;
//	#ifdef PTHREAD_SYNC
	pthread_mutex_unlock(&mutex);
//	#endif
	printf("Thead %d sees final value %d\n", w, value);
}

/* main class which handles the creation of the threads */
int main(int argc, char *argv[]){

	int nthreads, i, c;

	if(argc != 2 || !valid(argv[1]))
		nthreads = getValid();
	else
		nthreads = atoi(argv[1]);

	pthread_t threads[nthreads];
	
//	#ifdef PTHREAD_SYNC
	if(pthread_barrier_init(&barrier, NULL, nthreads)){
		printf("barrier failed\n");
		return -1;
	}
//	#endif
	
	for( i = 0; i < nthreads; i++ ){
		c = pthread_create( &threads[i], NULL, runSimpleThread2, (void*)(intptr_t)i );
}

	for( i =0; i < nthreads; ++i){
		if(pthread_join(threads[i],NULL)){
			printf("Join thread failed %d\n", i);
			return -1;
		}
	}

	pthread_exit(NULL);
//	#ifdef PTHREAD_SYNC
	pthread_mutex_destroy(&mutex);
//	#endif
	return 0;
}
