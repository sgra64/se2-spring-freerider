cpf=".class.path"                                       # classpath file
main="de.bht_berlin.freerider.FreeriderApplication"     # class with main() to execute

# make '.classpath#' file
function mkcp() {
    if [ ! -f "$cpf" -o "$1" = "-f" ]; then
        [[ "$(uname)" =~ (CYGWIN|MINGW) ]] && local sep=";" || local sep=":"
        echo "- mvn dependency:build-classpath -q -Dmdep.outputFile=$cpf.tmp"
        mvn dependency:build-classpath -q -Dmdep.outputFile=$cpf.tmp
        sed -e "s/^/target\\/classes$sep/" < $cpf.tmp > $cpf
        rm $cpf.tmp
    fi
}

function run() {
    [ -f "$cpf" ] &&
        java -cp @$cpf $main ||
        echo "no classpath file: $cpf, create with 'mkcp'"
}

function wipe() {
    [ -f "$cpf" ] && crm -f $cpf
    unset CLASSPATH
}

mkcp $@ &&
    echo "- export CLASSPATH=\$(cat $cpf)" &&
    export CLASSPATH=$(cat $cpf)

# function cmd() {
#     local args=();
#     for arg in $@; do
#         case $arg in
#         --execute) local execute=true ;;
#         *) args+=($arg) ;;
#         esac
#     done
#     local command="${args[@]}"
#     local prefix=" - "
#     [ "$command" ] && echo $prefix$command && [ "$execute" ] && eval $command
# }
