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
#include "builtin.h"
/*PROTOTYPES*/
/*
*builtInExit:
*  built in exit function that exits the ushell with
*  the requested value
*/
static void builtInExit(char *args[]);
/*
*builtInAecho:
*  checks for -n flag then echo's the args with or
*  without an extra space whether the -n flag is 
*  activated or not
*/
static void builtInAecho(char *args[]);
/*
*builtInCD:
*  built in function that cd's into a directory
*  specified by the user.
*    returns ERR if directory not valid
*/
static void builtInCD(char *args[]);
/*
*builtInEnvset:
*  built-in command that will set the value of 
*  a environment variable
*/
static void builtinEnvset(char *args[]);
/*
*builtinEnvunset:
*  built in command that will clear set the value of
*  an environment variable
*/
static void builtinEnvunset(char *args[]);
/*CONSTANTS*/
typedef void (*builtin_t)(char *args[]);
static builtin_t builtIns[] = {&builtInExit, &builtInAecho, &builtInCD, &builtinEnvset, &builtinEnvunset, NULL};
static char *builtInArgs[] = {"exit", "aecho", "cd", "envset", "envunset", NULL}; 
/*
*builtins:
*  checks if the input line contains any built in functions
*  then calls that function
*  RETURNS: 1 if a built in was found
*          -1 if a built in was not found
*/
int builtins(char **args, int *argcp){
  assert(args != NULL);
  int i = 0;
  while(builtInArgs[i] != NULL && strcmp(args[0],builtInArgs[i]) != 0){
    ++i;
  }  
  if(builtInArgs[i] != NULL)
    builtIns[i](args);
  return builtInArgs[i] != NULL ? 1 : -1;
}
/*
*builtInExit:
*/
static void builtInExit(char *args[]){
  if(args[1] == NULL){
    exit(0);
  }else{
    int exit_value = atoi(args[1]);
    exit(exit_value);       
  }              
}
/*
*builtInAecho:
*/
static void builtInAecho(char *args[]){
  int i = 1;
  int nFlag = 0;
  if(args[1] != NULL){
    if(strcmp(args[1],"-n") == 0)
      nFlag = 1;           
    if(nFlag == 1)
      i++; 
    dprintf(1,"%s",args[i]);
    i++;
    while(args[i] != NULL){
      dprintf(1," %s",args[i]);
      i++;
    }    
    if(nFlag == 0)
      dprintf(1, "\n");
  }
}
/*
*builtInCD
*/
static void builtInCD(char *args[]){
  int change_dir; 
  if(args[1] != NULL){
    change_dir = chdir(args[1]);
  }else{
    change_dir = chdir(getenv("HOME"));
  }
  if(change_dir == -1)
    fprintf(stderr,"%s\n","ERROR: not a valid directory to cd into");
}
/*
*builtInEnvset
*/
static void builtinEnvset(char *args[]){
  int err = -1;
  if(args[1] != NULL && args[2] != NULL)
    err = setenv(args[1],args[2],1);   
  if(err == -1)
    fprintf(stderr,"%s\n","ERROR: envset failed");
}
/*
*builtinEnvunset
*/
static void builtinEnvunset(char *args[]){
  int err = -1;
  if(args[1] != NULL)
    err = unsetenv(args[1]);
  if(err == -1)
    fprintf(stderr,"%s\n","ERROR: envunset failed");
}