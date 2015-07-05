define void @_11multiply(i32 %_9value.p, i32* %_10var.p) {
entry:
  %_9value = alloca i32, align 4
  store i32 %_9value.p, i32* %_9value, align 4
  %_10var = alloca i32*, align 4
  store i32* %_10var.p, i32** %_10var, align 4
  ;instance of Destination and not array item ptr, idxTemp: 0
  %0 = load i32* %_9value, align 4
  ;instance of Destination and not array item ptr, idxTemp: 1
  %1 = load i32* %_9value, align 4
  %mul0 = mul nsw i32 %0, %1
  ;calculate result 2
  ;calculate int 2
  %2 = load i32** %_10var, align 4
  store i32 %mul0, i32* %2, align 4


  br label %return.stmt

return.stmt:
  ret void
}

define void @_14teststrproc(i8* %_12strin.p, i8** %_13strout.p) {
entry:
  %_12strin = alloca i8*, align 4
  store i8* %_12strin.p, i8** %_12strin, align 4
  %_13strout = alloca i8**, align 4
  store i8** %_13strout.p, i8*** %_13strout, align 4
  %0 = load i8** %_12strin, align 4
  %call0 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %0)
  %1 = getelementptr inbounds [9 x i8]* @.str1, i32 0, i32 0
  %2 = load i8*** %_13strout, align 4
  store i8* %1, i8** %2, align 4
  %3 = load i8*** %_13strout, align 4
  %4 = load i8** %3, align 4
  %call1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %4)


  br label %return.stmt

return.stmt:
  ret void
}

define void @_16testgetstr(i8** %_15getstr.p) {
entry:
  %_15getstr = alloca i8**, align 4
  store i8** %_15getstr.p, i8*** %_15getstr, align 4
  %call0 = call i8* @malloc(i32 100)


  br label %return.stmt

return.stmt:
  ret void
}

@.str0 = private unnamed_addr constant [63 x i8] c"Runtime error -> Attempted to convert integer '%d' to boolean.\00", align 1
@.str_empty = private unnamed_addr constant [1 x i8] c"\00", align 1
@.str_getbool = private unnamed_addr constant [3 x i8] c"%i\00", align 1
@.str_getint = private unnamed_addr constant [3 x i8] c"%i\00", align 1
@.str_getfloat = private unnamed_addr constant [3 x i8] c"%f\00", align 1
@.str_getstring = private unnamed_addr constant [3 x i8] c"%s\00", align 1
@.str_putbool = private unnamed_addr constant [4 x i8] c"%i\0A\00", align 1
@.str_putint = private unnamed_addr constant [4 x i8] c"%i\0A\00", align 1
@.str_putfloat = private unnamed_addr constant [4 x i8] c"%f\0A\00", align 1
@.str_putstring = private unnamed_addr constant [4 x i8] c"%s\0A\00", align 1
@.str_sscanfqualify = private unnamed_addr constant [21 x i8] c"%[a-zA-Z0-9 _,;:.']\0A\00", align 1
@_imp___iob = external global [0 x %struct._iobuf]*

declare i8* @malloc(i32)

declare i8* @strcpy(i8*, i8*)

declare i32 @scanf(i8*, ...)

declare i8* @gets(i8*)

declare i32 @sscanf(i8*, i8*, ...)

%struct._iobuf = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }

declare i8* @fgets(i8*, i32, %struct._iobuf*)

declare i32 @printf(i8*, ...)

declare void @exit(i32)

define void @check_int_to_bool(i32 %test) {
entry:
  %test.addr = alloca i32, align 4
  store i32 %test, i32* %test.addr, align 4
  %0 = load i32* %test.addr, align 4
  %cmp = icmp ne i32 %0, 0
  br i1 %cmp, label %land.lhs.true, label %if.end

land.lhs.true:
  %1 = load i32* %test.addr, align 4
  %cmp1 = icmp ne i32 %1, 1
  br i1 %cmp1, label %if.then, label %if.end

if.then:
  %2 = load i32* %test.addr, align 4
  %call = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([63 x i8]* @.str0, i32 0, i32 0), i32 %2)
  call void @exit(i32 -1)
  unreachable

if.end:
  ret void
}

