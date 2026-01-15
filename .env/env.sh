cpf=".classpath#"                                       # classpath file
main="de.bht_berlin.freerider.FreeriderApplication"     # class with main() to execute

# make '.classpath#' file
function mkcp() {
    [[ "$(uname)" =~ (CYGWIN|MINGW) ]] && local sep=";" || local sep=":"
    cmd --execute mvn dependency:build-classpath -q -Dmdep.outputFile=$cpf.tmp
    cmd --execute sed -e 's/^/target\\/classes$sep/' " < $cpf.tmp > $cpf"
    cmd --execute rm $cpf.tmp
}

function run() {
    [ -f "$cpf" ] &&
        cmd --execute java -cp @.classpath# $main ||
        echo "no classpath file: $cpf, create with 'mkcp'"
}

function wipe() {
    [ -f ".classpath#" ] && cmd --execute rm -f .classpath#
}

function cmd() {
    local args=();
    for arg in $@; do
        case $arg in
        --execute) local execute=true ;;
        *) args+=($arg) ;;
        esac
    done
    local command="${args[@]}"
    local prefix=" - "
    [ "$command" ] && echo $prefix$command && [ "$execute" ] && eval $command
}
