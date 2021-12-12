/*

*/
#ifndef _ARGPARSE_H_  
#define _ARGPARSE_H_
/*
*argparse:
*   takes a char * called line and finds all the args 
*   and places them in a char** array.  saves the size
*   of line in argcp
*/
char **argparse(char *line, int *argcp);
/*
*expand:
*   expands any environment variables and returns the new 
*   expanded line
*   
*   RETURNS:  1 on success, 0 upon failure
*/
int expand(char *orig, char *new, int newsize);

#endif 