define i32 @main() {
  entry:



  %_17result = alloca i32, align 4
  %_18ftest = alloca float, align 4
  %_19i = alloca i32, align 4
  %_20arr = alloca [10 x i32], align 4
  %_21strtest = alloca i8*, align 4store i8* getelementptr inbounds ([1 x i8]* @.str_empty, i32 0, i32 0), i8** %_21strtest, align 4
  %0 = getelementptr inbounds [9 x i8]* @.str2, i32 0, i32 0
  store i8* %0, i8** %_21strtest, align 4
  %1 = load i8** %_21strtest, align 4
  %call0 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %1)
  %2 = getelementptr inbounds [6 x i8]* @.str3, i32 0, i32 0
  %call1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %2)
  %3 = getelementptr inbounds [9 x i8]* @.str4, i32 0, i32 0
  call void @_14teststrproc(i8* %3, i8** %_21strtest)
  %4 = load i8** %_21strtest, align 4
  %call2 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %4)
  %call3 = call i8* @malloc(i32 100)
  store i8* %call3, i8** %_21strtest, align 4
  %5 = load i8** %_21strtest, align 4
  %6 = load [0 x %struct._iobuf]** @_imp___iob, align 4
  %arrayidx4 = getelementptr inbounds [0 x %struct._iobuf]* %6, i32 0, i32 0
  %call5 = call i8* @fgets(i8* %5, i32 100, %struct._iobuf* %arrayidx4)
  %7 = load i8** %_21strtest, align 4
  %call6 = call i32 (i8*, i8*, ...)* @sscanf(i8* %call5, i8* getelementptr inbounds ([21 x i8]* @.str_sscanfqualify, i32 0, i32 0), i8* %7)
  %8 = load i8** %_21strtest, align 4
  %call7 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %8)
  %const8 = alloca i32, align 4
  store i32 1, i32* %const8, align 4
  %9 = load i32* %const8
  store i32 %9, i32* %_17result, align 4
  %const9 = alloca i32, align 4
  store i32 0, i32* %const9, align 4
  %10 = load i32* %const9
  store i32 %10, i32* %_19i, align 4
  br label %while.cond10
  
while.cond10:
  ;instance of Destination and not array item ptr, idxTemp: 11
  %11 = load i32* %_19i, align 4
  %const11 = alloca i32, align 4
  store i32 10, i32* %const11, align 4
  %12 = load i32* %const11
  %lt12 = icmp slt i32 %11, %12
  ;calculate result 13
  br i1 %lt12, label %while.body10, label %while.end10
  
while.body10:
  ;instance of Destination and not array item ptr, idxTemp: 13
  %13 = load i32* %_19i, align 4
  %const13 = alloca i32, align 4
  store i32 1, i32* %const13, align 4
  %14 = load i32* %const13
  %add14 = add nsw i32 %13, %14
  ;calculate result 15
  ;calculate int 15
  ;instance of Destination and not array item ptr, idxTemp: 15
  %15 = load i32* %_19i, align 4
  %arrayidx15 = getelementptr inbounds [10 x i32]* %_20arr, i32 0, i32 %15
  call void @_11multiply(i32 %add14, i32* %arrayidx15)
  ;instance of Destination and not array item ptr, idxTemp: 16
  %16 = load i32* %_19i, align 4
  %arrayidx16 = getelementptr inbounds [10 x i32]* %_20arr, i32 0, i32 %16
  %17 = load i32* %arrayidx16, align 4
  %call17 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %17)
  ;instance of Destination and not array item ptr, idxTemp: 18
  %18 = load i32* %_19i, align 4
  %const18 = alloca i32, align 4
  store i32 1, i32* %const18, align 4
  %19 = load i32* %const18
  %add19 = add nsw i32 %18, %19
  ;calculate result 20
  ;calculate int 20
  store i32 %add19, i32* %_19i, align 4

  br label %while.cond10
  
while.end10:
  ;20
  %const20 = alloca i32, align 4
  store i32 0, i32* %const20, align 4
  %20 = load i32* %const20
  store i32 %20, i32* %_19i, align 4
  br label %while.cond21
  
while.cond21:
  ;instance of Destination and not array item ptr, idxTemp: 21
  %21 = load i32* %_19i, align 4
  %const22 = alloca i32, align 4
  store i32 10, i32* %const22, align 4
  %22 = load i32* %const22
  %lt23 = icmp slt i32 %21, %22
  ;calculate result 23
  br i1 %lt23, label %while.body21, label %while.end21
  
while.body21:
  ;instance of Destination and not array item ptr, idxTemp: 23
  %23 = load i32* %_19i, align 4
  %arrayidx24 = getelementptr inbounds [10 x i32]* %_20arr, i32 0, i32 %23
  %24 = load i32* %arrayidx24, align 4
  %call25 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %24)
  ;instance of Destination and not array item ptr, idxTemp: 25
  %25 = load i32* %_19i, align 4
  %const26 = alloca i32, align 4
  store i32 1, i32* %const26, align 4
  %26 = load i32* %const26
  %add27 = add nsw i32 %25, %26
  ;calculate result 27
  ;calculate int 27
  store i32 %add27, i32* %_19i, align 4

  br label %while.cond21
  
while.end21:
  ;27

  ret i32 0
}

@.str1 = private unnamed_addr constant [9 x i8] c"teststr2\00", align 1
@.str2 = private unnamed_addr constant [9 x i8] c"teststr1\00", align 1
@.str3 = private unnamed_addr constant [6 x i8] c"test2\00", align 1
@.str4 = private unnamed_addr constant [9 x i8] c"teststr2\00", align 1
