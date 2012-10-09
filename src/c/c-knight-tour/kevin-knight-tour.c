/* knight's tour program
 *
 * Original code in Pascal by Niklaus Wirth
 * For an explanation and the original code see
 *	Niklaus Wirth
 *	Algorithms+Data Structures=Programs
 *	Prentice-Hall 1976
 *	pages 137--142
 *
 * Translated to ANSI C by Kevin Karplus, 19 February 1993
 * Further modifications made by Kevin Karplus 7 February 1999
 */
 
 
/* ABSTRACT:
 * This program determines whether a knight can visit all the squares of
 * a chess board exactly once, using the normal movements of chess.  
 * The size of the chess board is specified by compile-time constants
 * (NumRows and NumCols), as is the initial position (StartRow and
 * StartCol).  
 * There are no run-time inputs.
 *
 * A "tour" is a sequence of squares, each visited only once, that
 * reflect a possible sequence of knight's moves.
 * A "partial tour" includes some, but perhaps not all squares.
 * A "complete tour" includes all the squares, and is the desired solution.
 *
 * The output is either a message indicating that no complete tour exists,
 * or a display of the board, with a number in each square.
 * The numbers range from 1 to the number of squares on the board, and
 * indicate the order in which the squares were visited.
 *
 * The three examples of knight's tours given on page 141 of
 * Algorithms+Data Structures=Programs
 * can be obtained with the following settings of the constants:
 *
 *      NumRow  NumCol          StartRow        StartCol
 *      5       5               0               0
 *      5       5               2               2
 *      6       6               0               0
 */

/* KNOWN BUGS and possible future changes:
 *
 *      ChangeRow and ChangeCol should probably be a single array
 *      of 8 structs, each of which has a row and col value.
 *
 *      It might be cleaner to replace the PartialTour array
 *      with a data structure that would hold the array, the number of
 *      squares visited, and the location of the most recently visited
 *      square.  Even better might be to replace the recursion stack with
 *      a stack of moves in the PartialTour data structure, so that
 *      output could be provided in other notations, and "backtrack"
 *      could be defined as an operator on PartialTours.
 *      If this were C++ instead of C, PartialTour would be defined
 *      as a class.
 */

/* CHANGE LOG:
 * 19 February 1993, Kevin Karplus
 *	Translated to C, variable names changed, comments added.
 *
 *      globals and local variables of main program:
 *		n=> NumRows, NumCols
 *		nsq => NumSquares (macro)
 *		type index eliminated
 *		i=>row	made local to main()
 *		j=>col	made local to main()
 *		q	eliminated (using return value, no variable needed)
 *		s	eliminated	(replaced by procedure OnBoard(r,c))
 *		a=> RowChange, b=>ColChange	(subscripts now 0..7, not 1..8)
 *		h=>board (made local to main, and passed as parameter.
 *			also, made to be of new type RectangularBoard)
 *
 *	parameters and local variables of "try" procedure:
 *		try=>CompleteTour
 *		i=>SquaresVisited (meaning changed by 1, for easier naming)
 *		x,y => AtR, AtC
 *		q,q1 eliminated (using return value instead)
 *		k=>direction
 *		u,v => NextR, NextC
 *
 *	Moved the SquaresVisited test down one level in the recursion,
 *	to make the termination of the recursion easier to understand,
 *	and to make the behavior correct on 1x1 boards.
 *	(Note: this bug fix was not part of the assignment given to the
 *	class, but having identified the bug in Wirth's code, I couldn't
 *	stand leaving it in my translation.)
 *
 * 23 February 1993, Kevin Karplus
 *    Minor editing of comments, based on suggestions from David Patmore.
 *
 * 7 February 1999, Kevin Karplus
 *	Changed "board" to "when_visited", "RectangularBoard" to "PartialTour".
 *	Added "NotVisited" constant.
 *	Added efficiency hack to OnBoard, using unsigned ints.
 * 	Added ASCII-art lines to make output look more like a chess board.
 *	Added assertions to check that compile-time constants were legal.
 *	Added return value from main (0 if tour found, 1 otherwise);
 *	Moved RowChange and ColChange arrays into CompleteTour to eliminate
 *		all globals from CompleteTour, and made them static const to
 *		avoid repeated allocation and initialization.
 *	Modified CompleteTour to accept the NEXT move, rather than the LAST
 *		move, allowing starting from an empty board.
 *	Eliminated NextRow and NextCol variables from CompleteTour.
 */


#include <stdio.h>
#include <assert.h>

/* COMPILE-TIME CONSTANTS and TYPES */

/* These constants define the size of the board.
 * They are macros (rather than "const int") so that they can be used 
 * for specifying array dimensions.
 */
#define NumRows	8
#define NumCols 8

#define NumSquares (NumRows*NumCols)	/* the number of squares to visit */

