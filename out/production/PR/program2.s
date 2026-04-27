# int sprintf(char *str, const char *format, ...)
# a0 = output buffer
# a1 = format string
# a2 = integer to convert

.text
.global _start

_start:
    la   a0, buffer       # output buffer
    la   a1, fmt          # "%d\0"
    li   a2, 55           # the number
    call sprintf          # libc function

    # now print the buffer
    li   a7, 64           # write syscall
    li   a0, 1            # stdout
    la   a1, buffer
    li   a2, 12
    ecall

    li   a7, 93
    li   a0, 0
    ecall

.section .rodata
fmt:    .string "%d"

.section .bss
buffer: .space 32
