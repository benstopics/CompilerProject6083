; Function Attrs: nounwind
define i32 @getBool(i32 %arg) #0 {
entry:
  %arg.addr = alloca i32, align 4
  store i32 %arg, i32* %arg.addr, align 4
  %0 = load i32* %arg.addr, align 4
  ret i32 %0
}

; Function Attrs: nounwind
define i32 @main() #0 {
entry:
  %test = alloca i32, align 4
  %call = call i32 @getBool(i32 6)
  store i32 %call, i32* %test, align 4
  ret i32 0
}