/* StartRow and StartCol give the initial position of the knight.
 * Positions range from (0,0) to (NumRows-1,NumCols-1).
 */
#define StartRow	0
#define StartCol	0

#define NotVisited 	0
/* A PartialTour holds a tour or partial tour.
 * If a square has the value NotVisited, then that square has not been
 * visited yet on this partial tour.
 * Otherwise, the value is the time at which the square was visited
 * (from 1 to NumSquares for a complete tour, from 1 to n for a
 * partial tour with n squares visited). 
 */
typedef PartialTour[NumRows][NumCols];


typedef short int bool;		/* used for true=1/false=0 values */


/* OnBoard(r,c)
 * 	tests to see if a coordinate pair is a legal board position.
 * Inputs: r,c	a coordinate pair
 * Returns:  1 if the row and column position is on the board,
 * 	and 0 if it is not.
 */
bool OnBoard(int r, int c)
{
    return 0<=r && r<NumRows && 0<=c && c<NumCols;
}

/* CompleteTour(when_visited, NextMoveNum, AtR, AtC)
 * 	attempts to complete the partial tour in when_visited.
 *
 * Inputs:
 *	when_visited	holds the partial tour
 *	AtMoveNumber	one more than the number of squares already visited
 *	AtR, AtC	the next position to try in the partial tour
 *
 * Returns:	1 if the partial tour can be completed to a full tour.
 *		0 if no complete tour starts with this partial tour.
 *
 * Outputs:
 *	when_visited holds complete tour if 1 is returned, but
 *	is restored to the partial tour from before call, if 0 returned.
 */
bool
CompleteTour(PartialTour when_visited, 
	int AtMoveNumber, 	int AtR, int AtC)
{
    /* Try adding the new move to the partial tour.
     * If tour is still not complete, try all possible directions for
     * continuing the tour.
     * If no direction results in complete tour, retract the move
     * and return 0 for failure.
     */
    
    /* The pair (RowChange[i], ColChange[i]) is one of the eight possible
     * directions for the knight to move.
     * A knight at (r,c) can move to (r+RowChange[i], c+ColChange[i]),
     * if that destination is on the board and not already used.
     */
    static const int RowChange[8] = { 2,  1, -1, -2, -2, -1,  1,  2};
    static const int ColChange[8] = { 1,  2,  2,  1, -1, -2, -2, -1};

    int direction; /* loop counter for the 8 directions a knight can move */
    
    if (! OnBoard(AtR,AtC) || when_visited[AtR][AtC]!=NotVisited)
    	return 0;	/* can't visit this square, so no legal tour */
    
    /* visit the square */
    when_visited[AtR][AtC] = AtMoveNumber;
    if (AtMoveNumber >= NumSquares)
    	return 1;	/* partial tour is already complete */
    
    /* Try adding a move and completing the new partial tour. */
    for (direction=0; direction<8; direction++)
    {   if (CompleteTour(when_visited,AtMoveNumber+1, 
		AtR+RowChange[direction], AtC+ColChange[direction]))
	    return 1;	/* tour completed successfully */
    }
    
    /* None of the eight directions led to a complete tour with this
     *	    	move added, so backtrack. 
     */ 
    when_visited[AtR][AtC] = NotVisited;
    return 0;
}

/* main
 *	looks for a knight's tour, and prints the tour if it finds one,
 *	as described in the abstract at the beginning of the file.
 *
 *	The main procedure has no inputs, outputs, or return values.
 *	All parameters are set by compile-time constants.
 *	The only side effect is the printing of the solution,
 *	or a message saying that no tour exists.
 */
int main(void)
{
    int row,col;	/* row and column loop indices */
    PartialTour when_visited;

    /* check that the compile-time constants are all legal */
    assert(NumRows>=1);
    assert(NumCols>=1);
    assert(OnBoard(StartRow,StartCol));
    
    /* clear the when_visited array to get an empty partial tour */
    for (row=0; row<NumRows; row++)
    	for(col=0; col<NumCols; col++)
	    when_visited[row][col]=NotVisited;

    if (CompleteTour(when_visited, 1, StartRow,StartCol))
    {	/* print the solution found */
	for (row=0; row<NumRows; row++)
    	{   for(col=0; col<NumCols; col++)
		printf("+---");
	    printf("+\n");
	    for(col=0; col<NumCols; col++)
	    	printf("|%3d",when_visited[row][col]);
	    printf("|\n");
	}
	for(col=0; col<NumCols; col++)
	    printf("+---");
	printf("+\n");
	return 0;
    }
    
    printf("No knight's tour for %d x %d board, starting at (%d,%d)\n",
    		NumRows, NumCols, StartRow, StartCol);
    printf("Possible positions range from (0,0) to (%d,%d)\n",
		NumRows-1, NumCols-1);
    return 1;
}

