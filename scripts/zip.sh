#!/bin/bash
cd ..
src_dir="prev26"
vpisna="63230046"
dovoljene_koncnice=".java .g4 .xsl Makefile"

if [[ -z $1 ]]; then
    echo "Usage: $0 <compiler phase>"
    exit 1
fi

command="zip -r "$vpisna"-"$1".zip "$src_dir""
if [[ -n "$dovoljene_koncnice" ]]; then
    command=""$command" -i"
    for ending in $dovoljene_koncnice; do
        command=""$command" *"$ending""
    done
fi
echo "$command"
eval "$command"
