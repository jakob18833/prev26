# RISC-V Assembly: Sum of 1 to 10
# Result stored in a0

.section .text
.global _start

_start:
    li   t0, 0        # t0 = sum = 0
    li   t1, 1        # t1 = i = 1
    li   t2, 10       # t2 = upper bound (10)

loop:
    bgt  t1, t2, done # if i > 10, exit loop
    add  t0, t0, t1   # sum += i
    addi t1, t1, 1    # i++
    j    loop         # repeat

done:
    mv   a0, t0       # move result into a0 (return value / arg register)

    # Exit via Linux syscall (ecall)
    li   a7, 93       # syscall number 93 = exit
    ecall
