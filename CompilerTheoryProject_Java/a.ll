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


  %_15result = alloca i32, align 4
  %_16ftest = alloca float, align 4
  %_17i = alloca i32, align 4
  %_18arr = alloca [10 x i32], align 4
  %_19strtest = alloca i8*, align 4store i8* getelementptr inbounds ([1 x i8]* @.str_empty, i32 0, i32 0), i8** %_19strtest, align 4
  %0 = getelementptr inbounds [9 x i8]* @.str2, i32 0, i32 0
  store i8* %0, i8** %_19strtest, align 4
  %1 = load i8** %_19strtest, align 4
  %call0 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %1)
  %2 = getelementptr inbounds [6 x i8]* @.str3, i32 0, i32 0
  %call1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %2)
  %3 = getelementptr inbounds [9 x i8]* @.str4, i32 0, i32 0
  call void @_14teststrproc(i8* %3, i8** %_19strtest)
  %4 = load i8** %_19strtest, align 4
  %call2 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %4)
  %const3 = alloca i32, align 4
  store i32 1, i32* %const3, align 4
  %5 = load i32* %const3
  store i32 %5, i32* %_15result, align 4
  %const4 = alloca i32, align 4
  store i32 0, i32* %const4, align 4
  %6 = load i32* %const4
  store i32 %6, i32* %_17i, align 4
  br label %while.cond5
  
while.cond5:
  ;instance of Destination and not array item ptr, idxTemp: 7
  %7 = load i32* %_17i, align 4
  %const6 = alloca i32, align 4
  store i32 10, i32* %const6, align 4
  %8 = load i32* %const6
  %lt7 = icmp slt i32 %7, %8
  ;calculate result 9
  br i1 %lt7, label %while.body5, label %while.end5
  
while.body5:
  ;instance of Destination and not array item ptr, idxTemp: 9
  %9 = load i32* %_17i, align 4
  %const8 = alloca i32, align 4
  store i32 1, i32* %const8, align 4
  %10 = load i32* %const8
  %add9 = add nsw i32 %9, %10
  ;calculate result 11
  ;calculate int 11
  ;instance of Destination and not array item ptr, idxTemp: 11
  %11 = load i32* %_17i, align 4
  %arrayidx10 = getelementptr inbounds [10 x i32]* %_18arr, i32 0, i32 %11
  call void @_11multiply(i32 %add9, i32* %arrayidx10)
  ;instance of Destination and not array item ptr, idxTemp: 12
  %12 = load i32* %_17i, align 4
  %arrayidx11 = getelementptr inbounds [10 x i32]* %_18arr, i32 0, i32 %12
  %13 = load i32* %arrayidx11, align 4
  %call12 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %13)
  ;instance of Destination and not array item ptr, idxTemp: 14
  %14 = load i32* %_17i, align 4
  %const13 = alloca i32, align 4
  store i32 1, i32* %const13, align 4
  %15 = load i32* %const13
  %add14 = add nsw i32 %14, %15
  ;calculate result 16
  ;calculate int 16
  store i32 %add14, i32* %_17i, align 4

  br label %while.cond5
  
while.end5:
  ;16
  %const15 = alloca i32, align 4
  store i32 0, i32* %const15, align 4
  %16 = load i32* %const15
  store i32 %16, i32* %_17i, align 4
  br label %while.cond16
  
while.cond16:
  ;instance of Destination and not array item ptr, idxTemp: 17
  %17 = load i32* %_17i, align 4
  %const17 = alloca i32, align 4
  store i32 10, i32* %const17, align 4
  %18 = load i32* %const17
  %lt18 = icmp slt i32 %17, %18
  ;calculate result 19
  br i1 %lt18, label %while.body16, label %while.end16
  
while.body16:
  ;instance of Destination and not array item ptr, idxTemp: 19
  %19 = load i32* %_17i, align 4
  %arrayidx19 = getelementptr inbounds [10 x i32]* %_18arr, i32 0, i32 %19
  %20 = load i32* %arrayidx19, align 4
  %call20 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %20)
  ;instance of Destination and not array item ptr, idxTemp: 21
  %21 = load i32* %_17i, align 4
  %const21 = alloca i32, align 4
  store i32 1, i32* %const21, align 4
  %22 = load i32* %const21
  %add22 = add nsw i32 %21, %22
  ;calculate result 23
  ;calculate int 23
  store i32 %add22, i32* %_17i, align 4

  br label %while.cond16
  
while.end16:
  ;23

  ret i32 0
}

declare i8* @malloc(i32)

declare i8* @strcpy(i8*, i8*)

declare i32 @scanf(i8*, ...)

declare i8* @gets(i8*)

declare i32 @sscanf(i8*, i8*, ...)

%struct._iobuf = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }

declare i8* @fgets(i8*, i32, %struct._iobuf*)

declare i32 @printf(i8*, ...)

declare void @exit(i32)
@.str1 = private unnamed_addr constant [9 x i8] c"teststr2\00", align 1
@.str2 = private unnamed_addr constant [9 x i8] c"teststr1\00", align 1
@.str3 = private unnamed_addr constant [6 x i8] c"test2\00", align 1
@.str4 = private unnamed_addr constant [9 x i8] c"teststr2\00", align 1
