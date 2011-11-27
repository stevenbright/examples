for (bb_t knights = Board.pieces [WN]; knights; knights &= knights-1)
{
    int knight_sq = bit_scan (knights);
    for (bb_t captures = knight_moves [knight_sq] & opponent_pieces; captures; captures &= captures - 1)
    {
        int capture_sq = bit_scan (captures);
        *moves ++ = move (knight_sq, capture_sq);
        *values ++ = Board.sq [capture_sq] * 256 + 192;
    }
}


