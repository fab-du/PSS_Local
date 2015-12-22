#!/bin/sh


files=$(ls *.dot)

mkdir -p output


for file in $files
do
output=$(echo $file | cut -d'.' -f 1)
output_file="${output}.png"
echo $output_file
dot -Tpng $file -o "output/${output_file}"
done

#mv output ../md/figures/dotty


