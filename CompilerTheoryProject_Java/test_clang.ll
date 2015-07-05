; ModuleID = 'test_clang.c'
target datalayout = "e-m:w-p:32:32-i64:64-f80:32-n8:16:32-S32"
target triple = "i686-pc-windows-gnu"

%struct._iobuf = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }

@.str = private unnamed_addr constant [63 x i8] c"Runtime error -> Attempted to convert integer '%d' to boolean.\00", align 1
@_imp___iob = external global [0 x %struct._iobuf]*
@.str1 = private unnamed_addr constant [21 x i8] c"%[a-zA-Z0-9 _,;:.']\0A\00", align 1
@.str2 = private unnamed_addr constant [4 x i8] c"%s\0A\00", align 1
@.str3 = private unnamed_addr constant [6 x i8] c"test2\00", align 1
@.str4 = private unnamed_addr constant [6 x i8] c"test1\00", align 1
@.str5 = private unnamed_addr constant [9 x i8] c"testtest\00", align 1

; Function Attrs: nounwind
define void @check_int_to_bool(i32 %test) #0 {
entry:
  %test.addr = alloca i32, align 4
  store i32 %test, i32* %test.addr, align 4
  %0 = load i32* %test.addr, align 4
  %cmp = icmp ne i32 %0, 0
  br i1 %cmp, label %land.lhs.true, label %if.end

land.lhs.true:                                    ; preds = %entry
  %1 = load i32* %test.addr, align 4
  %cmp1 = icmp ne i32 %1, 1
  br i1 %cmp1, label %if.then, label %if.end

if.then:                                          ; preds = %land.lhs.true
  %2 = load i32* %test.addr, align 4
  %call = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([63 x i8]* @.str, i32 0, i32 0), i32 %2)
  call void @exit(i32 -1) #3
  unreachable

if.end:                                           ; preds = %land.lhs.true, %entry
  ret void
}

declare i32 @printf(i8*, ...) #1

; Function Attrs: noreturn nounwind
declare void @exit(i32) #2

; Function Attrs: nounwind
define void @testfunc(i32 %value, i32* %x) #0 {
entry:
  %value.addr = alloca i32, align 4
  %x.addr = alloca i32*, align 4
  store i32 %value, i32* %value.addr, align 4
  store i32* %x, i32** %x.addr, align 4
  %0 = load i32* %value.addr, align 4
  %1 = load i32** %x.addr, align 4
  store i32 %0, i32* %1, align 4
  ret void
}

; Function Attrs: nounwind
define void @teststrarrayptr(i8** %svars) #0 {
entry:
  %svars.addr = alloca i8**, align 4
  %newstr = alloca i8*, align 4
  store i8** %svars, i8*** %svars.addr, align 4
  %call = call i8* @malloc(i32 100)
  store i8* %call, i8** %newstr, align 4
  %0 = load i8** %newstr, align 4
  %1 = load [0 x %struct._iobuf]** @_imp___iob, align 4
  %arrayidx = getelementptr inbounds [0 x %struct._iobuf]* %1, i32 0, i32 0
  %call1 = call i8* @fgets(i8* %0, i32 100, %struct._iobuf* %arrayidx)
  %2 = load i8** %newstr, align 4
  %call2 = call i32 (i8*, i8*, ...)* @sscanf(i8* %call1, i8* getelementptr inbounds ([21 x i8]* @.str1, i32 0, i32 0), i8* %2)
  %3 = load i8** %newstr, align 4
  %4 = load i8*** %svars.addr, align 4
  %arrayidx3 = getelementptr inbounds i8** %4, i32 1
  store i8* %3, i8** %arrayidx3, align 4
  %5 = load i8*** %svars.addr, align 4
  %arrayidx4 = getelementptr inbounds i8** %5, i32 1
  %6 = load i8** %arrayidx4, align 4
  %call5 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str2, i32 0, i32 0), i8* %6)
  ret void
}

declare i8* @malloc(i32) #1

declare i32 @sscanf(i8*, i8*, ...) #1

declare i8* @fgets(i8*, i32, %struct._iobuf*) #1

; Function Attrs: nounwind
define i32 @main() #0 {
entry:
  %retval = alloca i32, align 4
  %ftest = alloca float, align 4
  %teststr2 = alloca i8*, align 4
  %teststr3 = alloca i8*, align 4
  %newstr = alloca i8*, align 4
  store i32 0, i32* %retval
  store float 0x4016666660000000, float* %ftest, align 4
  store i8* getelementptr inbounds ([6 x i8]* @.str3, i32 0, i32 0), i8** %teststr2, align 4
  store i8* getelementptr inbounds ([6 x i8]* @.str4, i32 0, i32 0), i8** %teststr3, align 4
  %0 = load i8** %teststr2, align 4
  %call = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str2, i32 0, i32 0), i8* %0)
  %1 = load i8** %teststr3, align 4
  store i8* %1, i8** %teststr2, align 4
  %call1 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str2, i32 0, i32 0), i8* getelementptr inbounds ([9 x i8]* @.str5, i32 0, i32 0))
  %call2 = call i8* @malloc(i32 100)
  store i8* %call2, i8** %newstr, align 4
  %2 = load i8** %newstr, align 4
  %3 = load [0 x %struct._iobuf]** @_imp___iob, align 4
  %arrayidx = getelementptr inbounds [0 x %struct._iobuf]* %3, i32 0, i32 0
  %call3 = call i8* @fgets(i8* %2, i32 100, %struct._iobuf* %arrayidx)
  %4 = load i8** %newstr, align 4
  %call4 = call i32 (i8*, i8*, ...)* @sscanf(i8* %call3, i8* getelementptr inbounds ([21 x i8]* @.str1, i32 0, i32 0), i8* %4)
  %5 = load i8** %newstr, align 4
  %call5 = call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([4 x i8]* @.str2, i32 0, i32 0), i8* %5)
  ret i32 0
}

attributes #0 = { nounwind "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { noreturn nounwind "less-precise-fpmad"="false" "no-frame-pointer-elim"="true" "no-frame-pointer-elim-non-leaf" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "stack-protector-buffer-size"="8" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { noreturn nounwind }

!llvm.ident = !{!0}

!0 = !{!"clang version 3.6.1 (tags/RELEASE_361/final)"}
