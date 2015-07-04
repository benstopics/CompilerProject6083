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

@.str0 = private unnamed_addr constant [63 x i8] c"Runtime error -> Attempted to convert integer '%d' to boolean.\00", align 1
@.str_empty = private unnamed_addr constant [3 x i8] c"%d\00", align 1
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

  %_12result = alloca i32, align 4
  %_13ftest = alloca float, align 4
  %_14i = alloca i32, align 4
  %_15arr = alloca [10 x i32], align 4
  %call0 = call i32 (i8*, ...)* @scanf(i8* getelementptr inbounds ([3 x i8]* @.str_getfloat, i32 0, i32 0), float* %_13ftest)
  ;instance of Destination and not array item ptr, idxTemp: 0
  %0 = load float* %_13ftest, align 4
  %1 = fpext float %0 to double
  %call1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putfloat, i32 0, i32 0), double %1)
  %const2 = alloca i32, align 4
  store i32 1, i32* %const2, align 4
  %2 = load i32* %const2
  store i32 %2, i32* %_12result, align 4
  %const3 = alloca i32, align 4
  store i32 0, i32* %const3, align 4
  %3 = load i32* %const3
  store i32 %3, i32* %_14i, align 4
  br label %while.cond4
  
while.cond4:
  ;instance of Destination and not array item ptr, idxTemp: 4
  %4 = load i32* %_14i, align 4
  %const5 = alloca i32, align 4
  store i32 10, i32* %const5, align 4
  %5 = load i32* %const5
  %lt6 = icmp slt i32 %4, %5
  ;calculate result 6
  br i1 %lt6, label %while.body4, label %while.end4
  
while.body4:
  ;instance of Destination and not array item ptr, idxTemp: 6
  %6 = load i32* %_14i, align 4
  %const7 = alloca i32, align 4
  store i32 1, i32* %const7, align 4
  %7 = load i32* %const7
  %add8 = add nsw i32 %6, %7
  ;calculate result 8
  ;calculate int 8
  ;instance of Destination and not array item ptr, idxTemp: 8
  %8 = load i32* %_14i, align 4
  %arrayidx9 = getelementptr inbounds [10 x i32]* %_15arr, i32 0, i32 %8
  call void @_11multiply(i32 %add8, i32* %arrayidx9)
  ;instance of Destination and not array item ptr, idxTemp: 9
  %9 = load i32* %_14i, align 4
  %arrayidx10 = getelementptr inbounds [10 x i32]* %_15arr, i32 0, i32 %9
  %10 = load i32* %arrayidx10, align 4
  %call11 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %10)
  ;instance of Destination and not array item ptr, idxTemp: 11
  %11 = load i32* %_14i, align 4
  %const12 = alloca i32, align 4
  store i32 1, i32* %const12, align 4
  %12 = load i32* %const12
  %add13 = add nsw i32 %11, %12
  ;calculate result 13
  ;calculate int 13
  store i32 %add13, i32* %_14i, align 4

  br label %while.cond4
  
while.end4:
  ;13
  %const14 = alloca i32, align 4
  store i32 0, i32* %const14, align 4
  %13 = load i32* %const14
  store i32 %13, i32* %_14i, align 4
  br label %while.cond15
  
while.cond15:
  ;instance of Destination and not array item ptr, idxTemp: 14
  %14 = load i32* %_14i, align 4
  %const16 = alloca i32, align 4
  store i32 10, i32* %const16, align 4
  %15 = load i32* %const16
  %lt17 = icmp slt i32 %14, %15
  ;calculate result 16
  br i1 %lt17, label %while.body15, label %while.end15
  
while.body15:
  ;instance of Destination and not array item ptr, idxTemp: 16
  %16 = load i32* %_14i, align 4
  %arrayidx18 = getelementptr inbounds [10 x i32]* %_15arr, i32 0, i32 %16
  %17 = load i32* %arrayidx18, align 4
  %call19 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %17)
  ;instance of Destination and not array item ptr, idxTemp: 18
  %18 = load i32* %_14i, align 4
  %const20 = alloca i32, align 4
  store i32 1, i32* %const20, align 4
  %19 = load i32* %const20
  %add21 = add nsw i32 %18, %19
  ;calculate result 20
  ;calculate int 20
  store i32 %add21, i32* %_14i, align 4

  br label %while.cond15
  
while.end15:
  ;20

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
