/*-------------------------------
Carlos Mestre
1335543
cmest005@fiu.edu
Lab 2, part 2
-------------------------------*/


/* header files */
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <errno.h>
#include <omp.h>
#include <semaphore.h>
#include <pthread.h>


/* global variables */
int studentCount = 0;
int officeCapacity = 0;
int io = 0;
int leave = false;
sem_t prof;
sem_t stud;
sem_t sati;
omp_lock_t student;
omp_lock_t enqueue;
pthread_barrier_t start;
pthread_t professor;

/* this struct represents the student which will enter the office.  They will each have their oown variables which represent a student id, their number of questions, an int to test whether they are in the office, and a pointer to the next student */
struct student{
	int id;
	int nq;
	int atOffice;
	struct student *next;
};

/* pointers to the queue setup */
struct student *tail = NULL;
struct student *head = NULL;
struct student *count = NULL;
struct student *prev = NULL;

/* function signatures */
void* StudentAssist(void * sdt);
void* PHelper();


/* Student function which creates a student with an ID and questions based on the amount of students in line. */
void Student(int id){
	struct student *student_node = malloc(sizeof(*student_node));
	
	student_node->atOffice = false;
	student_node->id = id;
	student_node->nq = id % 4 + 1;

	pthread_t sdt;
	if(pthread_create(&sdt, NULL, StudentAssist, (void*)student_node)!=0){
		perror("Error:  student thread failed");
		exit(0);
	}
}

/* StudentAssist is a function called from the pthread_create inside the Student function.  It handles the logic of the students entering the office, asking and getting questions answered, and then finally leaving the office */
void* StudentAssist(void * sdt){
	struct student * aStudent = sdt;
	
	omp_set_lock(&enqueue);

	if(head != NULL)
		tail = tail->next = aStudent;
	else
		tail = prev = count = head = aStudent;

	omp_unset_lock(&enqueue);
	pthread_barrier_wait(&start);

	while(count != NULL){
		omp_set_lock(&student);
		EnterOffice();
		if(count != NULL && count->nq > 0){
			QuestionStart();
			QuestionDone();
		}	

		if(count != NULL && count->nq == 0)
			LeaveOffice();

		sem_post(&sati);	

		if(leave)
			leave = false;
		else if(count != NULL){
			if(count->next != NULL){
				prev = count;
				count = count->next;
			}
			else
				count = head;
		}
		omp_unset_lock(&student);
	}	
	pthread_exit(NULL);
}

/* QuestionStart is a function which prints out which student is asking the question */
void QuestionStart(){
	printf("Student %i asks a question.\n", count->id);
	sem_post(&prof);
}

/* QuestionDone alerts when a certain student finishes with their question */
void QuestionDone(){
	sem_wait(&stud);
	count->nq--;
	printf("Student %i is satisfied.\n", count->id);
}

/* EnterOffice which tests if the office is full based on capacity and increment the count if it isnt full */
void EnterOffice(){
	if(count != NULL && !count->atOffice){
		if(io < officeCapacity){
			io++;
			count->atOffice = true;
			printf("Student %i enters the office.\n", count->id);
		}
		else
			count = head;
	}
}

/* LeaveOffice handles the logic of leaving the office and decrimenting the counters. */
void LeaveOffice(){
	--io;
	--studentCount;
	printf("Student %i leaves the office.\n", count->id);

	if(studentCount == 0 && count->next == NULL){
		count = NULL;
		leave = true;
	}
	
	else if(head == count && count->next != NULL){
		head = count->next;
		count = head;
		leave = true;
	}
	else if(count->next != NULL && prev->next != NULL){
		prev->next = count->next;
		count = prev->next;
		leave = true;
	}
	else if(count->next == NULL){
		prev->next = count->next;
		count = head;
		leave = true;
	}
}

/* setOC sets the capacity of the office to a global variable */
void setOC(int oc){
	officeCapacity = oc;
}

/* setSC sets the amont of students visiting the office to a global variable */
void setSC(int sc){
	studentCount = sc;
}

/* Professor function which creates the locks, semaphores, barriers, and thread creation */
void Professor(){
	omp_init_lock (&student);
	omp_init_lock (&enqueue);
	
	sem_init(&prof, 0, 0);
	sem_init(&stud, 0, 0);
	sem_init(&sati, 0, 0);

	pthread_barrier_init(&start, NULL, studentCount);
	
	if(pthread_create(&professor, NULL, PHelper, NULL) !=0){
		perror("Error: Professor thread failed");
		exit(0);
	}
}

/* PHelper is a function which is called from the pthread_create in the Professor function and it deals with the answering to the questions and whether there are any questions left based on student count */
void* PHelper(){
	while(count != NULL || studentCount > 0){
		AnswerStart();
		AnswerDone();

		if(studentCount == 0)
			printf("Professor has finished answering all questions");
	}
}

/* the printout on the professor answering a question */
void AnswerStart(){
	sem_wait(&prof);
	printf("The professor starts to answer question for student %i.\n", count->id);
}

/* the printout on the professor when he has finished answering all questions */
void AnswerDone(){
	printf("The professor is done with answer for student %i.\n", count->id);
	sem_post(&stud);
	sem_wait(&sati);
}

/* the main function which sets the number of students and office capacity based on user input */
int main(int argc, char* argv[]){

	int sc = 0;
	int tsc = 0;
	int oc = 0; 
	int toc = 0;
	int t;
	
	if(argc == 3){
		tsc = sscanf(argv[1], "%i", &sc);
		toc = sscanf(argv[2], "%i", &oc);
		
	omp_set_num_threads(sc);

	if(tsc != 1 && toc != 1){
		printf("Invalid input");
		return 0;
	}
	
	setSC(sc);
	setOC(oc);
	Professor();

	#pragma omp parrallel for private(t)
	{
		for(t = 0; t < sc; t++){
			Student(t);
		}
	}
		pthread_join(professor, NULL);
	}
	else {
printf("Sorry, program failed");
}
omp_destroy_lock(&student);
omp_destroy_lock(&enqueue);
return 0;
}

