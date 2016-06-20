" Vim syntax file
" Language: Pbscript
" Maintainer: Plankp T.
" Latest Revision: 20 June 2016

if exists("b:current_syntax")
	finish
endif

syn match pbNumber '_?(0|(1-9][0-9]*))(\.[0-9]+)?'
syn match pbReadId '@.'
syn match pbAssign '.='
syn match lcomment '# .*$'

syn region pbString start='%%' end='%%'

let b:current_syntax = "pb"

hi def link lcomment	Comment
hi def link pbString	Constant
hi def link pbNumber	Constant
hi def link pbReadId	Constant
hi def link pbAssign	Statement
