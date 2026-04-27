
.text
.global _start

_start:
    li t0, 0x8000000000000000
    li t1, 63
    srl a0, t0, t1
    li a7, 93
    ecall
