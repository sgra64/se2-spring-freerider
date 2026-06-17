declare -gA P=(
    [classpath-file]="target/.classpath"
    [mainclass-file]="target/.mainclass"
    [classes]="target/classes"
)

# run 'spring boot' application - the script creates file 'target/.classpath'
# with the classpath created by 'mvn dependency:build-classpath' and a file
# 'target/.mainclass' containing the class with the main() function.
function run() {
    if [ ! -f "${P[classpath-file]}" ]; then
        echo "mvn -q dependency:build-classpath -Dmdep.outputFile=${P[classpath-file]}"
        mvn -q dependency:build-classpath -Dmdep.outputFile="${P[classpath-file]}"
        local classpath=$( < "${P[classpath-file]}")
        [[ "$classpath" =~ ";" ]] && local sep=";" || local sep=":"
        classpath="${P[classes]}$sep$classpath"     # prepend path to classes
        echo $classpath > "${P[classpath-file]}"
        unset CLASSPATH
    fi
    [ -z "$CLASSPATH" -a -f "${P[classpath-file]}" ] &&
        echo "export CLASSPATH=\$( < ${P[classpath-file]})" &&
        export CLASSPATH=$( < "${P[classpath-file]}")
    # 
    [ ! -f "${P[mainclass-file]}" ] &&
        echo "searching for main()-method in 'src/main/java'..." &&
        find_mainclass > "${P[mainclass-file]}" && {
            [ -f "${P[mainclass-file]}" -a -s "${P[mainclass-file]}" ] &&
                echo "found main class: '$(cat ${P[mainclass-file]})', stored in file '${P[mainclass-file]}'" || {
                    rm -f "${P[mainclass-file]}"
                    echo "Error: no main()-method found, file '${P[mainclass-file]}' is empty"
                }
        }
    # 
    [ -z "$(find target -name '*.class' 2>/dev/null)" ] &&
        echo "no compiled code, compile with: 'mvn compile'" &&
        mvn compile
    # 
    [ -f "${P[classpath-file]}" -a -f "${P[mainclass-file]}" ] &&
        java -cp @"${P[classpath-file]}" @"${P[mainclass-file]}" ||
            echo "no classpath file: $cpf, create with 'mkcp'"
}

function find_mainclass() {
    find src/main/java -name "*.java" \
        -exec grep -Hn 'static[[:space:]]*void[[:space:]]*main' {} \; |
            head -n 1 | cut -d: -f1 |
            sed 's/src\/main\/java\///;s/\.java$//;s/\//./g'
}

# remove created content, unset CLASSPATH
function wipe() {
    echo "rm ${P[classpath-file]} ${P[mainclass-file]}"
    rm "${P[classpath-file]}" "${P[mainclass-file]}"
    unset CLASSPATH
}
