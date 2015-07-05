#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*struct ArrWrapper { int values[1]; };

void ByPtr(int *arr) {
	//printf("Before: %d\n", arr[0]);
	arr[0] = 1;
	//printf("After: %d\n", arr[0]);
}

void ByVal(struct ArrWrapper arr) {
	//printf("Before: %d\n", arr.values[1]);
	arr.values[0] = 1;
	//printf("After: %d\n", arr.values[1]);
}

void void_func() {

}*/

void check_int_to_bool(int test) {
	if(test != 0 && test != 1) { // Not a boolean value
		printf("Runtime error -> Attempted to convert integer '%d' to boolean.", test);
		exit(-1);
	}
}

void testfunc(int value, int *x) {
	*x = value;
}

void teststrarrayptr(char *svars[]) {
	char *newstr = (char *) malloc(100);
	//printf("next: ");
	//gets(newstr);
	sscanf(fgets(newstr, sizeof(char) * 100, stdin), "%[a-zA-Z0-9 _,;:.']\n", newstr);
	svars[1] = newstr;
	printf("%s\n", svars[1]);
}

int main () {
	/*int x[1] = { 0 };
	struct ArrWrapper arr;
	*arr.values = *x;
	
	test_func(5);
	
	ByVal(arr);
	printf("Check pass by value: %d\n", x[0]);
	ByPtr(x);
	printf("Check pass by value: %d\n", x[0]);
	
	printf("Press any key to continue...");
	getchar();*/
	
	//int result = 0;
	//testfunc(5, &result);
	
	//int result;
	//testfunc(5, &result);
	
	/*
	int bvar;
	int ivar;
	float fvar;
	char *teststr = "test";
	
	char svar [100];
	printf("Enter bool (1 or 0): ");
		scanf("%d", &bvar);
		check_int_to_bool(bvar);
	printf("Enter integer: ");
		scanf("%d", &ivar);
	printf("Enter float: ");
		scanf("%f", &fvar);
	printf("Enter string: ");
		gets(svar);
		sscanf(fgets(svar, sizeof(svar), stdin), "%[a-zA-Z0-9 _,;:.']\n", svar);
	
	printf("%i\n", bvar);
	printf("%i\n", ivar);
	printf("%f\n", fvar);
	printf("string: %s\n", svar);
	
	*/
	/*int intarr[5];
	testfunc(7, &intarr[0], intarr);
	printf("%i %i\n", intarr[0], intarr[1]);*/
	
	/*char *emptystr = "";
	char *str1 = "test1";
	char *str2 = "test2";
	emptystr = str1;
	emptystr = str2;
	
	printf("%i\n", 7);
	
	char *svars[5];
	
	char teststr[] = "test string";
	strcpy(teststr, "test1");
	printf("%s\n", teststr);
	
	char *newstr = (char *) malloc(100);
	//printf("next: ");
	//gets(newstr);
	sscanf(fgets(newstr, sizeof(char) * 100, stdin), "%[a-zA-Z0-9 _,;:.']\n", newstr);
	svars[1] = newstr;
	printf("%s\n", svars[1]);*/
	
	float ftest = 5.6;
	
	char *teststr2 = "test2";
	char *teststr3 = "test1";
	printf("%s\n", teststr2);
	teststr2 = teststr3;
	printf("%s\n", "testtest");
	
	char *newstr = (char *) malloc(100);
	sscanf(fgets(newstr, sizeof(char) * 100, stdin), "%[a-zA-Z0-9 _,;:.']\n", newstr);
	printf("%s\n", newstr);
	
	/*char *true = "true";
	char *false = "false";
	printf("%s\n", true);
	printf("%s\n", false);
	
	int iarr[10];
	testfunc(5, &iarr[1]);
	
	float fvar;
	printf("Enter float: ");
		scanf("%f", &fvar);
	
	printf("%f\n", fvar);*/
	
	/*intc = inta + intb;
	intc = inta - intb;
	intc = inta * intb;
	intc = inta / intb;
	
	floatc = floata + floatb;
	floatc = floata - floatb;
	floatc = floata * floatb;
	floatc = floata / floatb;
	
	check_int_to_bool(inta);
	
	intc = inta && intb;*/

	return 0;
}






