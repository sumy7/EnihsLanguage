NUMBER     : [0-9]+
IDENTIFIER : [a-z_A-Z][a-z_A-Z0-9]+
STRING     : "\"" ALL "\""
comment    : "//" ALL EOL | "/*" [comment] ALL [ comment ] "*/"
number     : NUMBER ( l|L )

factor     : "-" primary | primary
expr       : factor { OP factor }
block      : "{" [statement] { EOL [ statement ] } "}"
simple     : expr [ args ]
statement  : "if" expr block { "elif" expr block } [ "else" block ]  | "while" expr block | "print" args | simple

param      : IDENTIFIER
params     : param { "," param }
param_list : "(" [ params ] ")"
def        : "def" IDENTIFIER param_list block
args       : expr { "," expr }

member     : def | simple
class_body : "{" [ member ] { EOL [ member ]} "}"
defclass   : "class" IDENTIFIER [ "extends" IDENTIFIER] class_body
program    : [ defcalss | def | statement ] EOL 

elements   : expr { "," expr }
postfix    : "." IDENTIFIER | "(" [ args ] ")"
primary    : "fun" param_list block | ( "[" [ elements ] "]" | "(" expr ")" | NUMBER | IDENTIFIER | STRING ) { postfix }