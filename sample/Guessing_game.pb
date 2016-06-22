# Written by Plankp T.

!MACRO GUESS
[ 1
	y= 1 + @_ * 100 % 0
	t= 0
	 = # Start guessing!

	[ 1
		t= @t + 1
		 = %%(1 to 100)%%

		i= @	% 0

		[[@y - @i]  = %%You win!%%]
		[(@i < @y)  = %%Not enough%%]
		[(@i > @y)  = %%Too much%%]
	] << (@y <> @i)
	 = %%You took %% + ((@t + %%%%) % %%\.%% : 0) + #  try(s)
]
!ENDMAC

!DEFINE COND 1

!IFDEF COND
!CALL GUESS
!ELSE
 = # This module is disabled
!ENDIF
