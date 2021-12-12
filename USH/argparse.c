/*
CS347 Argparser for mini shell
Author: David Zaslavsky
*/
#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdbool.h> 
#include <ctype.h>
#include "argparse.h"
/*CONSTANTS*/
typedef
struct node_stack{
    struct node_stack *next;
    char *cmd;
} node, *stack;
#define EMPTY NULL
stack cmds = EMPTY;
stack.push(&cmds, l);
void stack_push(node *stk, char *cmd);
char *stack_pop(stack *stk);
/*PROTOTYPES*/
/*
*Arg counter:
*  counts the number of args in the line accounting for 
*  quotations and returns the size
*/
static size_t arg_counter(char *line);
/*
*Quote delete:
*  takes in the input line and the index of the quote
*  and scoots the whole line left by 1 going overriding
*  the quote
*/
static char* quoteDelete(char *line, int index);
/*
*expand:
* expands any environment variables in given line, keeps orig the same
* and puts the new expanded line in new
* RETURNS: 0 on fail, 1 on success
*/
int expand(char* orig, char* new, int newsize);
/*
*dontExpand:
* will not expand any environment variables in the case you
* want to name a variable with the ${} format 
* RETURNS: 1 if we dont expand, 0 if we do
*/
static int dontExpand(char *line);
/*
*char_append:
*  takes 2 char*'s and appends the str to the dest at the given index
*  RETURNS: the dest
*/
static char *char_append(char *dest, char *str, int index);
/*
*numDigits:
* takes an int and calculates how many digits are in it
* RETURNS: num digits in the int
*/
static int numDigits(int i);
/*
*Quote count:
*  counts the number of quotes in the input line
*  RETURN: num quotes as int 
*/
static int quoteCount(char *line);
/*  
*      STATE MACHINE USED FOR ARGPARSE AND ARGCOUNTER 
*          +-------------------"------------------------+
*          |                                            |
*          |                                            v
*          |                               +---"---->----------spc,!spc
*          |                               |         | QUO |      |
*          |                               |         +------<-----+
*          |                               |         |
*          |                               |         |
*  +---------------------!spc------->-------<---"---+
*spc   | BET |                       | ARG |
*  +-->-------<-----------spc--------------+
*                                    |     ^
*                                    |     |
*                                    +-!spc+               
*/
/*
*Arg parse:
*  parses thru the input line and finds all the arguements 
*  accounting for quotes and deleting them.  Then saves all 
*  valid args in an arg array and returns that as output
*  also saves the size of the arg array  
*  NOTE:
*  bool scooted is checking to see if we scooted the line and make adjustments
*/
char** argparse(char *line, int *argcp){
  assert(line != NULL);

  *argcp = arg_counter(line);
  int quoteCnt = quoteCount(line);
  
  if(quoteCnt%2 == 1)
    *argcp = 0;
  
  char** argArr = (char**)malloc(sizeof(char*)*(*argcp + 1));   
  
  int k = 0;
  int i = 0;
  enum {BET, ARG, QUO} state = BET;
  int arg_index_holder = -1;
  
  while(line[i] != '\0'){
    bool scooted = false;
    
    if(state == BET){   
      arg_index_holder = -1;
      if(line[i] == '\"'){
        arg_index_holder = i;
        line = quoteDelete(line,i);
        scooted = true;
        state = QUO;

      }else if(!isspace(line[i])){
        argArr[k] = &line[i];
        k++;
        state = ARG;
      }

    }else if(state == ARG){
      if(line[i] == '\"'){
        line = quoteDelete(line,i);
        scooted = true;
        state = QUO;

      }else if(isspace(line[i])){
        if(arg_index_holder != -1){
          argArr[k] = &line[arg_index_holder];
          k++;
        }
        line[i] = '\0';
        state = BET;
      } 

    }else if(state == QUO){
      if(line[i] == '\"'){
        line = quoteDelete(line,i);
        scooted = true;
        state = ARG;

      }  
    }
    if(!scooted)
      i++;
  }
  if(state == ARG && arg_index_holder != -1){
    argArr[k] = &line[arg_index_holder];
    k++;
  }
  argArr[k] = NULL;   
  return argArr;
}

