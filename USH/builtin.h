/*

*/
#ifndef _BUILTIN_H_  
#define _BUILTIN_H_
/*
*builtins:
*  checks if the input line contains any built in functions
*  then calls that function
*  RETURNS: 1 if a built in was found
*          -1 if a built in was not found
*/
int builtins(char **args, int *argcp);

#endif 