" Vim syntax file
" Language: Pbscript
" Maintainer: Plankp T.
" Latest Revision: 24 June 2016

if exists("b:current_syntax")
	finish
endif

syn keyword PRE_ENDIF endif ENDIF
syn keyword PRE_ELSE else ELSE
syn keyword PRE_ELSEIF elseif ELSEIF
syn keyword PRE_IF if IF
syn keyword PRE_UNDEF undef UNDEF
syn keyword PRE_DEFINE define DEFINE
syn keyword PRE_ENDMAC endmac ENDMAC
syn keyword PRE_MACRO macro MACRO
syn keyword PRE_CALL call CALL
syn keyword INCLUDE include INCLUDE

syn match DO_WHILE '<<'
syn match WHILE_DO '>>'

syn match pbNumber '(0|(1-9][0-9]*))(\.[0-9]+)?'
syn match pbReadId '@.'
syn match pbAssign '.='
syn match lcomment '# .*$'

syn region pbString start='%%' end='%%'

let b:current_syntax = "pbscript"

hi def link INCLUDE	Include

hi def link PRE_ENDIF	Keyword
hi def link PRE_ELSE	Keyword
hi def link PRE_ELSEIF	Keyword
hi def link PRE_IF	Keyword
hi def link PRE_UNDEF	Keyword
hi def link PRE_DEFINE	Keyword
hi def link PRE_ENDMAC	Keyword
hi def link PRE_MACRO	Keyword
hi def link PRE_CALL	Keyword

hi def link DO_WHILE	Repeat
hi def link WHILE_DO	Repeat

hi def link lcomment	Comment
hi def link pbString	String
hi def link pbNumber	Number
hi def link pbReadId	Identifier
hi def link pbAssign	Statement
