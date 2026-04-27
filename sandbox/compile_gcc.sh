#!/bin/bash

riscv64-linux-gnu-gcc -o a "$1"
qemu-riscv64 ./a
echo "The program returned $?"
