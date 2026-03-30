#!/usr/bin/env bash

if [[ -z $1 ]]; then
  echo "Usage: " $0 "<ime_programa>"
  exit 1
fi
cd prev26
make clean >/dev/null
make >/dev/null
cd prg

make clean >/dev/null
echo "Compiling $1"
make $1 TARGETPHASE=seman DEV_MODE=$2

phases="seman"

for phase in $phases; do
  file="$(pwd)"/"$1"-"$phase".html
  echo $file
  if [[ -f $file ]]; then
    echo found
    firefox $file
  fi
done
