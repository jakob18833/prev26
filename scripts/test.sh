
cd ../prev26
make clean >/dev/null
make >/dev/null

cd prg/
make clean >/del/null
make >/dev/null

echo "<h1>TEST REPORT</h1>" > all.html
echo "<h3>Created $(date '+%d.%m.%Y %H:%M:%S') on $(uname -n)</h3>" >> all.html

log_phases="imrgen"
compile_phase="imrgen"
font_size="1.5"
successful_tests=0
failed_tests=0
caught_errs=0
missed_errs=0

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
        ((successful_tests++))

    else
        ((failed_tests++))
        echo "[ERROR]: Failed to compile ${prg_name}!" >> all.html
    fi
done

for err in $(ls err*.p26 | cut -d"r" -f3 | sort -n | sed 's/^/err/'); do
    prg_name=${err:0:-4}
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
        ((missed_errs++))
        echo "[ERROR]: Compiled ${prg_name}!"
    else 
        ((caught_errs++))
    fi
done
echo "<h3> Report </h3><br>Successful tests: ${successful_tests} Failed tests: ${failed_tests}<br>" >> all.html
echo "Caught errs: ${caught_errs} Missed errs: ${missed_errs}<br>" >> all.html
echo -e "Report\nSuccessful tests: ${successful_tests} Failed tests: ${failed_tests}"
echo "Caught errs: ${caught_errs} Missed errs: ${missed_errs}"
firefox -P 4K all.html
