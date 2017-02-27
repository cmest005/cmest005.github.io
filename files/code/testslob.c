#include <stdio.h>
#include <stdlib.h>
#include <linux/unistd.h>

#define __NR_get_slob_amt_claimed 341
#define __NR_get_slob_amt_free 342

int main(int argc, char **argv){

	int n = 1;
	void* garbage;

	if(argc > 1){
		n = atoi(argv[1]);
	}
	
	printf("Allocating %d time!\n", n);
	int i;
	for(i = 0; i < n; i++){
		garbage = malloc(sizeof(size_t)*8);
	}
	printf("Claimed: %li (bytes)\n", syscall(__NR_get_slob_amt_claimed));
	printf("Free: %li (bytes)\n", syscall(__NR_get_slob_amt_free));

	return 0;
}