/*
*expand:
*  adds environment variable expansion 
*
*
*
*                              STATE MACHINE FOR EXPAND
*        +----!(${)-+
*        |          |
*        |          |
*        |          v
*+------------------------------${------------>+----------+---------+
*|       |          |                          |          |         |
*|       |          |                          |          |         !(})
*$$      |   ARG    |                          |    ENV   |         |
*|       |          |                          |          |         |
*+------>+----------+<----------}--------------+----------+<--------+
*/
int expand(char *orig, char *new, int newsize){
  enum {ARG, ENV} state = ARG;
  int i = 0;
  int j = 0;
  int x = 0;
  int rtrn = 1;
  int xpnd = dontExpand(orig);
  char env_var[1024] = {'\0'};

  if(xpnd == 1)
    new = char_append(new,orig,0);
  
  while(orig[i] != '\0' && xpnd == 0){   
    if(j >= 1023){
      fprintf(stderr,"%s\n","ERROR: BUFFER OVERFLOW");
      new = NULL;
      rtrn = 0;
    } 
    if(state == ARG){   
      if(orig[i] == '$' && orig[i+1] == '{'){
        state = ENV;
        i = i + 2;
        x = 0;
      }else if(orig[i] == '$' && orig[i+1] == '$'){    
        int pid = getpid();
        char pid_char[100] = {'\0'};
        sprintf(pid_char,"%d",pid); 
        new = char_append(new,pid_char,j);                                  
        i = i + 2; 
        j = j + numDigits(pid);    
      }else{
        new[j] = orig[i];
        i++;
        j++;
      }    

    }else if(state == ENV){     
      if(orig[i] == '}'){
        state = ARG;
        char *expanded_var = getenv(env_var);
        if(expanded_var != NULL){                    
          new = char_append(new, expanded_var,j);         
          j = strlen(new); 
        }
        i++; 
      }else{
        env_var[x] = orig[i];
        i++;
        x++;
      }
    }
  }  
  if(state == ENV){
    fprintf(stderr,"%s\n","ERR: forgot to close your env variable with a \"}\"");              
    rtrn = 0;
  } 
  return rtrn;
}
/*
*dontExpand:
* 
*/
static int dontExpand(char *line){
  int envsetFlag = 0;
  char envst[21] = {'\0'}; 
  int i = 0;    
  int j = 0;
  while(isspace(line[i])){ 
    i++;
  }
  if(line[i] == 'e'){
    while(!isspace(line[i])){  
      envst[j] = line[i];
      j++;
      i++;
    }
  }    
  if(strcmp(envst,"envset") == 0 || strcmp(envst,"envunset") == 0)
    envsetFlag++;
  return envsetFlag;
}
/*
*char_append:
*  takes 2 char pointers, and an index. it appends
*  the str to the line at the given index then returns the line
*/
static char *char_append(char *dest, char *str, int index){  
  int i = 0;
  while(str[i] != '\0'){
    dest[index] = str[i];
    i++;
    index++;
  }
  return dest;
} 
/*
*numDigits:
*  takes an integer and counts how many digits are in that integer
*  and returns that number
*/
static int numDigits(int i){
  int count = 0;
  while(i != 0){
    i /= 10;
    ++count;
  }
  return count;
}
/*  
*quote delete:
*/
static char* quoteDelete(char *line, int index){   
  while(line[index] != '\0'){
    line[index] = line[index + 1];
    index++;
  }
  return line;
}
/*   
*quote count:
*/
static int quoteCount(char *line){
int i = 0;
int quote_count = 0;
while(line[i] != '\0'){
  if(line[i] == '\"')
    quote_count++;
  i++;
}
if(quote_count%2 == 1){
  fprintf(stderr,"%s\n","ERR: ODD NUMBER OF QUOTES");
}
return quote_count;
}
/*   
*arg counter:
*/
static size_t arg_counter(char *line){
  assert(line != NULL && " input is null");

  int arg_count = 0;
  int i = 0;
  enum {BET, ARG, QUO} state = BET;

  while(line[i] != '\0'){      
    if(!isspace(line[i]) && state == BET && line[i] != '\"'){
      arg_count++; 
      state = ARG; 
    }else if(line[i] == '\"' && state == BET){
      arg_count++;
      state = QUO;
    }else if(isspace(line[i]) && state == ARG){
      state = BET;
    }else if(line[i] == '\"' && state == ARG){
      state = QUO;
    }else if(line[i] == '\"' && state == QUO){
      state = ARG;
    }     
    i++;
  }
  return arg_count;  
}
/*
*stack_push:
*
*/
void stack_push(node *stk, char *cmd){
    assert(stk != NULL);
    node *n = malloc(sizeof(node));
    n -> next = *stk;
    n -> cmd = cmd;
    *stk = n;
}
/*
*stack_pop:
*
*/
char *stack_pop(stack *stk){
    assert(stk != NULL);
    node *n = *stk;
    *stk = n -> next;
    char *cmd = n -> cmd;
    free(n);
    return cmd;
}