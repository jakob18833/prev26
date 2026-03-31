
cd prev26
make clean >/dev/null
make >/dev/null

cd prg/
make clean >/del/null
make >/dev/null

echo "<h1>TEST REPORT</h1>" > all.html
echo "<h3>Created $(date '+%d.%m.%Y %H:%M:%S') on $(uname -n)</h3>" >> all.html

log_phases="seman"
compile_phase="seman"
font_size="1.5"


for test in $(ls test*.p26 | cut -d"t" -f3 | sort -n | sed 's/^/test/'); do
    prg_name=${test:0:-4}

    echo -e "\nCompiling test $prg_name\n"

    make $prg_name TARGETPHASE=$compile_phase

    echo "<h2> ${prg_name} </h2>" >> all.html
    echo "<pre style=\"font-size: ${font_size}em;\"><code>" >> all.html
    cat ${prg_name}.p26 >> all.html
    echo "</code></pre>" >> all.html
    if [[ -f ${prg_name}-${compile_phase}.html ]]; then
        for phase in $log_phases; do
            cat ${prg_name}-${phase}.html >> all.html
        done

    else
        echo "[ERROR]: Failed to compile ${prg_name}!" >> all.html
    fi
done

for err in $(ls err*.p26 | cut -d"r" -f3 | sort -n | sed 's/^/err/'); do
    prg_name=${err:0:-4}
    echo -e "\nCompiling test $prg_name\n"
    make $prg_name TARGETPHASE=$compile_phase

    echo "<h2> ${prg_name} </h2>"
    echo "<pre style=\"font-size: ${font_size}em;\"><code>" >> all.html
    cat ${prg_name}.p26 >> all.html
    echo "</code></pre>" >> all.html
    if [[ -f ${prg_name}-${compile_phase}.html ]]; then
        for phase in $log_phases; do
            cat ${prg_name}-${phase}.html >> all.html
        done
    else 
        echo "[ERROR]: Compiled ${prg_name}!"
    fi
done

firefox all.html
