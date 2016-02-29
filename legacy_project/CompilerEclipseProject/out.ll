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
@.str_booltrue = private unnamed_addr constant [6 x i8] c"true\0A\00", align 1
@.str_boolfalse = private unnamed_addr constant [7 x i8] c"false\0A\00", align 1
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

define void @putbool(i1 %test) {
entry:
  %test.addr = alloca i1, align 4
  store i1 %test, i1* %test.addr, align 4
  %0 = load i1* %test.addr, align 4
  %cmp = icmp ne i1 %0, 0
  br i1 %cmp, label %print.true, label %print.false

print.true:
  %call1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([6 x i8]* @.str_booltrue, i32 0, i32 0))
  br label %if.end

print.false:
  %call2 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([7 x i8]* @.str_boolfalse, i32 0, i32 0))
  br label %if.end

if.end:
  ret void
}

define i32 @main() {
  entry:
  %_9str1 = alloca i8*, align 4
  %_10str2 = alloca i8*, align 4
  %_11str3 = alloca i8*, align 4
  %_12strs = alloca [30 x i8*], align 4
  %_13i = alloca i32, align 4
  %0 = getelementptr inbounds [14 x i8]* @.str1, i32 0, i32 0
  store i8* %0, i8** %_9str1, align 4
  %1 = getelementptr inbounds [14 x i8]* @.str2, i32 0, i32 0
  store i8* %1, i8** %_10str2, align 4
  %2 = getelementptr inbounds [14 x i8]* @.str3, i32 0, i32 0
  store i8* %2, i8** %_11str3, align 4
  %const0 = alloca i32, align 4
  store i32 0, i32* %const0, align 4
  %3 = load i32* %const0
  store i32 %3, i32* %_13i, align 4
  br label %while.cond1
  
while.cond1:
  ;instance of Destination and not array item ptr, idxTemp: 4
  %4 = load i32* %_13i, align 4
  %const2 = alloca i32, align 4
  store i32 30, i32* %const2, align 4
  %5 = load i32* %const2
  %lt3 = icmp slt i32 %4, %5
  ;calculate result 6
  br i1 %lt3, label %while.body1, label %while.end1
  
while.body1:
  ;instance of Destination and not array item ptr, idxTemp: 6
  %6 = load i8** %_9str1, align 4
  ;instance of Destination and not array item ptr, idxTemp: 7
  %7 = load i32* %_13i, align 4
  %arrayidx4 = getelementptr inbounds [30 x i8*]* %_12strs, i32 0, i32 %7
  store i8* %6, i8** %arrayidx4, align 4
  ;instance of Destination and not array item ptr, idxTemp: 8
  %8 = load i8** %_10str2, align 4
  ;instance of Destination and not array item ptr, idxTemp: 9
  %9 = load i32* %_13i, align 4
  %const5 = alloca i32, align 4
  store i32 1, i32* %const5, align 4
  %10 = load i32* %const5
  %add6 = add nsw i32 %9, %10
  ;calculate result 11
  ;calculate int 11
  %arrayidx7 = getelementptr inbounds [30 x i8*]* %_12strs, i32 0, i32 %add6
  store i8* %8, i8** %arrayidx7, align 4
  ;instance of Destination and not array item ptr, idxTemp: 11
  %11 = load i8** %_11str3, align 4
  ;instance of Destination and not array item ptr, idxTemp: 12
  %12 = load i32* %_13i, align 4
  %const8 = alloca i32, align 4
  store i32 2, i32* %const8, align 4
  %13 = load i32* %const8
  %add9 = add nsw i32 %12, %13
  ;calculate result 14
  ;calculate int 14
  %arrayidx10 = getelementptr inbounds [30 x i8*]* %_12strs, i32 0, i32 %add9
  store i8* %11, i8** %arrayidx10, align 4
  ;instance of Destination and not array item ptr, idxTemp: 14
  %14 = load i32* %_13i, align 4
  %call11 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %14)
  ;instance of Destination and not array item ptr, idxTemp: 15
  %15 = load i32* %_13i, align 4
  %const12 = alloca i32, align 4
  store i32 3, i32* %const12, align 4
  %16 = load i32* %const12
  %add13 = add nsw i32 %15, %16
  ;calculate result 17
  ;calculate int 17
  store i32 %add13, i32* %_13i, align 4

  br label %while.cond1
  
while.end1:
  ;17
  %const14 = alloca i32, align 4
  store i32 0, i32* %const14, align 4
  %17 = load i32* %const14
  store i32 %17, i32* %_13i, align 4
  br label %while.cond15
  
while.cond15:
  ;instance of Destination and not array item ptr, idxTemp: 18
  %18 = load i32* %_13i, align 4
  %const16 = alloca i32, align 4
  store i32 30, i32* %const16, align 4
  %19 = load i32* %const16
  %lt17 = icmp slt i32 %18, %19
  ;calculate result 20
  br i1 %lt17, label %while.body15, label %while.end15
  
while.body15:
  ;instance of Destination and not array item ptr, idxTemp: 20
  %20 = load i32* %_13i, align 4
  %call18 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %20)
  ;instance of Destination and not array item ptr, idxTemp: 21
  %21 = load i32* %_13i, align 4
  %arrayidx19 = getelementptr inbounds [30 x i8*]* %_12strs, i32 0, i32 %21
  %22 = load i8** %arrayidx19, align 4
  %call20 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %22)
  ;instance of Destination and not array item ptr, idxTemp: 23
  %23 = load i32* %_13i, align 4
  %const21 = alloca i32, align 4
  store i32 1, i32* %const21, align 4
  %24 = load i32* %const21
  %add22 = add nsw i32 %23, %24
  ;calculate result 25
  ;calculate int 25
  store i32 %add22, i32* %_13i, align 4

  br label %while.cond15
  
while.end15:
  ;25

  ret i32 0
}

@.str1 = private unnamed_addr constant [14 x i8] c"test string 1\00", align 1
@.str2 = private unnamed_addr constant [14 x i8] c"test string 2\00", align 1
@.str3 = private unnamed_addr constant [14 x i8] c"test string 3\00", align 1
