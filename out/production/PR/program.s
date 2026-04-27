
.text
.global  main



main:

        la a0, buffer
        la a1, fmt
        la a2, 55
        call sprintf
        
        li a0, 1
        la a1, buffer
        li a2, 20

        li a7, 64
        ecall
        
        li a7, 93
        ecall

.section .rodata
fmt: .string "%d"

.section .bss
buffer: .space 20
