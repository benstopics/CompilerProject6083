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



  %_17result = alloca i32, align 4
  %_18ftest = alloca float, align 4
  %_19i = alloca i32, align 4
  %_20arr = alloca [10 x i32], align 4
  %_21strtest = alloca i8*, align 4store i8* getelementptr inbounds ([1 x i8]* @.str_empty, i32 0, i32 0), i8** %_21strtest, align 4
  %_22truebool = alloca i1, align 4
  %_23falsebool = alloca i1, align 4
  %const0 = alloca i1, align 4
  store i1 true, i1* %const0, align 4
  %0 = load i1* %const0
  call void @putbool(i1 %0)
  %const1 = alloca i1, align 4
  store i1 false, i1* %const1, align 4
  %1 = load i1* %const1
  call void @putbool(i1 %1)
  %const2 = alloca i32, align 4
  store i32 7, i32* %const2, align 4
  %2 = load i32* %const2
  %const3 = alloca i32, align 4
  store i32 6, i32* %const3, align 4
  %3 = load i32* %const3
  %gt4 = icmp sgt i32 %2, %3
  ;calculate result 4
  store i1 %gt4, i1* %_22truebool, align 4
  %const5 = alloca i32, align 4
  store i32 6, i32* %const5, align 4
  %4 = load i32* %const5
  %const6 = alloca i32, align 4
  store i32 7, i32* %const6, align 4
  %5 = load i32* %const6
  %gt7 = icmp sgt i32 %4, %5
  ;calculate result 6
  store i1 %gt7, i1* %_23falsebool, align 4
  ;instance of Destination and not array item ptr, idxTemp: 6
  %6 = load i1* %_22truebool, align 4
  call void @putbool(i1 %6)
  ;instance of Destination and not array item ptr, idxTemp: 7
  %7 = load i1* %_23falsebool, align 4
  call void @putbool(i1 %7)
  %8 = getelementptr inbounds [9 x i8]* @.str2, i32 0, i32 0
  store i8* %8, i8** %_21strtest, align 4
  %9 = load i8** %_21strtest, align 4
  %call8 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %9)
  %10 = getelementptr inbounds [6 x i8]* @.str3, i32 0, i32 0
  %call9 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %10)
  %11 = getelementptr inbounds [9 x i8]* @.str4, i32 0, i32 0
  call void @_14teststrproc(i8* %11, i8** %_21strtest)
  %12 = load i8** %_21strtest, align 4
  %call10 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %12)
  %call11 = call i8* @malloc(i32 100)
  store i8* %call11, i8** %_21strtest, align 4
  %13 = load i8** %_21strtest, align 4
  %14 = load [0 x %struct._iobuf]** @_imp___iob, align 4
  %arrayidx12 = getelementptr inbounds [0 x %struct._iobuf]* %14, i32 0, i32 0
  %call13 = call i8* @fgets(i8* %13, i32 100, %struct._iobuf* %arrayidx12)
  %15 = load i8** %_21strtest, align 4
  %call14 = call i32 (i8*, i8*, ...)* @sscanf(i8* %call13, i8* getelementptr inbounds ([21 x i8]* @.str_sscanfqualify, i32 0, i32 0), i8* %15)
  %16 = load i8** %_21strtest, align 4
  %call15 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putstring, i32 0, i32 0), i8* %16)
  %const16 = alloca i32, align 4
  store i32 1, i32* %const16, align 4
  %17 = load i32* %const16
  store i32 %17, i32* %_17result, align 4
  %const17 = alloca i32, align 4
  store i32 0, i32* %const17, align 4
  %18 = load i32* %const17
  store i32 %18, i32* %_19i, align 4
  br label %while.cond18
  
while.cond18:
  ;instance of Destination and not array item ptr, idxTemp: 19
  %19 = load i32* %_19i, align 4
  %const19 = alloca i32, align 4
  store i32 10, i32* %const19, align 4
  %20 = load i32* %const19
  %lt20 = icmp slt i32 %19, %20
  ;calculate result 21
  br i1 %lt20, label %while.body18, label %while.end18
  
while.body18:
  ;instance of Destination and not array item ptr, idxTemp: 21
  %21 = load i32* %_19i, align 4
  %const21 = alloca i32, align 4
  store i32 1, i32* %const21, align 4
  %22 = load i32* %const21
  %add22 = add nsw i32 %21, %22
  ;calculate result 23
  ;calculate int 23
  ;instance of Destination and not array item ptr, idxTemp: 23
  %23 = load i32* %_19i, align 4
  %arrayidx23 = getelementptr inbounds [10 x i32]* %_20arr, i32 0, i32 %23
  call void @_11multiply(i32 %add22, i32* %arrayidx23)
  ;instance of Destination and not array item ptr, idxTemp: 24
  %24 = load i32* %_19i, align 4
  %arrayidx24 = getelementptr inbounds [10 x i32]* %_20arr, i32 0, i32 %24
  %25 = load i32* %arrayidx24, align 4
  %call25 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %25)
  ;instance of Destination and not array item ptr, idxTemp: 26
  %26 = load i32* %_19i, align 4
  %const26 = alloca i32, align 4
  store i32 1, i32* %const26, align 4
  %27 = load i32* %const26
  %add27 = add nsw i32 %26, %27
  ;calculate result 28
  ;calculate int 28
  store i32 %add27, i32* %_19i, align 4

  br label %while.cond18
  
while.end18:
  ;28
  %const28 = alloca i32, align 4
  store i32 0, i32* %const28, align 4
  %28 = load i32* %const28
  store i32 %28, i32* %_19i, align 4
  br label %while.cond29
  
while.cond29:
  ;instance of Destination and not array item ptr, idxTemp: 29
  %29 = load i32* %_19i, align 4
  %const30 = alloca i32, align 4
  store i32 10, i32* %const30, align 4
  %30 = load i32* %const30
  %lt31 = icmp slt i32 %29, %30
  ;calculate result 31
  br i1 %lt31, label %while.body29, label %while.end29
  
while.body29:
  ;instance of Destination and not array item ptr, idxTemp: 31
  %31 = load i32* %_19i, align 4
  %arrayidx32 = getelementptr inbounds [10 x i32]* %_20arr, i32 0, i32 %31
  %32 = load i32* %arrayidx32, align 4
  %call33 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str_putint, i32 0, i32 0), i32 %32)
  ;instance of Destination and not array item ptr, idxTemp: 33
  %33 = load i32* %_19i, align 4
  %const34 = alloca i32, align 4
  store i32 1, i32* %const34, align 4
  %34 = load i32* %const34
  %add35 = add nsw i32 %33, %34
  ;calculate result 35
  ;calculate int 35
  store i32 %add35, i32* %_19i, align 4

  br label %while.cond29
  
while.end29:
  ;35

  ret i32 0
}

@.str1 = private unnamed_addr constant [9 x i8] c"teststr2\00", align 1
@.str2 = private unnamed_addr constant [9 x i8] c"teststr1\00", align 1
@.str3 = private unnamed_addr constant [6 x i8] c"test2\00", align 1
@.str4 = private unnamed_addr constant [9 x i8] c"teststr2\00", align 1
