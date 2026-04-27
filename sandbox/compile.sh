#!/bin/bash

riscv64-linux-gnu-as -o a.o "$1"
riscv64-linux-gnu-ld -o a a.o
qemu-riscv64 ./a
echo "The program returned $?"
