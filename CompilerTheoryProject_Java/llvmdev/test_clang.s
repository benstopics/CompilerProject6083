	.text
	.def	 @feat.00;
	.scl	3;
	.type	0;
	.endef
	.globl	@feat.00
@feat.00 = 1
	.def	 _foobar;
	.scl	2;
	.type	32;
	.endef
	.globl	_foobar
	.align	16, 0x90
_foobar:                                # @foobar
# BB#0:                                 # %entry
	pushl	%ebp
	movl	%esp, %ebp
	andl	$-8, %esp
	subl	$16, %esp
	movl	$1, (%esp)
	movl	$2, 4(%esp)
	movsd	(%esp), %xmm0
	movsd	%xmm0, 8(%esp)
	movl	8(%esp), %eax
	movl	12(%esp), %edx
	movl	%ebp, %esp
	popl	%ebp
	retl

	.def	 _main;
	.scl	2;
	.type	32;
	.endef
	.globl	_main
	.align	16, 0x90
_main:                                  # @main
# BB#0:                                 # %entry
	pushl	%ebp
	movl	%esp, %ebp
	andl	$-8, %esp
	subl	$40, %esp
	calll	___main
	leal	L_.str, %eax
	movl	$0, 36(%esp)
	movl	%eax, 20(%esp)          # 4-byte Spill
	calll	_foobar
	movl	%edx, 28(%esp)
	movl	%eax, 24(%esp)
	movl	24(%esp), %eax
	movl	20(%esp), %edx          # 4-byte Reload
	movl	%edx, (%esp)
	movl	%eax, 4(%esp)
	calll	_printf
	leal	L_.str, %edx
	movl	28(%esp), %ecx
	movl	%edx, (%esp)
	movl	%ecx, 4(%esp)
	movl	%eax, 16(%esp)          # 4-byte Spill
	calll	_printf
	xorl	%ecx, %ecx
	movl	%eax, 12(%esp)          # 4-byte Spill
	movl	%ecx, %eax
	movl	%ebp, %esp
	popl	%ebp
	retl

	.section	.rdata,"dr"
L_.str:                                 # @.str
	.asciz	"%d"


