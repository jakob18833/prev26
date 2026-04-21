#!/usr/bin/env bash

if [[ -z $1 ]]; then
  echo "Usage: " $0 "<ime_programa>"
  exit 1
fi
cd ../prev26_wc
make clean >/dev/null
make >/dev/null
cd prg

make clean >/dev/null

echo "Compiling $1"
compile_phase="imrgen"
log_phases="imrgen"
document="$1.html"
prg_name="$1"
font_size="1.2"
echo "" > $document

make $prg_name TARGETPHASE=${compile_phase}
echo "Searching for ${prg_name}-${compile_phase}.html "

echo "<h2> ${prg_name} </h2>" >> $document
echo "<pre style=\"font-size: ${font_size}em;\"><code>" >> $document
cat ${prg_name}.p26 >> $document
echo "</code></pre>" >> $document
if [[ -f ${prg_name}-${compile_phase}.html ]]; then
    for phase in $log_phases; do
        cat ${prg_name}-${phase}.html >> $document
    done
fi
firefox -P 4K $document
