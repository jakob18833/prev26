#!/bin/bash
files=$(find prev26 -type f | grep -e .java$ -e .g4$ -e .xsl$ -e Makefile$)
if [[ -z $1 ]]; then
    echo "Usage: $0 <phase>"
    exit 1
fi
zip 63230046-"$1".zip $files
